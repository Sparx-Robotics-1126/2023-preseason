package frc.subsystem;

import frc.robot.IO;

import frc.drives.DrivesCommand;
import frc.drives.DrivesOutput;
import frc.drives.DrivesSensorInterface;

import frc.drives.commands.TurnLeft;
import frc.drives.commands.TurnRight;
import frc.drives.commands.DriveBackwards;
import frc.drives.commands.DriveForward;
import frc.drives.commands.DriverControlled;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * Controls all drives behavior.
 */
public class Drives extends Subsystem 
{
    /**
     * The maximum amount of current in amps that should be permitted during motor operation.
     */
    private static final int MAX_CURRENT = 30;
 
    /**
     * The ideal voltage that the motors should attempt to match.
     */
    private static final double NOMINAL_VOLTAGE = 12;

    /**
     * The current DrivesCommand being ran. Examples include DriveForward, TurnRight, DriverControlled, etc..
     */
    private DrivesCommand drivesCommand;

    /*
     * Class containing all sensor data for drives. Examples include encoders, limit switches, vision, etc..
     */
    private DrivesSensorInterface drivesSensors;

    //Put motor initialization here.
    private CANSparkMax rightMotorMaster;
    private CANSparkMax leftMotorMaster;

    /**
     * Main initializer for the drives subsystem. Called in Robot.java.
     * @param driveSensors The sensors to use for the drives subsystem.
     */
    public Drives(DrivesSensorInterface driveSensors) 
    {
        rightMotorMaster = new CANSparkMax(IO.DRIVES_RIGHT_MOTOR_1, MotorType.kBrushless);
        CANSparkMax rightMotorSlave = new CANSparkMax(IO.DRIVES_RIGHT_MOTOR_2, MotorType.kBrushless);
        configureMotor(rightMotorMaster, rightMotorSlave);

        leftMotorMaster = new CANSparkMax(IO.DRIVES_LEFT_MOTOR_1, MotorType.kBrushless);
        CANSparkMax leftMotorSlave = new CANSparkMax(IO.DRIVES_LEFT_MOTOR_2, MotorType.kBrushless);
        configureMotor(leftMotorMaster, leftMotorSlave);

        driveSensors.addEncoders(leftMotorMaster.getEncoder(), rightMotorMaster.getEncoder());
        drivesSensors = driveSensors;
    }

    /**
     * Configures motors to follow one controller.
     * @param master The controller to follow.
     * @param slaves The controllers that should follow master.
     */
    private static void configureMotor(CANSparkMax master, CANSparkMax... slaves) 
    {
        master.restoreFactoryDefaults();
        master.set(0);
        master.setIdleMode(IdleMode.kCoast);
        master.enableVoltageCompensation(NOMINAL_VOLTAGE);
        master.setSmartCurrentLimit(MAX_CURRENT);
        master.setOpenLoopRampRate(1);

        for (CANSparkMax slave : slaves) 
        {
            slave.restoreFactoryDefaults();
            slave.follow(master);
            slave.setIdleMode(IdleMode.kCoast);
            slave.setSmartCurrentLimit(MAX_CURRENT);
            slave.setOpenLoopRampRate(1);
        }
    }

    /**
     * @return The DrivesSensorInterface being used by this Drives instance.
     */
    public DrivesSensorInterface getSensors()
    {
        return drivesSensors;
    }

    @Override
    void execute() 
    {
        if (drivesCommand != null) 
        {
            DrivesOutput output = drivesCommand.execute();
            
            leftMotorMaster.set(output.getLeftMotor());
            rightMotorMaster.set(-output.getRightMotor());

            if (output.isDone()) 
            {
                leftMotorMaster.set(0);
                rightMotorMaster.set(0);
                drivesCommand = null;
            }
        }
    }

    /**
     * Signals when the current command is done executing (if applicable).
     */
    @Override
    public boolean isDone() 
    {
        return drivesCommand == null;
    }

    /**
     * Configures the joysticks that control the wheels of the robot.
     * @param left The joystick for the left wheels.
     * @param right The joystick for the right wheels.
     */
    public void setJoysticks(double left, double right) 
    {
        drivesSensors.setLeftJoystick(left);
        drivesSensors.setRightJoystick(right);
    }

    /**
     * Moves the robot forward by the set distance.
     * @param speed The speed at which to drive forward.
     * @param distance The distance in motor shaft rotations to move forward.
     */
    public void moveForward(double speed, double distance) 
    {
        drivesCommand = new DriveForward(drivesSensors, speed, distance);
    }

    /**
     * Moves the robot backward by the set distance.
     * @param speed The speed at which to drive backward.
     * @param distance The distance in motor shaft rotations to move backward.
     */
    public void moveBackward(double speed, double distance) 
    {
        drivesCommand = new DriveBackwards(drivesSensors, speed, distance);
    }

    /**
     * Turns the robot right by angle.
     * @param angle The angle by which to turn right.
     */
    public void turnRight(double angle) 
    {
        drivesCommand = new TurnRight(drivesSensors, 1, angle);
    }

    /**
     * Turns the robot left by angle.
     * @param angle The angle by which to turn left.
     */
    public void turnLeft(double angle) 
    {
        drivesCommand = new TurnLeft(drivesSensors, 1, angle);
    }

    /**
     * Engages teleoperated mode for drives.
     */
    public void startDriverControlled() 
    {
        drivesCommand = new DriverControlled(drivesSensors);
    }
}