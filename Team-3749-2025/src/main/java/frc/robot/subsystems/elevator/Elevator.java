package frc.robot.subsystems.elevator;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.subsystems.elevator.ElevatorConstants.ElevatorStates;
import frc.robot.subsystems.elevator.ElevatorIO.ElevatorData;
import frc.robot.subsystems.elevator.real.ElevatorSparkMax;
import frc.robot.subsystems.elevator.sim.ElevatorSimulation;
import frc.robot.utils.ShuffleData;
import frc.robot.utils.UtilityFunctions;
import frc.robot.utils.MiscConstants.SimConstants;

/**
 * Elevator subsystem
 * 
 * @author Dhyan Soni
 * @author Andrew Ge
 */

public class Elevator extends SubsystemBase {
    private ElevatorIO elevatorio;
    private ElevatorData data = new ElevatorData();
    private ElevatorStates state = ElevatorStates.STOP;

    private ProfiledPIDController pidController = new ProfiledPIDController(
            ElevatorConstants.ElevatorControl.kPSim,
            0,
            ElevatorConstants.ElevatorControl.kDSim,
            new TrapezoidProfile.Constraints(ElevatorConstants.ElevatorControl.maxV,
                    ElevatorConstants.ElevatorControl.maxA));
    
    private ElevatorFeedforward feedforward = new ElevatorFeedforward(
        ElevatorConstants.ElevatorControl.kSSim,
        ElevatorConstants.ElevatorControl.kGSim,
        ElevatorConstants.ElevatorControl.kVSim,
        ElevatorConstants.ElevatorControl.kASim
        );

    private ShuffleData<String> currentCommandLog = new ShuffleData<String>(this.getName(), "current command", "None");
    private ShuffleData<Double> positionMetersLog = new ShuffleData<Double>("Elevator", "position meters", 0.0);
    private ShuffleData<Double> velocityUnitsLog = new ShuffleData<Double>("Elevator", "velocity units", 0.0);
    private ShuffleData<Double> accelerationUnitsLog = new ShuffleData<Double>("Elevator", "acceleration units", 0.0);
    private ShuffleData<Double> inputVoltsLog = new ShuffleData<Double>("Elevator", "input volts", 0.0);
    private ShuffleData<Double> appliedVoltsLog = new ShuffleData<Double>("Elevator", "applied volts", 0.0);
    private ShuffleData<Double> currentAmpsLog = new ShuffleData<Double>("Elevator", "current amps", 0.0);
    private ShuffleData<Double> tempCelciusLog = new ShuffleData<Double>("Elevator", "temp celcius", 0.0);

    // For tuning on real
    // private ShuffleData<Double> kPData = new ShuffleData<Double>("Elevator",
    // "kPData", ElevatorConstants.ElevatorControl.kPSim);
    // private ShuffleData<Double> kDData = new ShuffleData<Double>("Elevator",
    // "kDData", ElevatorConstants.ElevatorControl.kDSim);
    // private ShuffleData<Double> kGData = new ShuffleData<Double>("Elevator",
    // "kGData", ElevatorConstants.ElevatorControl.kGSim);
    // private ShuffleData<Double> kVData = new ShuffleData<Double>("Elevator",
    // "kVData", ElevatorConstants.ElevatorControl.kVSim);
    // private ShuffleData<Double> kAData = new ShuffleData<Double>("Elevator",
    // "kAData", ElevatorConstants.ElevatorControl.kASim);

    private Mechanism2d mech = new Mechanism2d(3, 3);
    private MechanismRoot2d root = mech.getRoot("elevator", 2, 0);
    private MechanismLigament2d elevatorMech = root
            .append(new MechanismLigament2d("elevator", Units.feetToMeters(3.25), 90));

    public Elevator() {
        if (Robot.isSimulation()) {
            elevatorio = new ElevatorSimulation();
        } else {
            elevatorio = new ElevatorSparkMax();
        }
    }

    public ElevatorStates getState() {
        return state;
    }

    public double getPositionMeters() {
        return data.positionMeters;
    }

    public double getVelocityRadPerSec() {
        return data.velocityUnits;
    }

    // returns true when the state is reached
    public boolean getIsStableState() {
        switch (state) {
            case L1:
                return UtilityFunctions.withinMargin(0.01, ElevatorConstants.stateHeights.l1Height,
                        data.positionMeters);
            case L2:
                return UtilityFunctions.withinMargin(0.01, data.positionMeters,
                        ElevatorConstants.stateHeights.l2Height);
            case L3:
                return UtilityFunctions.withinMargin(0.01, data.positionMeters,
                        ElevatorConstants.stateHeights.l3Height);
            case L4:
                return UtilityFunctions.withinMargin(0.01, data.positionMeters,
                        ElevatorConstants.stateHeights.l4Height);
            default:
                return false;
        }
    }

    public void setVoltage(double volts) {
        elevatorio.setVoltage(volts);
    }

    public void setState(ElevatorStates state) {
        this.state = state;
        switch (state) {
            case STOP:
                runStateStop();
                break;
            case L1:
                setGoal(ElevatorConstants.stateHeights.l1Height);
                break;
            case L2:
                setGoal(ElevatorConstants.stateHeights.l2Height);
                break;
            case L3:
                setGoal(ElevatorConstants.stateHeights.l3Height);
                break;
            case L4:
                setGoal(ElevatorConstants.stateHeights.l4Height);
                break;
            case MAX:
                setGoal(6);
                break;
            case STOW:
                setGoal(0);
                break;
            default:
                break;
        }
    }

    public void setGoal(double height) {
        pidController.setGoal(height);
    }

    private void runState() {
        switch (state) {
            case STOP:
                runStateStop();
                break;
            case L1:
                moveToGoal();
                break;
            case L2:
                moveToGoal();
                break;
            case L3:
                moveToGoal();

                break;
            case L4:
                moveToGoal();

                break;
            case MAX:
                moveToGoal();

                break;
            case STOW:
                moveToGoal();
                break;
        }
    }

    private void moveToGoal() {
        State state = pidController.getSetpoint();
        double pidVoltage = pidController.calculate(getPositionMeters());

        State nextState = pidController.getSetpoint();
        double ffVoltage = feedforward.calculate(state.velocity,nextState.velocity);

        elevatorio.setVoltage(ffVoltage + pidVoltage);
    }

    private void runStateStop() {
        elevatorio.setVoltage(0);
    }

    public void stop() {
        elevatorio.setVoltage(0);
    }

    private void logData() {
        currentCommandLog.set(this.getCurrentCommand() == null ? "None" : this.getCurrentCommand().getName());
        positionMetersLog.set(data.positionMeters);
        velocityUnitsLog.set(data.velocityUnits);
        accelerationUnitsLog.set(data.accelerationUnits);
        inputVoltsLog.set(data.inputVolts);
        appliedVoltsLog.set(data.appliedVolts);
        currentAmpsLog.set(data.currentAmps);
        tempCelciusLog.set(data.tempCelcius);

        // make 3.25 a constant "elevatorBaseHeight"
        elevatorMech.setLength(Units.feetToMeters(3.25) + data.positionMeters);
        SmartDashboard.putData("elevator mechanism", mech);
    }

    @Override
    public void periodic() {
        elevatorio.updateData(data);

        System.out.println(state);
        runState();
        logData();
        // pidController.setPID(kPData.get(),0,kDData.get())
    }
}
