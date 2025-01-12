package frc.robot.commands.arm.algeaArmCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.arm.ArmConstants.algeaArmConstants.ArmStates;

public class AlgeaMoveUp extends Command {

    public AlgeaMoveUp() {
        super.addRequirements(Robot.algeaArm);
    }

    @Override
    public void initialize() {
        Robot.algeaArm.setState(ArmStates.MOVING_UP);
    }

        @Override
    public void end(boolean interupted) {
        Robot.algeaArm.setState(ArmStates.STOPPED);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
