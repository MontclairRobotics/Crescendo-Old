package org.team555;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Commands555 
{   
    // INTAKE COMMANDS

    public static Command eat() {
        return Commands.runOnce(Crescendo.mouth::in, Crescendo.mouth).withName("intake in");
    }

    public static Command barf() {
       return Commands.runOnce(Crescendo.mouth::out, Crescendo.mouth).withName("intake out");
    }

    public static Command stopIntake() {
        return Commands.runOnce(Crescendo.mouth::stop, Crescendo.mouth).withName("intake stop");
    }

    // FLIPTOP COMMANDS

    public static Command foward() {
        return Commands.runOnce(Crescendo.fliptop::forward, Crescendo.fliptop).withName("fliptop forward");
    }

    public static Command backward() {
        return Commands.runOnce(Crescendo.fliptop::backward, Crescendo.fliptop).withName("fliptop backward");
    }

    // SPROCKET COMMANDS

    public static Command goUp() {
        return Commands.runOnce(Crescendo.sprocket::goUp).withName("sprocket up");
    }
    public static Command goDown() {
        return Commands.runOnce(Crescendo.sprocket::goDown).withName("sprocket down");
    }
    public static Command stopSprocket() {
        return Commands.runOnce(Crescendo.sprocket::stop).withName("sprocket stop");
    }
    
    

    // SHOOTER COMMANDS

    public static Command shoot() {
        return Commands.runOnce(Crescendo.shooter::shoot, Crescendo.shooter).withName("shooter shoot");
    }
    
    public static Command stopShooter() {
        return Commands.runOnce(Crescendo.shooter::stop, Crescendo.shooter).withName("shooter stop");
    }
    public static Command reverseShooter() {
        return Commands.runOnce(Crescendo.shooter::reverseShooter, Crescendo.shooter).withName("shooter reverse");
    }
}
