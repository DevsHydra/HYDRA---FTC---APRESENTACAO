package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Apresentacao extends LinearOpMode {

    private DcMotor LMF;    //Motor - Esquerda  Front
    private DcMotor LMB;    //Motor - Esquerda  Back
    private DcMotor RMF;    //Motor - Direita   Front
    private DcMotor RMB;    //Motor - Direita   Back
    private DcMotor MMT;    //Motor - Movimentação Tanque
    private DcMotor MDP;    //Motor - Derrubar Pato
    private DcMotor MC;     //Motor - Coletar


    boolean toggle = false;

    @Override
    public void runOpMode() {
        LMF = hardwareMap.get(DcMotor.class, "LMF");
        LMB = hardwareMap.get(DcMotor.class, "LMB");
        RMF = hardwareMap.get(DcMotor.class, "RMF");
        RMB = hardwareMap.get(DcMotor.class, "RMB");
        MMT = hardwareMap.get(DcMotor.class,"MMT");
        MC = hardwareMap.get(DcMotor.class,"MC");
        MDP = hardwareMap.get(DcMotor.class, "MDP");

        LMF.setDirection(DcMotorSimple.Direction.REVERSE);
        LMB.setDirection(DcMotorSimple.Direction.REVERSE);
        RMF.setDirection(DcMotorSimple.Direction.FORWARD);
        RMB.setDirection(DcMotorSimple.Direction.FORWARD);
        MMT.setDirection(DcMotorSimple.Direction.FORWARD);
        MC.setDirection(DcMotorSimple.Direction.FORWARD);
        MDP.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                double TickAng = 1120 / 360;
                double gmax = TickAng * 90;
                double y = -gamepad1.left_stick_y;
                double x = gamepad1.left_stick_x;
                double x2 = gamepad1.right_stick_x;
                double y2 = -gamepad1.right_stick_y;
                double Redutor = 0;

//==================================================================================================
////REDUTOR DE VELOCIDADE
//                if(!toggle) {
//                if (gamepad1.b) {
//                    Redutor = 1;
//                    toggle = true;
//                } else if (gamepad1.a) {
//                    Redutor = 2;
//                    toggle = true;
//                } else if (gamepad1.x) {
//                    Redutor = 4;
//                    toggle = true;
//                }
//                }  else if(!gamepad1.x && !gamepad1.a && !gamepad1.b) toggle = false;
                if(gamepad1.left_bumper){
                    Redutor = 4;
                }else if(gamepad1.right_bumper){
                    Redutor = 2;
                }else{
                    Redutor = 1;
                }

//==================================================================================================
//LOCOMOCAO

                RMF.setPower(((y - x) - x2) / Redutor);
                LMF.setPower(((y + x) + x2) / Redutor);
                RMB.setPower(((y + x) - x2) / Redutor);
                LMB.setPower(((y - x) + x2) / Redutor);
//                if(!gamepad1.right_bumper && !gamepad1.left_bumper){MMT.setPower(0);}
//                else if(gamepad1.right_bumper){MMT.setPower(1);}
//                else if(gamepad1.left_bumper){MMT.setPower(-1);}
                telemetry.update();

//==================================================================================================
//COLETAR
                if((gamepad1.right_trigger <0.5) && (gamepad1.left_trigger <0.5)){
                    MC.setPower(0);
                }
                if(gamepad1.right_trigger >= 0.5){
                    MC.setPower(gamepad1.right_trigger);
                }
                else if(gamepad1.left_trigger >= 0.5){
                    MC.setPower(-gamepad1.left_trigger);
                }

//==================================================================================================
// DERRUBAR PATO

                if(gamepad1.dpad_up){
                    MDP.setPower(1);
                }
                else if(gamepad1.dpad_down){
                    MDP.setPower(-1);
                }else {
                    MDP.setPower(0);
                }
            }
        }
    }
}