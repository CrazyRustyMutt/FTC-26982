package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TeleOpTwoControllersTikiTechSHIDID (Blocks to Java)")
public class TeleOpTwoControllersTikiTechSHIDID extends LinearOpMode {

    private DcMotor Motor0SHLDR;
    private DcMotor Motor1EXT;
    private DcMotor Motor22ndEXT;
    private DcMotor Motor1FR;
    private DcMotor Motor3BR;
    private DcMotor Motor0FL;
    private DcMotor Motor2BL;
    private CRServo Servo0WRIST;
    private CRServo servo1;
    private CRServo servo2;
    private CRServo servo3;

    int maxDrivePower;

    /**
     * Describe this function...
     */
    private void Arm_PID(int shouldertarget, int exttarget) {
        // Experimental PID code
        Motor0SHLDR.setTargetPosition(shouldertarget);
        Motor1EXT.setTargetPosition(-exttarget);
        Motor22ndEXT.setTargetPosition(-exttarget);
        Motor0SHLDR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motor1EXT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motor22ndEXT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // If the arm is gonna go down, we do ext then shoulder. If its gonna go up, we do shoulder then ext; the following code does this
        if (Motor0SHLDR.getCurrentPosition() < shouldertarget) {
            Motor0SHLDR.setPower(1);
            while (opModeIsActive() && Motor0SHLDR.isBusy()) {
                Mecanum_Drive();
                Head();
                telemetry2();
                if (gamepad1.back) {
                    break;
                }
            }
            Motor0SHLDR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Motor0SHLDR.setPower(0);
            Motor1EXT.setPower(1);
            Motor22ndEXT.setPower(1);
            while (opModeIsActive() && (Motor1EXT.isBusy() || Motor22ndEXT.isBusy())) {
                Mecanum_Drive();
                Head();
                telemetry2();
                if (gamepad1.back) {
                    break;
                }
            }
            Motor1EXT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Motor1EXT.setPower(0);
            Motor22ndEXT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Motor22ndEXT.setPower(0);
        } else {
            Motor1EXT.setPower(1);
            Motor22ndEXT.setPower(1);
            while (opModeIsActive() && (Motor1EXT.isBusy() || Motor22ndEXT.isBusy())) {
                Mecanum_Drive();
                Head();
                telemetry2();
                if (gamepad1.back) {
                    break;
                }
            }
            Motor1EXT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Motor1EXT.setPower(0);
            Motor22ndEXT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Motor22ndEXT.setPower(0);
            Motor0SHLDR.setPower(1);
            while (opModeIsActive() && Motor0SHLDR.isBusy()) {
                Mecanum_Drive();
                Head();
                telemetry2();
                if (gamepad1.back) {
                    break;
                }
            }
            Motor0SHLDR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Motor0SHLDR.setPower(0);
        }
    }

    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        Motor0SHLDR = hardwareMap.get(DcMotor.class, "Motor-0-SHLDR");
        Motor1EXT = hardwareMap.get(DcMotor.class, "Motor-1-EXT");
        Motor22ndEXT = hardwareMap.get(DcMotor.class, "Motor-2-2ndEXT");
        Motor1FR = hardwareMap.get(DcMotor.class, "Motor-1-FR");
        Motor3BR = hardwareMap.get(DcMotor.class, "Motor-3-BR");
        Motor0FL = hardwareMap.get(DcMotor.class, "Motor-0-FL");
        Motor2BL = hardwareMap.get(DcMotor.class, "Motor-2-BL");
        Servo0WRIST = hardwareMap.get(CRServo.class, "Servo-0-WRIST");
        servo1 = hardwareMap.get(CRServo.class, "servo1");
        servo2 = hardwareMap.get(CRServo.class, "servo2");
        servo3 = hardwareMap.get(CRServo.class, "servo3");

