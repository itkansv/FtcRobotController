/*
Copyright 2025 FIRST Tech Challenge Team FTC

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.util.List;
import java.util.ArrayList;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.vision.Builder;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * This file contains a minimal example of a Linear "OpMode". An OpMode is a 'program' that runs
 * in either the autonomous or the TeleOp period of an FTC match. The names of OpModes appear on
 * the menu of the FTC Driver Station. When an selection is made from the menu, the corresponding
 * OpMode class is instantiated on the Robot Controller and executed.
 *
 * Remove the @Disabled annotation on the next line or two (if present) to add this OpMode to the
 * Driver Station OpMode list, or add a @Disabled annotation to prevent this OpMode from being
 * added to the Driver Station.
 */

@TeleOp(name = "Distance Sensor Example", group = "Examples")
public class DistanceSensorExample extends LinearOpMode {

    private RevColorSensorV3 distanceSensor;
    AprilTagProcessor myAprilTagProcessor;


    public void initializeVisionPortal(){
      myAprilTagProcessor = new AprilTagProcessor.Builder().build();
      VisionPortal myVisionPortal;
      myVisionPortal = VisionPortal.easyCreateWithDefaults(
          hardwareMap.get(WebcamName.class, "Webcam1"), myAprilTagProcessor);
    }


    @Override
    public void runOpMode() {
        
        initializeVisionPortal();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        distanceSensor = hardwareMap.get(RevColorSensorV3.class, "colorsensor");
        
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double distCm = distanceSensor.getDistance(DistanceUnit.CM);
            int r = distanceSensor.red();
            int g = distanceSensor.green();
            int b = distanceSensor.blue();
            float[] hsv = new float[3];
            String colorDesc = "n/a";
            android.graphics.Color.RGBToHSV(r, g, b, hsv);
            colorDesc = String.format("R:%d G:%d B:%d HSV:%.0f,%.0f,%.0f", r, g, b, hsv[0], hsv[1]*100, hsv[2]*100);
            
            telemetry.addData("Status", "Running");
            telemetry.addData("Distance (cm)", "%.2f", distCm);
            telemetry.addData("Color", colorDesc);
            
            ArrayList<AprilTagDetection> myAprilTagDetections = (myAprilTagProcessor.getDetections());
            for (AprilTagDetection myAprilTagDetection : myAprilTagDetections) {
                telemetry.addData("ID", (myAprilTagDetection.id));
                telemetry.addData("Range", (myAprilTagDetection.ftcPose.range));
                telemetry.addData("Yaw", (myAprilTagDetection.ftcPose.yaw));
            }
            
            telemetry.update();

        }
    }
}
