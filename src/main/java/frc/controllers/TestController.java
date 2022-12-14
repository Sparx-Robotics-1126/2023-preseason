package frc.controllers;

import frc.robot.Robot;

import frc.drives.DrivesSensorInterface;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controller for testing.
 */
public class TestController extends Controller 
{
    DrivesSensorInterface drivesSensors;

    public TestController() 
    {
        drivesSensors = Robot.getDrives().getSensors();

        SmartDashboard.putNumber("DRIVE_FORWARD", 0);
        SmartDashboard.putNumber("DRIVE_BACKWARD", 0);
        SmartDashboard.putNumber("TURN_RIGHT", 0);
        SmartDashboard.putNumber("TURN_LEFT", 0);
    }

    @Override
    public void execute() 
    {
        SmartDashboard.putNumber("GYRO_ANGLE", drivesSensors.getGyroAngle());

        SmartDashboard.putNumber("DRIVES_LEFT_ENCODER_DISTANCE", drivesSensors.getLeftEncoderDistance());
        SmartDashboard.putNumber("DRIVES_LEFT_ENCODER_SPEED", drivesSensors.getLeftEncoderSpeed());

        SmartDashboard.putNumber("DRIVES_RIGHT_ENCODER_DISTANCE", drivesSensors.getRightEncoderDistance());
        SmartDashboard.putNumber("DRIVES_RIGHT_ENCODER_SPEED", drivesSensors.getRightEncoderSpeed());

        double moveForward = SmartDashboard.getNumber("DRIVE_FORWARD", 0);
        double moveBackward = SmartDashboard.getNumber("DRIVE_BACKWARD", 0);
        double turnLeft = SmartDashboard.getNumber("TURN_LEFT", 0);
        double turnRight = SmartDashboard.getNumber("TURN_RIGHT", 0);

        if (moveForward > 0)
        {
            Robot.getDrives().moveForward(0.5, moveForward);
            SmartDashboard.putNumber("DRIVE_FORWARD", 0);
        }
        else if (moveBackward > 0)
        {
            Robot.getDrives().moveBackward(0.5, moveBackward);
            SmartDashboard.putNumber("DRIVE_BACKWARD", 0);
        }
        else if (turnLeft > 0)
        {
            Robot.getDrives().turnLeft(turnLeft);
            SmartDashboard.putNumber("TURN_LEFT", 0);
        }
        else if (turnRight > 0)
        {
            Robot.getDrives().turnRight(turnRight);
            SmartDashboard.putNumber("TURN_RIGHT", 0);
        }
    }
}
