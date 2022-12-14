package github.foehammer127svg.otterlib;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MechanemDrivetrain implements Drivetrain {
    public DcMotor rightFront, leftFront, rightBack, leftBack;

    public double vMulti = 1;
    public double hMulti = 1;
    public double rMulti = 1;
    @Override
    public void init(HardwareMap hardwareMap) {
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
    }

    public MechanemDrivetrain(double vMulti, double hMulti, double rMulti) {
        this.vMulti = vMulti;
        this.hMulti = hMulti;
        this.rMulti = rMulti;
    }
    public MechanemDrivetrain() {}

    public void Drive(Gamepad g) {
        double v = g.left_stick_y * vMulti;
        double h = g.left_stick_x * hMulti;
        double r = g.right_stick_x * rMulti;

        // Correct Imperfect Strafing
        h = h * 1.1;

        // Stop Power from being > 1
        double denominator = Math.max(Math.abs(v) + Math.abs(h) + Math.abs(r), 1);
        SetMotorPower((v+h+r)/denominator,(v-h-r)/denominator,(v-h+r)/denominator,(v+h-r)/denominator);



    }
    public void SetMotorPower(double lnose_power, double rnose_power, double lrear_power, double rrear_power) {
        leftFront.setPower(lnose_power);
        rightBack.setPower(rrear_power);
        rightFront.setPower(rnose_power);
        leftBack.setPower(lrear_power);
    }
}
