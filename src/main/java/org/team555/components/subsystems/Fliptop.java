package org.team555.components.subsystems;

import org.team555.constants.*;
import org.team555.util.frc.commandrobot.ManagerSubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;


public class Fliptop extends ManagerSubsystemBase {
    
    private final CANSparkMax motor = new CANSparkMax(Ports.FLIPTOP_MOTOR_PORT, MotorType.kBrushless);

    public Fliptop() {
        motor.setInverted(false);
    }
    
    // Move fliptop forwards
    public void forward() {
        motor.set(Constants.FLIPTOP_SPEED);
    }
    
    // Move fliptop backwards
    public void backward() {
        motor.set(-Constants.FLIPTOP_SPEED);
    }
    
    
}
