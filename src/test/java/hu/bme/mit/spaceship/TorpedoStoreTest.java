package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.booleanThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TorpedoStoreTest {
    TorpedoStore storeNotEmpty;
    TorpedoStore storeEmpty;
    TorpedoStore storeAlwaysFails;
    TorpedoStore storeNeverFails;

    @BeforeEach
    void setup() {
        storeNotEmpty = new TorpedoStore(10);
        storeEmpty = new TorpedoStore(0);
        storeAlwaysFails = new TorpedoStore(1, 1.0);
        storeNeverFails = new TorpedoStore(10, 0.0);
    }

    @Test
    void fire_Success() {
        // Arrange
        TorpedoStore store = new TorpedoStore(1);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void emptyWorks() {
        boolean firstEmpty = storeNotEmpty.isEmpty();
        boolean secondEmpty = storeEmpty.isEmpty();

        assertEquals(false, firstEmpty);
        assertEquals(true, secondEmpty);
    }

    @Test
    void torpedoCountWorks() {
        int notEmptyCount = storeNotEmpty.getTorpedoCount();
        int emptyCount = storeEmpty.getTorpedoCount();

        assertEquals(10, notEmptyCount);
        assertEquals(0, emptyCount);
    }

    @Test
    void fireWorksBasic() {
        boolean notEmptySuccess = storeNotEmpty.fire(1);
        boolean alwaysFailsSucces = storeAlwaysFails.fire(1);
        boolean neverFailsSuccess = storeNeverFails.fire(1);

        assertThrows(IllegalArgumentException.class, () -> storeEmpty.fire(1));
        assertEquals(true, notEmptySuccess);
        assertEquals(false, alwaysFailsSucces);
        assertEquals(true, neverFailsSuccess);
    }

    @Test
    void fireWorksOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> storeNotEmpty.fire(11));
        assertThrows(IllegalArgumentException.class, () -> storeNotEmpty.fire(-1));
        assertThrows(IllegalArgumentException.class, () -> storeNotEmpty.fire(0));
        assertDoesNotThrow(() -> storeNotEmpty.fire(10));
    }
}
