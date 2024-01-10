package org.team555.components.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import org.team555.constants.*;

public class Fliptop {
    
    private CANSparkMax motor = new CANSparkMax(Ports.FLIPTOP_MOTOR_PORT, MotorType.kBrushless);

    public Fliptop() {
        motor.setInverted(false);
    }
    
    public void forward() {
        motor.set(Constants.FLIPTOP_SPEED);
    }
    public void backward() {
        motor.set(-Constants.FLIPTOP_SPEED);
    }
    
    
}
