package org.team555.components.managers;

import org.team555.Crescendo;
import org.team555.components.subsystems.Drivetrain;
import org.team555.constants.DriveConstants;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.ReplanningConfig;

public class Auto { // :)

    public Auto() {

        AutoBuilder.configureHolonomic(
            Crescendo.drivetrain::getRobotPose, 
            Crescendo.drivetrain.poseEstimator::resetPosition, 
            null, 
            null, 
            new HolonomicPathFollowerConfig(
                DriveConstants.PosPID.consts(),
                DriveConstants.ThetaPID.autoconsts(),
                DriveConstants.MAX_SPEED_MPS,
                -1.0, //TODO: We need to measure the drivebase radius (center to furthest swerve module)
                new ReplanningConfig()
            ), 
            Drivetrain.shouldFlipSide(), 
            Crescendo.drivetrain)
        ;
    }
}
