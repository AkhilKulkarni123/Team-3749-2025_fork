package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.arm.ArmConstants;
import frc.robot.subsystems.arm.ArmConstants.ArmStates;
import frc.robot.utils.UtilityFunctions;

public class HalfExtend extends Command {

    public HalfExtend() {
        super.addRequirements(Robot.arm);
    }

    @Override
    public void initialize() {
        Robot.arm.setState(ArmStates.HALFWAY_EXTENDED);
    }

    @Override
    public void end(boolean interupted) {
    }

    @Override
    public boolean isFinished() {
        double position = Robot.arm.getPositionRad();
        return UtilityFunctions.withinMargin(0.001, ArmConstants.halfwayExtendedSetPoint, position);
    }
}
