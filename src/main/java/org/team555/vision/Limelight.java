package org.team555.vision;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.Timer;

public class Limelight {
    private String cameraName;
    private DetectionType targeting;
    private Debouncer targetDebouncer;
    
    public Limelight(String cameraName, DetectionType initialType) {
 
        double[] camerapose_robotspace = new double[] {-1, -1, -1, 0.0, 0.0, 0.0};
        LimelightHelpers.setLimelightNTDoubleArray(cameraName,"camerapose_robotspace", camerapose_robotspace);
        // TODO: Networktables boilerplate?

        this.cameraName = cameraName;
        this.targeting = initialType;
        

    }
    
    public double getTimestampSeconds() {
        double latency = (LimelightHelpers.getLimelightNTDouble(cameraName, "cl") + LimelightHelpers.getLimelightNTDouble(cameraName, "tl")) / 1000.0;
        return Timer.getFPGATimestamp() - latency;

    }

    // public double hasValidTarget() {
    //     // Debouncer 
    // }

    // public boolean currentPipelineMatches(DetectionType ty)
    // {
    //     if(ty == DetectionType.DEFAULT) return currentPipelineMatches(getDefaultInternal());

    //     int pipe = (int) getPipeline();

    //     if     (ty == DetectionType.CUBE || ty == DetectionType.CONE) return (pipe == CONE_CUBE_PIPE);
    //     else if(ty == DetectionType.TAPE                            ) return (pipe == TAPE_RETRO_PIPE);
    //     else if(ty == DetectionType.APRIL_TAG                       ) return (pipe == APRIL_TAG_PIPE);

    //     else return false;
    // }
    public boolean currentPipelineMatches(DetectionType type) 
    {
        int newCamMode = 0;
        if (type == DetectionType.DRIVER) {
            LimelightHelpers.setLimelightNTDouble(cameraName,"camMode",1);
        }
        LimelightHelpers.setLimelightNTDouble(cameraName, "camMode", newCamMode);
        int currentPipe = (int) LimelightHelpers.getCurrentPipelineIndex("limelight");
    
        return type.getPipe() == currentPipe;

    }



}
