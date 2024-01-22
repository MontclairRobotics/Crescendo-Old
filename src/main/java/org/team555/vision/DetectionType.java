package org.team555.vision;

public enum DetectionType {
    NONE(-1),
    NOTE(0),
    APRIL_TAG(1);

    private final int pipe;
    private DetectionType(int pipe) {
        this.pipe = pipe;
    }

    public int getPipe() {
        return pipe;
    }



}
