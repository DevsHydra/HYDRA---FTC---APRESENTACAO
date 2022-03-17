package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class MecanumTestes extends LinearOpMode {
    private DcMotor LMF;    //Motor - Esquerda  Front
    private DcMotor LMB;    //Motor - Esquerda  Back
    private DcMotor RMF;    //Motor - Direita   Front
    private DcMotor RMB;    //Motor - Direita   Back
    private DcMotor MMT;    //Motor - Movimentação Tanque
    private DcMotor MDP;    //Motor - Derrubar Pato
    private DcMotor MC;     //Motor - Coletar
    private DcMotor MMG;    //Motor - Movimentar Garra


    //private DcMotor ML;

    double speed;
    boolean toggle = false;

    public void runOpMode(){
        LMF = hardwareMap.get(DcMotor.class, "LMF");
        LMB = hardwareMap.get(DcMotor.class, "LMB");
        RMF = hardwareMap.get(DcMotor.class, "RMF");
        RMB = hardwareMap.get(DcMotor.class, "RMB");
        MMT = hardwareMap.get(DcMotor.class, "MMT");
        MDP = hardwareMap.get(DcMotor.class, "MDP");
        MC = hardwareMap.get(DcMotor.class, "MC");

        //ML = hardwareMap.get(DcMotor.class, "ML");
        LMF.setDirection(DcMotorSimple.Direction.FORWARD);
        LMB.setDirection(DcMotorSimple.Direction.FORWARD);
        RMF.setDirection(DcMotorSimple.Direction.FORWARD);
        RMB.setDirection(DcMotorSimple.Direction.FORWARD);
        MMT.setDirection(DcMotorSimple.Direction.FORWARD);
        MDP.setDirection(DcMotorSimple.Direction.FORWARD);
        MC.setDirection(DcMotorSimple.Direction.FORWARD);
        //ML.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        while(opModeIsActive()){
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double f = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
            double cos = x/f;
            double d = Math.acos(cos);//cos-1 arco

            final double pi = Math.PI;
            double sen = y/f;
            double x2 = gamepad1.right_stick_x;
            double y2 = -gamepad1.right_stick_y;
            double d2 = Math.sqrt(Math.pow(x2,2) + Math.pow(y2,2));
            double sen2 = y2/d2;
            double angulorad = Math.asin(sen);
            double angulo = angulorad * (180/pi);
            double fMD = 0;
            double fME = 0;
            double forceMotorR = 0;
            double forceMotorL = 0;

            double mlf = 0;

            //Control Speed

            // gamepad1.b ? speed = 1 : gamepad1.a ? speed = 1/2 : gamepad1.x ? speed = 1/4 ;
            /*
            if(!toggle) {
                if(gamepad1.x) {
                    speed = 1 / 4;
                    toggle = true;
                }
                if(gamepad1.a) {
                    speed = 1 / 2;
                    toggle = true;
                }
                if(gamepad1.b) {
                    speed = 1;
                    toggle = true;
                }
            } else if(!gamepad1.x && !gamepad1.a && !gamepad1.b) toggle = false;
            */
            // Funções Mecanum

            //1º QUADRANTE
            if((x >= 0) && (y >= 0)) {
                fME = f;
                fMD = (((4/pi)*d)-1);
            }
            //2º QUADRANTE
            else if ((x <= 0) && (y >= 0)) {
                fME = (((-1*(4/pi))*d)-1);
                fMD = f;
            }
            //3º QUADRANTE
            else if ((x <= 0) && (y <= 0)) {
                fME = -f;
                fMD = (((-1*(4/(3*pi)))*d)+1);
            }
            //4º QUADRANTE
            else if ((x >= 0) && (y <= 0)) {
                fME = (((-1*(4/(3*pi)))*d)+1);
                fMD = -f;
            }
            RMF.setPower(fMD/2);
            LMF.setPower(fME/2);
            RMB.setPower(fME/2);
            LMB.setPower(fMD/2);
/*
            // Funções Normais

            //1º QUADRANTE
            if((x2 >= 0 ) && (y2 >= 0)){
                forceMotorR = ((2 * sen2) - 1) * d2;
                forceMotorL = d2;
            }
            // 2º QUADRANTE
            else if((x2 < 0 ) && (y2 >= 0)) {
                forceMotorR = d2;
                forceMotorL = ((2 * sen2) - 1) * d2;
            }
            // 3º QUADRANTE
            else if((x2 < 0 ) && (y2 < 0)){
                forceMotorR = -d2;
                forceMotorL = ((2 * sen2) + 1) * d2;
            }
            // 4º QUADRANTE
            else if((x2 >= 0) && (y2 < 0)) {
                forceMotorR = ((2 * sen2) + 1) * d2;
                forceMotorL = -d2;
            }


 */
            // força esteira
            if(gamepad1.right_trigger > 0.2){
                MMT.setPower(gamepad1.right_trigger);
                MDP.setPower(gamepad1.right_trigger);
                MC.setPower(gamepad1.right_trigger);
            }
            else if(gamepad1.left_trigger > 0){
                MMT.setPower(-gamepad1.left_trigger);
                MDP.setPower(-gamepad1.left_trigger);
                MC.setPower(-gamepad1.left_trigger);
            }

            // Funções Linear
            /*
            if(!toggle) {
                if(gamepad2.x) {
                    mlf = 0.3;
                    toggle = true;
                }
                if(gamepad2.a) {
                    mlf = 0.5;
                    toggle = true;
                }
                if(gamepad2.b) {
                    mlf = 0.7;
                    toggle = true;
                }
            } else if(!gamepad2.x && !gamepad2.a && !gamepad2.b) toggle = false;


            if(gamepad2.dpad_up) {
                ML.setPower(mlf);
            }
            else if(gamepad2.dpad_down){
                ML.setPower(-mlf);
            }

            */

            // Set Force
            /*
            if(fMD != 0 && fME != 0){
                RMF.setPower(fMD * speed);
                LMF.setPower(fME * speed);
                RMB.setPower(fME * speed);
                LMB.setPower(fMD * speed);
            }else{
                RMF.setPower(forceMotorR * speed);
                RMB.setPower(forceMotorR * speed);
                LMF.setPower(forceMotorL * speed);
                LMB.setPower(forceMotorL * speed);
            }

             */

        }
    }
}
