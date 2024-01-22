package org.team555.vision;

import edu.wpi.first.wpilibj.Timer;

public class Limelight {
    
    public Limelight() {
 
        double[] camerapose_robotspace = new double[] {-1, -1, -1, 0.0, 0.0, 0.0};
        LimelightHelpers.setLimelightNTDoubleArray("limelight","camerapose_robotspace", camerapose_robotspace);
        // TODO: Networktables boilerplate?

    }

    public double getTimestampSeconds() {
        double latency = (LimelightHelpers.getLimelightNTDouble("limelight", "cl") + LimelightHelpers.getLimelightNTDouble("limelight", "tl")) / 1000.0;
        return Timer.getFPGATimestamp() - latency;

    }


}
