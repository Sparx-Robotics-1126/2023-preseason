package frc.drives.commands;

import frc.drives.DrivesCommand;
import frc.drives.DrivesOutput;
import frc.drives.DrivesSensorInterface;

/**
 * DrivesCommand to manually instruct the robot to turn right.
 */
public class TurnRight extends DrivesCommand
{
	private double minSpeed = 0.3;
	private final double SPEED;
	private double finalAngle = 0;
	private double stopAngle = 90;
	private double speedToStop = 0;
	private double distanceToStop = 0;

	/**
	 * Creates a DrivesCommand instructing the robot to turn right by the specified angle.
	 * @param sensors A DrivesSensorInterface containing the sensors that should be used with this DrivesCommand.
	 * @param speed The speed at which to turn by.
	 * @param angle The amount of degrees that the robot should turn.
	 */
	public TurnRight(DrivesSensorInterface sensor, double speed, double angle) 
	{
		super(sensor);
		this.SPEED = speed;
		finalAngle = sensors.getGyroAngle() + angle;
	}

	@Override
	public DrivesOutput execute() 
	{
		distanceToStop = Math.abs(finalAngle - sensors.getGyroAngle());

		if (sensors.getGyroAngle() >= finalAngle)
			return new DrivesOutput(0, 0, true);
		else if (distanceToStop <= stopAngle)
		{
			speedToStop = distanceToStop / stopAngle;

			//Slow down the robot when we near the desired angle to avoid overshooting it.
			if (speedToStop < minSpeed)
				speedToStop = minSpeed;

			return new DrivesOutput(speedToStop, -speedToStop);
		}
			
		return new DrivesOutput(SPEED, -SPEED);
	}
}