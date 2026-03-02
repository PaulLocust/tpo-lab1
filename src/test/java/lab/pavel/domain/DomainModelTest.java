package lab.pavel.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DomainModelTest {

    @Test
    void crowd_shouldReactForAllStateCombinations() {
        Crowd crowd = new Crowd();

        crowd.reactToSpectacle(false, false);
        assertFalse(crowd.isMouthsOpen());
        assertEquals(0, crowd.getAstonishmentLevel());

        crowd.reactToSpectacle(true, false);
        assertFalse(crowd.isMouthsOpen());
        assertEquals(1, crowd.getAstonishmentLevel());

        crowd.reactToSpectacle(false, true);
        assertFalse(crowd.isMouthsOpen());
        assertEquals(2, crowd.getAstonishmentLevel());

        crowd.reactToSpectacle(true, true);
        assertTrue(crowd.isMouthsOpen());
        assertEquals(4, crowd.getAstonishmentLevel());
    }

    @Test
    void cutter_shouldSwitchStatesAndCreateWake() {
        President president = new President();
        Bay bay = new Bay();
        Stabilizer stabilizer = new Stabilizer();
        AtmosphericCutter cutter = new AtmosphericCutter(president, bay, stabilizer);

        cutter.passCapeAndEnterBay();
        cutter.switchToIonCushion();
        cutter.lowerFakeStabilizersForEffect();
        cutter.createFoamyWake();
        cutter.raiseStabilizers();

        assertTrue(cutter.isInBay());
        assertEquals(AtmosphericCutter.DriveMode.ION_CUSHION, cutter.getDriveMode());
        assertTrue(cutter.isWakeCreated());
        assertFalse(stabilizer.isLowered());
    }

    @Test
    void cutter_shouldFailWithoutPresidentOnBoard() {
        President president = new President();
        president.disembark();
        AtmosphericCutter cutter = new AtmosphericCutter(president, new Bay(), new Stabilizer());
        assertThrows(IllegalStateException.class, cutter::passCapeAndEnterBay);
    }

    @Test
    void cutter_shouldFailLowerStabilizersWithoutIonMode() {
        AtmosphericCutter cutter = new AtmosphericCutter(new President(), new Bay(), new Stabilizer());
        assertThrows(IllegalStateException.class, cutter::lowerFakeStabilizersForEffect);
    }

    @Test
    void cutter_shouldFailWakeCreationOutsideBay() {
        AtmosphericCutter cutter = new AtmosphericCutter(new President(), new Bay(), new Stabilizer());
        cutter.switchToIonCushion();
        cutter.lowerFakeStabilizersForEffect();
        assertThrows(IllegalStateException.class, cutter::createFoamyWake);
    }

    @Test
    void cutter_shouldFailWakeCreationWhenStabilizersRaised() {
        AtmosphericCutter cutter = new AtmosphericCutter(new President(), new Bay(), new Stabilizer());
        cutter.passCapeAndEnterBay();
        cutter.switchToIonCushion();
        assertThrows(IllegalStateException.class, cutter::createFoamyWake);
    }

    @Test
    void cutter_shouldValidateNullDependencies() {
        President president = new President();
        Bay bay = new Bay();
        Stabilizer stabilizer = new Stabilizer();

        assertThrows(NullPointerException.class, () -> new AtmosphericCutter(null, bay, stabilizer));
        assertThrows(NullPointerException.class, () -> new AtmosphericCutter(president, null, stabilizer));
        assertThrows(NullPointerException.class, () -> new AtmosphericCutter(president, bay, null));
    }
}

