package org.team555.components.subsystems;

import org.team555.constants.*;
import org.team555.util.frc.commandrobot.ManagerSubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;


public class Shooter extends ManagerSubsystemBase {

    private final CANSparkMax motor1 = new CANSparkMax(Ports.SHOOTER_MOTOR_1_PORT, MotorType.kBrushless);
    private final CANSparkMax motor2 = new CANSparkMax(Ports.SHOOTER_MOTOR_2_PORT, MotorType.kBrushless);
    
    public Shooter() {
        // Possibility of adding spin (spinning one motor faster than the other so the note is more stable, like a frisbee)
        motor1.setInverted(false);
        motor2.setInverted(false);
    }
    
    // shoot
    public void shoot() {
        motor1.set(Constants.EJECT_SPEED);
        motor2.set(Constants.EJECT_SPEED);
    }

    //stop shooting
    public void stop(){
        motor1.set(0);
        motor2.set(0);
    }
    public void reverseShooter() {
        motor1.set(-Constants.EJECT_SPEED);
        motor2.set(-Constants.EJECT_SPEED);
    }
}