package lab.pavel.domain;

public class Stabilizer {
    private boolean lowered;

    public void lower() {
        lowered = true;
    }

    public void raise() {
        lowered = false;
    }

    public boolean isLowered() {
        return lowered;
    }
}

