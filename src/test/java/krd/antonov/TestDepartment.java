package krd.antonov;

import krd.antonov.storage.BanknoteStorage;
import krd.antonov.storage.exceptions.BanknoteException;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class TestDepartment {
    private static final Logger log = Logger.getLogger(TestDepartment.class);
    private static BanknoteStorage banknoteStorage;
    private static Department department;

    @BeforeEach
    void init() throws BanknoteException {
        banknoteStorage = new BanknoteStorage(5, 5, 5, 5, 5, 5, 5);
        department = new Department();
    }

    @Test
    void testAddStorage() {
        assertTrue(department.addStorage(banknoteStorage));
        assertFalse(department.addStorage(null));
    }

    @Test
    void testDeleteStorage() {
        department.addStorage(banknoteStorage);
        assertFalse(department.deleteStorage(null));
        assertTrue(department.deleteStorage(banknoteStorage));
    }

    @Test
    void testGetTotalSum() throws BanknoteException {
        department.addStorage(banknoteStorage);
        department.addStorage(new BanknoteStorage(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
                Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE));
        BigInteger sum = BigInteger.valueOf(5 + 2 * 5 + 5 * 5 + 10 * 5 + 20 * 5 + 50 * 5 + 100 * 5);
        assertEquals(BigInteger.valueOf(403726925636L).add(sum), department.getTotalSum());
    }

    @Test
    void testRestoreAllStorages() throws BanknoteException {
        department.addStorage(banknoteStorage);
        BanknoteStorage banknoteStorageCopy = new BanknoteStorage(5, 5, 5,
                5, 5, 5, 5);
        department.addStorage(banknoteStorageCopy);
        banknoteStorage.getAllDollars();
        banknoteStorageCopy.getMinBillsDollars(88);
        assertNotEquals(banknoteStorage.getSumDollars(), banknoteStorageCopy.getSumDollars());
        department.restoreAllStorages();
        assertEquals(banknoteStorage.getSumDollars(), banknoteStorageCopy.getSumDollars());
    }
}
