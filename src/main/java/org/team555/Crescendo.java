// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.team555;

import org.team555.components.subsystems.*;
import org.team555.constants.ControlScheme;
import org.team555.util.frc.GameController;
import org.team555.util.frc.commandrobot.RobotContainer;

public class Crescendo extends RobotContainer 
{
    // SUBSYSTEMS
    public static Intake mouth = new Intake();
    public static Fliptop fliptop = new Fliptop();
    public static Shooter shooter = new Shooter();
    public static Sprocket sprocket = new Sprocket();

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
<<<<<<< Updated upstream
        operatorController.getAxis(Axis.LEFT_TRIGGER)
            .whenGreaterThan(0.5).onTrue(Commands555.eat())
            .onFalse(Commands555.stop());
        operatorController.getAxis(Axis.RIGHT_TRIGGER)
            .whenGreaterThan(0.5).onTrue(Commands555.barf())
            .onFalse(Commands555.stop());
=======
        operatorController.getDPad(DPad.UP).onTrue(commands555.goUp()).onFalse(commands555.stop());
        operatorController.getDPad(DPad.DOWN).onTrue(commands555.goDown()).onFalse(commands555.stop());
>>>>>>> Stashed changes
    }
    
}