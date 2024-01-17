package org.team555.components.subsystems;

import java.util.Arrays;
import org.team555.constants.ControlScheme;
import org.team555.constants.DriveConstants;
import org.team555.constants.DriveConstants.ThetaPID;
import org.team555.inputs.JoystickInput;
import org.team555.math.Math555;
import org.team555.util.frc.Logging;
import org.team555.util.frc.PIDMechanism;
import org.team555.util.frc.Tunable;
import org.team555.util.frc.commandrobot.ManagerSubsystemBase;
import org.team555.Crescendo;
import org.team555.constants.*;
import com.revrobotics.CANSparkMax;
import com.swervedrivespecialties.swervelib.SwerveModule;


import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;



public class Drivetrain extends ManagerSubsystemBase {


    private final SwerveModule[] modules;

    // private final SlewRateLimiter xLimiter;
    // private final SlewRateLimiter yLimiter;
    // private final SlewRateLimiter thetaLimiter;

    private final PIDMechanism xPID;
    private final PIDMechanism yPID;
    private final PIDMechanism thetaPID;

    private boolean useFieldRelative;

    private final SwerveDrivePoseEstimator poseEstimator;

    public boolean useFieldCentric = true;

    public Drivetrain() {
        modules = new SwerveModule[4];

        poseEstimator = new SwerveDrivePoseEstimator(
            DriveConstants.KINEMATICS, 
            getRobotRotation(),
            getModulePositions(),
            new Pose2d()
        );


        Pose2d[] modPoses = new Pose2d[4];

        for (int i = 0; i < 4; i++) {
            modules[i] = DriveConstants.MODULES[i].build();

            modPoses[i] = new Pose2d(DriveConstants.MOD_POSITIONS[i], new Rotation2d());
            CANSparkMax motor = (CANSparkMax) modules[i].getSteerMotor();
            motor.burnFlash();
        }

        DriveConstants.DRIVE_INVERT.whenUpdate(modules[0].getDriveMotor()::setInverted)
                    .whenUpdate(modules[1].getDriveMotor()::setInverted)
                    .whenUpdate(modules[2].getDriveMotor()::setInverted)
                    .whenUpdate(modules[3].getDriveMotor()::setInverted);
        DriveConstants.STEER_INVERT.whenUpdate(modules[0].getSteerMotor()::setInverted)
                    .whenUpdate(modules[1].getSteerMotor()::setInverted)
                    .whenUpdate(modules[2].getSteerMotor()::setInverted)
                    .whenUpdate(modules[3].getSteerMotor()::setInverted);


        PIDController xController = new PIDController(
            DriveConstants.PosPID.consts().kP,
            DriveConstants.PosPID.consts().kI, 
            DriveConstants.PosPID.consts().kD
        );

        PIDController yController = new PIDController(
            DriveConstants.PosPID.consts().kP,
            DriveConstants.PosPID.consts().kI, 
            DriveConstants.PosPID.consts().kD
        );

        PIDController thetaController = new PIDController(
            ThetaPID.consts().kP,
            ThetaPID.consts().kI, 
            ThetaPID.consts().kD
        );

        thetaController.setTolerance(Math.toRadians(1.5), Math.toRadians(0.5));
        thetaController.enableContinuousInput(0, 2*Math.PI);

        DriveConstants.PosPID.KP.whenUpdate(xController::setP).whenUpdate(yController::setP);
        DriveConstants.PosPID.KI.whenUpdate(xController::setI).whenUpdate(yController::setI);
        DriveConstants.PosPID.KD.whenUpdate(xController::setD).whenUpdate(yController::setD);

        ThetaPID.KP.whenUpdate(thetaController::setP);
        ThetaPID.KI.whenUpdate(thetaController::setI);
        ThetaPID.KD.whenUpdate(thetaController::setD);

        xPID     = new PIDMechanism(xController);
        yPID     = new PIDMechanism(yController);
        thetaPID = new PIDMechanism(thetaController);
        //TODO test if this should be normalized
        thetaPID.disableOutputClamping();

    }

