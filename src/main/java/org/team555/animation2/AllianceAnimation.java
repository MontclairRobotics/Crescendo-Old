package org.team555.animation2;

import java.util.Optional;

import org.team555.animation2.api.AnimationBase;
import org.team555.animation2.api.LEDBuffer;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * The default animation for the LEDs.
 * Takes the current alliance color from the driver station and sets the LEDs to the correct color.
 */
public class AllianceAnimation extends AnimationBase
{
    @Override
    public void render() 
    {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        Color color = Color.kDimGray;
        //TODO check if this works I changed it - Abe
        if (alliance.isPresent()) {
            if (alliance.get() == Alliance.Blue) color = Color.kBlue; 
            else if (alliance.get() == Alliance.Red) color = Color.kRed;
        }
        else color = Color.kWhite;

        LEDBuffer.fill(getBuffer(), color);
    }
}
