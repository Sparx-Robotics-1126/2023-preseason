package frc.shooter.commands;

import frc.shooter.ShooterCommand;
import frc.shooter.ShooterOutput;
import frc.shooter.ShooterSensorInterface;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StaticSpeed extends ShooterCommand
{
    static
    {
        SmartDashboard.putNumber("SHOOTER_SPEED", 0.15);
    }

    public StaticSpeed(ShooterSensorInterface sensors)
    {
        super(sensors);
    }

    @Override
    public ShooterOutput execute() 
    {
        return new ShooterOutput(SmartDashboard.getNumber("SHOOTER_SPEED", 0.15), false);
    }
}