    public void enableFieldRelative() {
        useFieldRelative = true;
        Logging.info("Field centric enabled");
    }

    public void disableFieldRelative() {
        useFieldRelative = false;
        Logging.info("Field centric disabled");
    }

    public boolean usingFieldRelative() {
        return useFieldCentric;
    }

    public Rotation2d getRobotRotation() {
        return Crescendo.gyroscope.getRotation2d(); // TODO: getRotation2d may have to be rewritten
    }

    public ChassisSpeeds getSpeedsFromMode(double omega, double x, double y) {
        if(useFieldRelative) return ChassisSpeeds.fromFieldRelativeSpeeds(x, y, omega, getRobotRotation());
        return new ChassisSpeeds(x, y, omega);
    }

    /**
    * Get the rotation of the robot within the unit circle.
    */
    public Rotation2d getRobotRotationInCircle() {
        return Rotation2d.fromRadians(getRobotRotation().getRadians() % (2*Math.PI));
    }

    public Pose2d getRobotPose() {
        return poseEstimator.getEstimatedPosition();
    }

    public void setInput(JoystickInput turn, JoystickInput drive) {
        ControlScheme.TURN_ADJUSTER.adjustX(turn);
        ControlScheme.DRIVE_ADJUSTER.adjustMagnitude(drive);

        setChassisSpeeds(getSpeedsFromMode(
            turn.getX()  * DriveConstants.MAX_TURN_SPEED_RAD_PER_S,
            drive.getY() * DriveConstants.MAX_SPEED_MPS,
            drive.getX() * DriveConstants.MAX_SPEED_MPS
        ));
    }

    public void setChassisSpeeds(double omega, double vx, double vy) {
        vx    /= DriveConstants.MAX_SPEED_MPS;
        vy    /= DriveConstants.MAX_SPEED_MPS;
        omega /= DriveConstants.MAX_TURN_SPEED_RAD_PER_S;

        vx    = Math555.clamp1(vx);
        vy    = Math555.clamp1(vy);
        omega = Math555.clamp1(omega);

        xPID    .setSpeed(vx);
        yPID    .setSpeed(vy);
        thetaPID.setSpeed(omega);
    }

    public void setChassisSpeeds(ChassisSpeeds speeds) {
        setChassisSpeeds(speeds.omegaRadiansPerSecond, speeds.vxMetersPerSecond, speeds.vyMetersPerSecond);
    }

    private void driveFromStates(SwerveModuleState[] states) {

        SwerveDriveKinematics.desaturateWheelSpeeds(states, DriveConstants.MAX_SPEED_MPS);

        for(int i = 0; i < 4; i++) {
            //TODO why is it disabled
            // states[i] = SwerveModuleState.optimize(
            //     states[i], 
            //     new Rotation2d(modules[i].getSteerAngle())
            // );

            modules[i].set(
                states[i].speedMetersPerSecond / DriveConstants.MAX_SPEED_MPS * DriveConstants.MAX_VOLTAGE_V,
                states[i].angle.getRadians()
            );
        }

        poseEstimator.update(getRobotRotation(), getModulePositions());
    }

    public SwerveModulePosition[] getModulePositions() {
        return Arrays.stream(modules)
            .map(x -> x.getPosition())
            .toArray(SwerveModulePosition[]::new);
    }

    @Override
    public void always() {
        
       
        xPID.setMeasurement(getRobotPose().getX());
        yPID.setMeasurement(getRobotPose().getY());
        thetaPID.setMeasurement(getRobotRotationInCircle().getRadians());


        xPID.update();
        yPID.update();
        thetaPID.update();

        double xSpeed = xPID.getSpeed();
        double ySpeed = yPID.getSpeed();
        //TODO should we use SlewRateLimiters?
        double thetaSpeed = thetaPID.getSpeed(); 

        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(xSpeed, ySpeed, thetaSpeed);

        SwerveModuleState[] states = DriveConstants.KINEMATICS.toSwerveModuleStates(chassisSpeeds);

        driveFromStates(states);


    }
}