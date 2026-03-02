package lab.pavel.domain;

import java.util.Objects;

public class AtmosphericCutter {
    public enum DriveMode {
        IDLE,
        ION_CUSHION
    }

    private final President president;
    private final Bay bay;
    private final Stabilizer stabilizer;
    private DriveMode driveMode = DriveMode.IDLE;
    private boolean inBay;

    public AtmosphericCutter(President president, Bay bay, Stabilizer stabilizer) {
        this.president = Objects.requireNonNull(president);
        this.bay = Objects.requireNonNull(bay);
        this.stabilizer = Objects.requireNonNull(stabilizer);
    }

    public void passCapeAndEnterBay() {
        if (!president.isOnBoard()) {
            throw new IllegalStateException("president must be on board");
        }
        inBay = true;
    }

    public void switchToIonCushion() {
        driveMode = DriveMode.ION_CUSHION;
    }

    public void lowerFakeStabilizersForEffect() {
        if (driveMode != DriveMode.ION_CUSHION) {
            throw new IllegalStateException("ion cushion must be active");
        }
        stabilizer.lower();
    }

    public void raiseStabilizers() {
        stabilizer.raise();
    }

    public void createFoamyWake() {
        if (!inBay) {
            throw new IllegalStateException("wake can be formed only in bay");
        }
        if (!stabilizer.isLowered()) {
            throw new IllegalStateException("stabilizers must touch water");
        }
        bay.setFoamyWake(true);
    }

    public DriveMode getDriveMode() {
        return driveMode;
    }

    public boolean isInBay() {
        return inBay;
    }

    public boolean isWakeCreated() {
        return bay.isFoamyWake();
    }
}

