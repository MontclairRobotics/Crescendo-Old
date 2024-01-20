package org.team555.components.managers;

import org.team555.Crescendo;

import com.pathplanner.lib.auto.AutoBuilder;

public class Auto { // :)

    public Auto() {

        AutoBuilder.configureHolonomic(
            Crescendo.drivetrain::getRobotPose, 
            Crescendo.drivetrain.poseEstimator::resetPosition, 
            null, 
            null, 
            null, 
            null, 
            Crescendo.drivetrain)
        ;
    }
}
