package lab.pavel.domain;

public class Crowd {
    private int astonishmentLevel;
    private boolean mouthsOpen;

    public void reactToSpectacle(boolean brightSun, boolean dashingManeuver) {
        if (brightSun && dashingManeuver) {
            mouthsOpen = true;
            astonishmentLevel += 2;
            return;
        }
        if (brightSun || dashingManeuver) {
            astonishmentLevel += 1;
            return;
        }
        mouthsOpen = false;
        astonishmentLevel = 0;
    }

    public int getAstonishmentLevel() {
        return astonishmentLevel;
    }

    public boolean isMouthsOpen() {
        return mouthsOpen;
    }
}

