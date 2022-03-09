package frc.controllers;

import frc.robot.Robot;

import frc.subsystem.Drives;
import frc.subsystem.Acquisitions;
import frc.subsystem.Subsystem.SubsystemState;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleoperatedController extends Controller 
{
	private Joystick joystick;

  private Axis driverLeftAxisY;
  private Axis driverLeftAxisX;
	private Axis driverRightAxis;
  private Axis driverRightTrigger;

  private Button acquisitionsArmButton;
  private Button acquisitionsIntakeButton;

  static 
  {
    SmartDashboard.putBoolean("USE_BOTH_JOYSTICKS", true);
  }
	
	/**
	 * Creates the Controller manager for teleoperated.
	 * @param drives The Drives subsystem to associate with this Controller.
	 */
  public TeleoperatedController()
  {
    joystick = new Joystick(0);

    //For drives.
    driverLeftAxisY = new Axis(joystick, ControllerMappings.XBOX_LEFT_Y, true);
    driverLeftAxisX = new Axis(joystick, ControllerMappings.XBOX_LEFT_X, true);
    driverRightAxis = new Axis(joystick, ControllerMappings.XBOX_RIGHT_Y, true);
    driverRightTrigger = new Axis(joystick, ControllerMappings.XBOX_R2, true);
   

    //For Acquisitions
    acquisitionsArmButton = new Button(joystick, ControllerMappings.XBOX_B);
    acquisitionsIntakeButton = new Button(joystick, ControllerMappings.XBOX_A);

    //Add additional controls here.
  }

  @Override
  public void execute() 
  {
    //Drives
    if (SmartDashboard.getBoolean("USE_BOTH_JOYSTICKS", true))
        Robot.getDrives().setJoysticks(driverLeftAxisY.get(), driverRightAxis.get());
    else
    {
      double leftAxisY = driverLeftAxisY.get();
      double leftAxisX = driverLeftAxisX.get() * 0.5;

      if (Math.abs(leftAxisY) >= 0.15 && leftAxisY < 0)
        leftAxisX = -leftAxisX;

      Robot.getDrives().setJoysticks(leftAxisY - leftAxisX, leftAxisY + leftAxisX);
    }

    //Acquisitions
    if (acquisitionsArmButton.get())
      if (acquisitionsArmButton.previouslyPressed())
        Robot.getAcquisitions().raiseArm();
      else
        Robot.getAcquisitions().dropArm();

    if (acquisitionsIntakeButton.get())
      if (acquisitionsIntakeButton.previouslyPressed())
        Robot.getAcquisitions().stopRollers();
      else
        Robot.getAcquisitions().intakeRollers();

    //Trigger Sensitivity Control
    if (driverRightTrigger.get() <= -0.8)
    {
      driverLeftAxisX.setSensitivity(0.4);
      driverLeftAxisY.setSensitivity(0.4);
      driverRightAxis.setSensitivity(0.4);
    }
    else
    {
      driverLeftAxisX.setSensitivity(1);
      driverLeftAxisY.setSensitivity(1);
      driverRightAxis.setSensitivity(1);
    }
  }
}