        // Put initialization blocks here.
        Motor1FR.setDirection(DcMotor.Direction.REVERSE);
        Motor3BR.setDirection(DcMotor.Direction.REVERSE);
        Motor1EXT.setDirection(DcMotor.Direction.FORWARD);
        Motor22ndEXT.setDirection(DcMotor.Direction.REVERSE);
        Motor0SHLDR.setDirection(DcMotor.Direction.REVERSE);
        Motor0FL.setDirection(DcMotor.Direction.FORWARD);
        Motor2BL.setDirection(DcMotor.Direction.FORWARD);
        Servo0WRIST.setDirection(CRServo.Direction.FORWARD);
        servo1.setDirection(CRServo.Direction.REVERSE);
        servo2.setDirection(CRServo.Direction.FORWARD);
        servo3.setDirection(CRServo.Direction.FORWARD);
        Motor0SHLDR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motor1EXT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motor22ndEXT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motor0SHLDR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Motor1EXT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Motor22ndEXT.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        maxDrivePower = 1;
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                Mecanum_Drive();
                telemetry2();
                // Arm Systems
                if (gamepad1.right_stick_button || gamepad2.right_stick_button) {
                    if (gamepad1.dpad_up || gamepad2.dpad_up) {
                        Motor0SHLDR.setPower(maxDrivePower / 2);
                    } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                        Motor0SHLDR.setPower(-(maxDrivePower / 3));
                    } else {
                        Motor0SHLDR.setPower(0);
                    }
                } else {
                    if (gamepad1.dpad_up || gamepad2.dpad_up) {
                        Motor0SHLDR.setPower(maxDrivePower);
                    } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                        Motor0SHLDR.setPower(-maxDrivePower);
                    } else {
                        Motor0SHLDR.setPower(0);
                    }
                }
                if (gamepad1.dpad_left || gamepad2.dpad_left) {
                    Motor1EXT.setPower(1);
                    Motor22ndEXT.setPower(1);
                } else if (gamepad1.dpad_right || gamepad2.dpad_right) {
                    Motor1EXT.setPower(-1);
                    Motor22ndEXT.setPower(-1);
                } else {
                    Motor1EXT.setPower(0);
                    Motor22ndEXT.setPower(0);
                }
                Head();
                // Automated Actions
                // All positions are place-holders as of 29DEC24
                if (gamepad1.x || gamepad2.x) {
                    // Intake From Floor
                    if (Motor0SHLDR.getCurrentPosition() < 1000) {
                        Servo0WRIST.setPower(0.05);
                        Arm_PID(1000, 0);
                        Arm_PID(0, 0);
                    } else {
                        Servo0WRIST.setPower(0.05);
                        Arm_PID(0, 0);
                    }
                }
                if (gamepad1.y || gamepad2.y) {
                    // Outtake High Bucket
                    if (gamepad1.right_stick_button || gamepad2.right_stick_button) {
                        Servo0WRIST.setPower(0.62);
                        Arm_PID(1000, 0);
                        Arm_PID(4800, 5000);
                    } else {
                        Servo0WRIST.setPower(0.62);
                        Arm_PID(4800, 5000);
                    }
                }
                if (gamepad1.a || gamepad2.a) {
                    // Intake Wall
                    Servo0WRIST.setPower(0.62);
                    Arm_PID(1000, 0);
                }
                if (gamepad1.b || gamepad2.b) {
                    // Outtake High Chamber
                    Servo0WRIST.setPower(0.25);
                    Arm_PID(3000, 370);
                }
                if (gamepad1.right_stick_button || gamepad2.right_stick_button) {
                    Servo0WRIST.setPower((Servo0WRIST.getPower() - gamepad2.right_trigger) / 2);
                    Servo0WRIST.setPower((Servo0WRIST.getPower() - gamepad1.right_trigger) / 2);
                }
                // Arm Telemetry Reset (do at Intake position)
                if (gamepad1.start && gamepad1.back || gamepad2.start && gamepad2.back) {
                    Motor0SHLDR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    Motor1EXT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    Motor22ndEXT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                }
            }
        }
    }

    /**
     * Describe this function...
     */
    private void Mecanum_Drive() {
        // Mechanum Omnidirectional Drive
        if (gamepad1.left_stick_button) {
            Motor0FL.setPower(Math.pow(gamepad1.left_stick_y + (-gamepad1.left_stick_x - gamepad1.right_stick_x), 3) * 0.5);
            Motor1FR.setPower(Math.pow(gamepad1.left_stick_y - (-gamepad1.left_stick_x - gamepad1.right_stick_x), 3) * 0.5);
            Motor2BL.setPower(Math.pow(gamepad1.left_stick_y - (-gamepad1.left_stick_x + gamepad1.right_stick_x), 3) * 0.5);
            Motor3BR.setPower(Math.pow(gamepad1.left_stick_y + -gamepad1.left_stick_x + gamepad1.right_stick_x, 3) * 0.5);
        } else {
            Motor0FL.setPower(Math.pow(gamepad1.left_stick_y + (-gamepad1.left_stick_x - gamepad1.right_stick_x), 5) * maxDrivePower);
            Motor1FR.setPower(Math.pow(gamepad1.left_stick_y - (-gamepad1.left_stick_x - gamepad1.right_stick_x), 5) * maxDrivePower);
            Motor2BL.setPower(Math.pow(gamepad1.left_stick_y - (-gamepad1.left_stick_x + gamepad1.right_stick_x), 5) * maxDrivePower);
            Motor3BR.setPower(Math.pow(gamepad1.left_stick_y + -gamepad1.left_stick_x + gamepad1.right_stick_x, 5) * maxDrivePower);
        }
    }

    /**
     * Describe this function...
     */
    private void Head() {
        if (gamepad1.left_bumper || gamepad2.left_bumper) {
            servo3.setPower(1);
            servo2.setPower(-1);
        } else if (gamepad1.right_bumper || gamepad2.right_bumper) {
            servo3.setPower(-1);
            servo2.setPower(1);
        } else {
            servo3.setPower(0);
            servo2.setPower(0);
        }
        servo1.setPower((gamepad2.left_trigger - 0.1) / 2);
        servo1.setPower((gamepad1.left_trigger - 0.1) / 2);
    }

    /**
     * Describe this function...
     */
    private void telemetry2() {
        telemetry.update();
        telemetry.addData("Shoulder", Motor0SHLDR.getCurrentPosition());
        telemetry.addData("EXT1", Motor1EXT.getCurrentPosition());
        telemetry.addData("EXT2", Motor22ndEXT.getCurrentPosition());
    }
}