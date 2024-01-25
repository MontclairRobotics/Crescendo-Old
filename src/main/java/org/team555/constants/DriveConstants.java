package org.team555.constants;

import org.team555.math.Units555;
import org.team555.util.frc.SwerveModuleSpec;
import org.team555.util.frc.Tunable;

import com.pathplanner.lib.util.PIDConstants;
import com.swervedrivespecialties.swervelib.MotorType;
import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

public class DriveConstants {

    public static final MotorType DRIVE_TYPE = MotorType.FALCON;
    public static final MotorType STEER_TYPE = MotorType.NEO;

    public static final Tunable<Boolean> DRIVE_INVERT = Tunable.of(true, "drive.drive_invert");
    public static final Tunable<Boolean> STEER_INVERT = Tunable.of(false, "drive.steer_invert");


    //TODO set steer offsets

    // FL
    // 0.571533  rot
    private static final SwerveModuleSpec FRONT_LEFT = 
        new SwerveModuleSpec(
            SdsModuleConfigurations.MK4I_L1, 
            DRIVE_TYPE, Ports.DRIVE_FL_PORT, DRIVE_INVERT.get(), 
            STEER_TYPE, Ports.STEER_FL_PORT, STEER_INVERT.get(),  
            Ports.CANCO_FL_PORT, 205.75188
        ); 

     // FR
     //0.348633  rot
    private static final SwerveModuleSpec FRONT_RIGHT = 
        new SwerveModuleSpec(
            SdsModuleConfigurations.MK4I_L1, 
            DRIVE_TYPE, Ports.DRIVE_FR_PORT, DRIVE_INVERT.get(), 
            STEER_TYPE, Ports.STEER_FR_PORT, STEER_INVERT.get(), 
            Ports.CANCO_FR_PORT, 125.50788
        ); 
    
    // BL
    //0.670410 rot
    private static final SwerveModuleSpec BACK_LEFT = 
        new SwerveModuleSpec(
            SdsModuleConfigurations.MK4I_L1, 
            DRIVE_TYPE, Ports.DRIVE_BL_PORT, DRIVE_INVERT.get(), 
            STEER_TYPE, Ports.STEER_BL_PORT, STEER_INVERT.get(), 
            Ports.CANCO_BL_PORT, 241.3476
        ); 
    
    // BR

    //0.953613 rot
    private static final SwerveModuleSpec BACK_RIGHT =
        new SwerveModuleSpec(
            SdsModuleConfigurations.MK4I_L1, 
            DRIVE_TYPE, Ports.DRIVE_BR_PORT, DRIVE_INVERT.get(), 
            STEER_TYPE, Ports.STEER_BR_PORT, STEER_INVERT.get(), 
            Ports.CANCO_BR_PORT, 343.30068
        ); 

    /**
     * Rotator port first, driver port second
     * 
     * FL - 0
     * FR - 1
     * BL - 2
     * BR - 3
     */
    public static final SwerveModuleSpec[] MODULES = 
    {
        FRONT_LEFT, 
        FRONT_RIGHT,
        BACK_LEFT,
        BACK_RIGHT
    };

    public static final String[] MODULE_NAMES = {"FL", "FR", "BR", "BL"};

    public static class PosPID
    {
        public static final Tunable<Double> KP = Tunable.of(0, "drive.pos.kp");
        public static final Tunable<Double> KI = Tunable.of(0, "drive.pos.ki");
        public static final Tunable<Double> KD = Tunable.of(0, "drive.pos.kd");

        public static PIDConstants consts()
        {
            return new PIDConstants(
                KP.get(), 
                KI.get(), 
                KD.get()
            );
        }
    }

    public static class ThetaPID
    {
        public static final Tunable<Double> KP = Tunable.of(0, "drive.theta.kp");
        public static final Tunable<Double> KI = Tunable.of(0, "drive.theta.ki");
        public static final Tunable<Double> KD = Tunable.of(0, "drive.theta.kd");

        public static PIDConstants consts()
        {
            return new PIDConstants(
                KP.get(),  
                KI.get(), 
                KD.get()
            );
        }


        public static final Tunable<Double> KAutoP = Tunable.of(0, "drive.auto.theta.kp"); //3.5
        public static final Tunable<Double> KAutoI = Tunable.of(0, "drive.auto.theta.ki");
        public static final Tunable<Double> KAutoD = Tunable.of(0, "drive.auto.theta.kd"); //0.22

        public static PIDConstants autoconsts()
        {
            return new PIDConstants(
                KAutoP.get(), 
                KAutoI.get(), 
                KAutoD.get()
            );
        }
    }

    public static final double MAX_VOLTAGE_V = 12.0;

    public static final double MAX_SPEED_MPS = Units.feetToMeters(11);
    public static final double MAX_ACCEL_MPS2 = Units555.miphpsToMpsps(10);
    public static final double MAX_TURN_SPEED_RAD_PER_S = Math.PI * 2;
    public static final double MAX_TURN_ACCEL_RAD_PER_S2 = Units.degreesToRadians(360);

    //TODO Set offsets

    private static final double WHEEL_BASE_HEIGHT = Units.inchesToMeters(23.75);
    private static final double WHEEL_BASE_WIDTH = Units.inchesToMeters(23.75);

    private static Translation2d FLPosition = new Translation2d(WHEEL_BASE_HEIGHT/2, WHEEL_BASE_WIDTH/2); //FL
    private static Translation2d FRPosition = new Translation2d(WHEEL_BASE_HEIGHT/2, -WHEEL_BASE_WIDTH/2); //FR
    private static Translation2d BLPosition = new Translation2d(-WHEEL_BASE_HEIGHT/2, WHEEL_BASE_WIDTH/2); //BL
    private static Translation2d BRPosition = new Translation2d(-WHEEL_BASE_HEIGHT/2, -WHEEL_BASE_WIDTH/2); //BR
    
    public static final Translation2d[] MOD_POSITIONS = {
        FLPosition,
        FRPosition,
        BLPosition,
        BRPosition
    };

    public static final SwerveDriveKinematics KINEMATICS = new SwerveDriveKinematics(
        FLPosition, //FL
        FRPosition, //FR
        BLPosition, //BL
        BRPosition  //BR
    );



}