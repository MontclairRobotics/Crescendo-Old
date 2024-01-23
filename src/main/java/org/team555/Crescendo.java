// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.team555;

import org.team555.components.managers.GyroscopeNavX;
import org.team555.components.subsystems.*;
import org.team555.constants.ControlScheme;
import org.team555.inputs.JoystickInput;
import org.team555.util.frc.GameController;
import org.team555.util.frc.commandrobot.RobotContainer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Commands;

import org.team555.util.frc.GameController.Axis;
import org.team555.util.frc.GameController.Button;
import org.team555.util.frc.GameController.DPad;

public class Crescendo extends RobotContainer {
    // SUBSYSTEMS
    public static Intake mouth = new Intake();
    public static Fliptop fliptop = new Fliptop();
    public static Shooter shooter = new Shooter();
    public static Sprocket sprocket = new Sprocket();

    public static final GyroscopeNavX gyroscope = new GyroscopeNavX();
    public static final Drivetrain drivetrain = new Drivetrain();
    public static final GameController operatorController = GameController.from(
        ControlScheme.OPERATOR_CONTROLLER_TYPE,
        ControlScheme.OPERATOR_CONTROLLER_PORT
    );
    public static final GameController driverController = GameController.from(
        ControlScheme.DRIVER_CONTROLLER_TYPE, 
        ControlScheme.DRIVER_CONTROLLER_PORT
    );

    @Override
    public void initialize() {
       
        drivetrain.setDefaultCommand(Commands.run(() -> {
            if (!DriverStation.isTeleop()) 
                {
                    drivetrain.setChassisSpeeds(0, 0, 0);
                    return;
                }

                drivetrain.setInput(
                    JoystickInput.getRight(driverController, false, true), //TODO do these inverts need to be set to true or false? Last year x was set to true for both remotes
                    JoystickInput.getLeft(driverController, false, true)
                );
        })); //TODO why is there no default method supplied for this function? - rechs

        
        // SHOOTER BINDINGS
        operatorController.getButton(Button.A_CROSS).onTrue(Commands555.shoot());
        operatorController.getButton(Button.X_SQUARE).onTrue(Commands555.stopShooter());

        // INTAKE BINDINGS
        operatorController.getAxis(Axis.RIGHT_TRIGGER)
        .whenGreaterThan(0.5).onTrue(Commands555.barf())
        .onFalse(Commands555.stopIntake());

        operatorController.getAxis(Axis.LEFT_TRIGGER)
        .whenGreaterThan(0.5).onTrue(Commands555.eat())
        .onFalse(Commands555.stopIntake());

        // SPROCKET BINDINGS
        operatorController.getDPad(DPad.UP).onTrue(Commands555.goUp()).onFalse(Commands555.stopSprocket());
        operatorController.getDPad(DPad.DOWN).onTrue(Commands555.goDown()).onFalse(Commands555.stopSprocket());

        // FLIPTOP BINDINGS
        operatorController.getButton(Button.Y_TRIANGLE).onTrue(Commands555.goDown());
        operatorController.getButton(Button.B_CIRCLE).onTrue(Commands555.goUp());
    }
    
}