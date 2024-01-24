package org.team555.vision;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj.Timer;

public class Limelight {
    private String cameraName;
    private Debouncer targetDebouncer = new Debouncer(0.1, DebounceType.kFalling);
    
    public Limelight(String cameraName, DetectionType initialType) {
 
        double[] camerapose_robotspace = new double[] {-1, -1, -1, 0.0, 0.0, 0.0};
        LimelightHelpers.setLimelightNTDoubleArray(cameraName,"camerapose_robotspace", camerapose_robotspace);
        // TODO: Networktables boilerplate?

        this.cameraName = cameraName;
    }
    
    public double getTimestampSeconds() {
        double latency = (LimelightHelpers.getLimelightNTDouble(cameraName, "cl") + LimelightHelpers.getLimelightNTDouble(cameraName, "tl")) / 1000.0;
        return Timer.getFPGATimestamp() - latency;
    }

    public boolean hasValidTarget() {
        boolean hasMatch = (LimelightHelpers.getLimelightNTDouble(cameraName, "tv") == 1.0);
        return targetDebouncer.calculate(hasMatch);
    }

    public boolean currentPipelineMatches(DetectionType type) {

        int pipeline = (int) LimelightHelpers.getCurrentPipelineIndex(cameraName);
        return type.getPipe() == pipeline;

    }
    
    private void setPipelineTo(DetectionType type) {
        if (type == DetectionType.DRIVER) {
            LimelightHelpers.setCameraMode_Driver(cameraName);
        } else {
            LimelightHelpers.setCameraMode_Processor(cameraName);
        }
        
        LimelightHelpers.setPipelineIndex(cameraName, type.getPipe());
    }
}
