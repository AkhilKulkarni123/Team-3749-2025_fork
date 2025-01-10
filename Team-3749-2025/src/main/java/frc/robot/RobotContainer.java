// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.lang.ModuleLayer.Controller;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.auto.AutoUtils;
import frc.robot.commands.auto.Autos;
import frc.robot.commands.elevator.elevatorstop;
import frc.robot.commands.elevator.movetosetpoint;
import frc.robot.commands.elevator.setstate;
import frc.robot.subsystems.elevator.ElevatorConstants.ElevatorStates;
import frc.robot.utils.JoystickIO;

public class RobotContainer {
  final CommandXboxController controller = new CommandXboxController(0);
  private final movetosetpoint test = new movetosetpoint(2);
  private final setstate l1 = new setstate(ElevatorStates.L1);
  private final setstate l2 = new setstate(ElevatorStates.L2);
  private final setstate l3 = new setstate(ElevatorStates.L3);
  private final setstate l4 = new setstate(ElevatorStates.L4);
  private final setstate stop = new setstate(ElevatorStates.STOP);
  private final elevatorstop reset = new elevatorstop();
  public RobotContainer() {
    // DriverStation.silenceJoystickConnectionWarning(true);
    // DriverStation.removeRefreshedDataEventHandle(44000);

    // // data logs
    // DataLogManager.start();
    // DataLogManager.logNetworkTables(true);
    // DriverStation.startDataLog(DataLogManager.getLog(), true);

    // // When to go into brownout protection
    // RobotController.setBrownoutVoltage(7.0);

    // // robot setup
    // JoystickIO.getButtonBindings();
    // AutoUtils.initAuto();P
    controller.a().onTrue(l1);
    controller.b().onTrue(l2);
    controller.x().onTrue(l3);
    controller.y().onTrue(l4);
  }

  public Command getAutonomousCommand() {
    // return new DriveStraight();
    return Autos.getSelectedCommand();
  }
}
