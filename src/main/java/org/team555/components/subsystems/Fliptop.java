package org.team555.components.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import org.team555.constants.*;

public class Fliptop {
    //TODO: Actual motor ID
    private CANSparkMax motor = new CANSparkMax(Constants.FLIPTOP_MOTOR_ID, MotorType.kBrushless);

    public Fliptop() {
        motor.setInverted(false);
    }
    
    public void forward() {
        motor.set(Constants.FLIPTOP_SPEED);
    }
    public void backward() {
        motor.set(-1*Constants.FLIPTOP_SPEED);
    }
    public void off() {
        motor.set(0);
    }
    
}
