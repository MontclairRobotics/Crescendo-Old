package org.team555.components.managers;

import org.team555.Crescendo;
import org.team555.components.subsystems.Drivetrain;
import org.team555.constants.DriveConstants;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.wbilibj.shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

public class Auto { 

    private ShuffleboardTab autoTab = Shuffleboard.getTab("auto");
    private SendableChooser<String> chooseStart;

    public Auto() {
        AutoBuilder.configureHolonomic(
            Crescendo.drivetrain::getRobotPose, 
            Crescendo.drivetrain::setRobotPose, 
            Crescendo.drivetrain::getRobotRelativeSpeeds, 
            Crescendo.drivetrain::setChassisSpeeds, 
            new HolonomicPathFollowerConfig(
                DriveConstants.PosPID.consts(),
                DriveConstants.ThetaPID.autoconsts(),
                DriveConstants.MAX_SPEED_MPS,
                -1.0, // TODO: We need to measure the drivebase radius (center to furthest swerve module)
                new ReplanningConfig()
            ), 
            Crescendo.drivetrain::shouldFlipSide, 
            Crescendo.drivetrain
        );

        autoTab.add("Starting Position", chooseStart)
            .withPosition(0,0)
            .withSize(2, 1);

    }

    /**
     * Gets the path from path planner 
     * @param filePath (String)
     * @return the command to follow the path it just got.
     */
    public Command getPath(String filePath) {
        PathPlannerPath path = new PathPlannerPath.fromPathFile(filePath);

        return AutoBuilder.followPath(path);
    }
}
