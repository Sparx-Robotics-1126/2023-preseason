package frc.storage;

import frc.robot.IO;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class StorageSensors implements StorageSensorInterface
{
	private Encoder storageEncoder;

    private DigitalInput storageIR;

    /**
	 * Creates a new object for interfacing with the various sensors of the Storage subsystem (e.g. encoders).
	 */
	public StorageSensors() 
	{
		storageIR = new DigitalInput(IO.STORAGE_IR);
	}

	/**
	 * Gets the distance the storage motor has rotated.
	 */
	@Override
	public double getStorageEncoderDistance() 
	{
		return storageEncoder.getDistance();
	}

	@Override
	public boolean getStorageIRSensor() 
	{
        return !storageIR.get();
	}
}