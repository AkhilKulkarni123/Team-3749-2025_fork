package frc.robot.subsystems.elevator;

import edu.wpi.first.math.util.Units;
public class ElevatorConstants {
    public static class ElevatorSpecs {
        public static final double gearing = 6.0;
        public static final double carriageMassKg = 12;
        public static final double drumRadiusMeters = Units.inchesToMeters(2);
        public static final double minHeightMeters = 0;
        public static final double maxHeightMeters = Units.feetToMeters(25); // remeasure maxV and A
        // public static final boolean simulateGravity = true;
        public static final double startingHeightMeters = 0;
    } 

    public static class ElevatorControl {
        public static final double kPSim = 1;
        public static final double kDSim = 0.02; // 1/20 of kP for now
        public static final double kSSim = 0;
        public static final double kGSim = 2.3;
        public static final double kVSim = 2.365; // 12 - 2.3 / 4.1 NOT USED
        public static final double kASim = 0;
        public static final double maxV = 4.112;
        public static final double maxA = 6.588; // change in velocity / seconds
        
        // public static final double kSSim = 0;
    }

    public enum ElevatorStates{
        STOP,
        L1,
        L2,
        L3,
        L4,
        MAX,
        STOW
    }
    public static class stateHeights {
        public static final double l1Height = Units.inchesToMeters(18);
        public static final double l2Height = Units.inchesToMeters(31.875);
        public static final double l3Height = Units.inchesToMeters(47.625);
        public static final double l4Height = Units.inchesToMeters(72);
    }
}
