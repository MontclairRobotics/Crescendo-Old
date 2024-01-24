package org.team555.components.managers;

import java.util.HashMap;

import org.team555.Crescendo;
import org.team555.components.subsystems.Drivetrain;
import org.team555.constants.DriveConstants;
import org.team555.util.frc.Logging;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

public class Auto { 

    private ShuffleboardTab autoTab = Shuffleboard.getTab("auto");
    private final SendableChooser<String> chooseStart;
    private final GenericEntry scoreFirst;
    private final GenericEntry pickupOnce;
    private final GenericEntry scoreSpeakerTwice;
    private final GenericEntry pickupTwice;
    private final GenericEntry scoreAmp;
    private final GenericEntry pickupNearOnly;
    private final GenericEntry pickupFarOnly;
    private final GenericEntry autoStringEntry;

    private String autoString = "";


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

        
        chooseStart = new SendableChooser<String>();
        chooseStart.setDefaultOption("1", "1");
        chooseStart.addOption("2", "2");
        chooseStart.addOption("3", "3");

        autoTab.add("Starting Position", chooseStart)
            .withPosition(0,0)
            .withSize(2, 1);

        scoreFirst = autoTab.add("Score First", false)
            .withWidget(BuiltInWidgets.kToggleSwitch)
            .withPosition(0, 1)
            .withSize(1, 2)
            .getEntry();
        
        pickupOnce = autoTab.add("Pickup Once", false)
            .withWidget(BuiltInWidgets.kToggleSwitch)
            .withPosition(0, 2)
            .withSize(1, 2)
            .getEntry();
        
        scoreSpeakerTwice = autoTab.add("Score Speaker Twice", false)
            .withWidget(BuiltInWidgets.kToggleSwitch)
            .withPosition(0, 3)
            .withSize(1, 2)
            .getEntry();

        pickupTwice = autoTab.add("Pickup Twice", false)
            .withWidget(BuiltInWidgets.kToggleSwitch)
            .withPosition(0, 4)
            .withSize(1, 2)
            .getEntry();

        scoreAmp = autoTab.add("Score Amp", false)
            .withWidget(BuiltInWidgets.kToggleSwitch)
            .withPosition(0, 5)
            .withSize(1, 2)
            .getEntry();

        pickupNearOnly = autoTab.add("Pickup Near Only", false)
            .withWidget(BuiltInWidgets.kToggleSwitch)
            .withPosition(0, 6)
            .withSize(1, 2)
            .getEntry();

        pickupFarOnly = autoTab.add("Pickup Far Only", false) 
            .withWidget(BuiltInWidgets.kToggleSwitch)
            .withPosition(0, 7)
            .withSize(1, 2)
            .getEntry();

        autoStringEntry = autoTab.add("Auto String Entry", autoString)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(0, 8)
            .withSize(2, 1)
            .getEntry();

        
    }

    /**
     * Gets the path from path planner 
     * @param filePath (String)
     * @return the command to follow the path it just got.
     */
    public Command getPath(String filePath) {
        PathPlannerPath path = PathPlannerPath.fromPathFile(filePath); 

        return AutoBuilder.followPath(path);
    }

    private String getAutoString(String start, boolean pickupOnce, boolean scoreSpeakerTwice, boolean pickupTwice, boolean scoreAmp, boolean pickupNearOnly, boolean pickupFarOnly) {
        String str = "";
        HashMap<String, String> pointMap = new HashMap<String, String>();
        pointMap.put("1", "AD");
        pointMap.put("2", "BG");
        pointMap.put("3,", "CH");

        if(start == null) {
            Logging.errorNoTrace("Invalid start position for auto path");
            return null;
        }
        str += "" + pointMap.get(start).charAt(0);

        //Go to the nearest near pickup point if pickup near only, probably used if an alliance member can't ground pickup
        if(pickupOnce && pickupNearOnly) str += pointMap.get(start);
        //Go to the nearest far pickup point
        else str += "" + pointMap.get(start).charAt(1);

        //this assumes that you always score in the same position you start in
        str += start;

        

    }
}
