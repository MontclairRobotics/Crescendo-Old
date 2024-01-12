package org.team555.components.subsystems;
import org.team555.constants.Ports;
import org.team555.util.frc.commandrobot.ManagerSubsystemBase;
import org.team555.constants.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Sprocket extends ManagerSubsystemBase {
    
    private final CANSparkMax motor = new CANSparkMax(Ports.ANGLE_MOTOR_PORT, MotorType.kBrushless);

    public Sprocket() {
        motor.setInverted(false);
    }
    // Move sprocket up
    public void goUp() {
        motor.set(Constants.ANGLE_MOVE_SPEED);
    }
    // Move sprocket down
    public void goDown() {
        motor.set(-Constants.ANGLE_MOVE_SPEED);
    }
    // Stop sprocket
    public void stop() {
        motor.set(0);
    }
}