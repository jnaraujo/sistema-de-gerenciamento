package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.erros.InvalidUniqueIDException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UniqueIDTest {
    @Test
    void testValidIDFromConstructor() throws InvalidUniqueIDException {
        assertDoesNotThrow(() -> new UniqueID(UUID.randomUUID().toString()));
    }

    @Test
    void testInvalidIDFromConstructor() {
        assertThrows(InvalidUniqueIDException.class, () -> new UniqueID("invalid id"));
    }

    @Test
    void testCreate() throws InvalidUniqueIDException {
        assertDoesNotThrow(() -> UniqueID.create());
    }

    @Test
    void testIsValid() throws InvalidUniqueIDException {
        assertTrue(UniqueID.isValid(UUID.randomUUID().toString()));
        assertFalse(UniqueID.isValid("invalid id"));
    }
    @Test
    void testToString() throws InvalidUniqueIDException {
        String validID = UUID.randomUUID().toString();
        UniqueID uniqueID = new UniqueID(validID);

        assertEquals(validID, uniqueID.toString());
    }

    @Test
    void testEquals() throws InvalidUniqueIDException {
        UniqueID uniqueID = UniqueID.create();
        UniqueID uniqueID2 = UniqueID.create();
        assertNotEquals(uniqueID, uniqueID2);
    }
}