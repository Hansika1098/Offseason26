package org.firstinspires.ftc.teamcode.Mechanisms.Sorter;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class Sorter {



    ColorSensor sensorColor1;
    ColorSensor sensorColor2;

    sensorColor1 = hardwareMap.get(ColorSensor.class, "color sensor 1");
    sensorColor2 = hardwareMap.get(ColorSensor.class, "color sensor 2");



}
