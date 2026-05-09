package org.firstinspires.ftc.teamcode.Mechanisms.Sorter;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import edu.ftcphoenix.fw.core.time.LoopClock;
import edu.ftcphoenix.fw.input.Gamepads;
import edu.ftcphoenix.fw.input.binding.Bindings;

@TeleOp(name = "Sorter Test Teleop", group = "Sorter")
public final class sorterTestTeleop extends OpMode {

    private final LoopClock clock = new LoopClock();

    private Gamepads gamepads;
    private Bindings bindings;

    private Sorter sorter;
    private SorterImpl debugSorter;
    private String lastCommandResult = "none";

    @Override
    public void init() {
        clock.reset(getRuntime());

        gamepads = Gamepads.create(gamepad1, gamepad2);
        bindings = new Bindings();

        sorter = new SorterImpl(hardwareMap, clock);
        debugSorter = (SorterImpl) sorter;

        bindings.onRise(gamepads.p1().a(), () -> sorter.startCalibration());
        bindings.onRise(gamepads.p1().b(), () -> sorter.startInventoryScan());
        bindings.onRise(gamepads.p1().x(), () -> sorter.tryPrepareLoad());
        bindings.onRise(gamepads.p1().y(), () -> sorter.tryFeed());

        bindings.onRise(gamepads.p1().dpadLeft(), () ->
                lastCommandResult = "Present 0: " + sorter.tryPresentSlot(0));
        bindings.onRise(gamepads.p1().dpadUp(), () ->
                lastCommandResult = "Present 1: " + sorter.tryPresentSlot(1));
        bindings.onRise(gamepads.p1().dpadRight(), () ->
                lastCommandResult = "Present 2: " + sorter.tryPresentSlot(2));

        bindings.onRise(gamepads.p1().rightBumper(), sorter::confirmShotComplete);
        bindings.onRise(gamepads.p1().leftBumper(), sorter::clearInventory);
    }

    @Override
    public void loop() {
        clock.update(getRuntime());

        // Handle buttons first so the command applies in this same loop.
        bindings.update(clock);

        sorter.update();

        renderTelemetry();
    }

    private void renderTelemetry() {
        SorterStatus status = sorter.getStatus();

        telemetry.addLine("Sorter Debug");
        telemetry.addData("Action", status.getAction());
        telemetry.addData("Calibration", status.getCalibrationState());

        telemetry.addData("Load Slot", status.getLoadSlot());
        telemetry.addData("Shoot Slot", status.getShootSlot());
        telemetry.addData("Load Aligned", status.isLoadAligned());
        telemetry.addData("Shoot Aligned", status.isShootAligned());

        telemetry.addLine("--- Scan Debug ---");
        telemetry.addData("Scan Passes", debugSorter.getDebugCompletedScanPasses());
        telemetry.addData("Slot Pass Active", debugSorter.isDebugSlotPassActive());
        telemetry.addData("Active Pass Slot", debugSorter.getDebugActivePassSlot());
        telemetry.addData("Current Slot", debugSorter.getDebugCurrentSlot());
        telemetry.addData("Spinner Ticks", debugSorter.getDebugSpinnerTicks());
        telemetry.addData("Current Angle", "%.2f", debugSorter.getDebugCurrentAngle());
        telemetry.addData("Spinner Power", "%.2f", debugSorter.getDebugSpinnerPower());

        telemetry.addLine("--- Inventory ---");
        telemetry.addData("Slot 0", sorter.getSlotContent(0));
        telemetry.addData("Slot 1", sorter.getSlotContent(1));
        telemetry.addData("Slot 2", sorter.getSlotContent(2));

        telemetry.addLine("--- Controls ---");
        telemetry.addLine("A calibrate");
        telemetry.addLine("B inventory scan");
        telemetry.addLine("X prepare load");
        telemetry.addLine("Y feed");
        telemetry.addLine("Dpad L/U/R present 0/1/2");

        telemetry.update();
    }
}