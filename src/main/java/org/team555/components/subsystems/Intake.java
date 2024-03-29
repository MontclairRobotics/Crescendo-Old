package org.team555.components.subsystems;

import org.team555.constants.*;
import org.team555.util.frc.commandrobot.ManagerSubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Intake extends ManagerSubsystemBase {

    private final CANSparkMax intakeMotor1 = new CANSparkMax(Ports.INTAKE_MOTOR_1_PORT, MotorType.kBrushless); 
    private final CANSparkMax intakeMotor2 = new CANSparkMax(Ports.INTAKE_MOTOR_2_PORT, MotorType.kBrushless);

    // Intake
    public void in() {
        intakeMotor1.set(Constants.INTAKE_SPEED); 
        intakeMotor2.set(Constants.INTAKE_SPEED);
    }

    // Reverse intake if gamepiece gets stuck
    public void out() {
        intakeMotor1.set(-Constants.INTAKE_SPEED);
        intakeMotor2.set(-Constants.INTAKE_SPEED);
    }
    
    // Stop intaking
    public void stop() {
        intakeMotor1.set(0);
        intakeMotor2.set(0);
    }

}