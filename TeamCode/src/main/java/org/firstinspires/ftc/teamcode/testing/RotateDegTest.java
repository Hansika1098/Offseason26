package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "spin 120 degrees test", group = "Sorter")
public class RotateDegTest extends OpMode {
    private DcMotorEx spinner;

    private static final double TICKS_PER_REV = 383.5;
    private static final double TICKS_PER_DEGREE = TICKS_PER_REV / 360.0;
    private static final int TICKS_FOR_120 = (int) Math.round(120 * TICKS_PER_DEGREE);

    private boolean lastX = false;

    @Override
    public void init() {
        spinner = hardwareMap.get(DcMotorEx.class, "spinnerMotor");

        spinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spinner.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spinner.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        if (gamepad1.x && !lastX) {
            int target = spinner.getCurrentPosition() + TICKS_FOR_120;

            spinner.setTargetPosition(target);
            spinner.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            spinner.setPower(0.25);
        }

        if (!spinner.isBusy()) {
            spinner.setPower(0);
        }

        lastX = gamepad1.x;

        telemetry.addData("Current ticks", spinner.getCurrentPosition());
        telemetry.addData("Ticks for 120", TICKS_FOR_120);
        telemetry.addData("Busy", spinner.isBusy());
        telemetry.update();
    }
}
