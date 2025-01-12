package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.arm.ArmConstants.algeaArmConstants;
import frc.robot.subsystems.arm.ArmConstants.coralArmConstants.ArmStates;
import frc.robot.utils.UtilityFunctions;

public class FullyExtend extends Command {

    public FullyExtend() {
        super.addRequirements(Robot.coralArm);
    }

    @Override
    public void initialize() {
        Robot.coralArm.setState(ArmStates.FULLY_EXTENDED);
    }

        @Override
    public void end(boolean interupted) {
    }

    @Override
    public boolean isFinished() {
        double position = Robot.coralArm.getPositionRad();
        return UtilityFunctions.withinMargin(0.001, algeaArmConstants.fullyExtendedSetPoint, position);
    }
}
