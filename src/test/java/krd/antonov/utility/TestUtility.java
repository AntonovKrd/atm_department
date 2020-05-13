package krd.antonov.utility;

import krd.antonov.storage.BanknoteStorage;
import krd.antonov.storage.BanknotesDenomination;
import krd.antonov.storage.exceptions.BanknoteException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtility {

    @Test
    void testConvertMapDollarsToString() throws BanknoteException {
        BanknoteStorage banknoteStorage = new BanknoteStorage(5, 5, 5, 5, 5, 5, 5);
        String mapString = "50 dollar(s)  - 4 bill(s)";
        assertEquals(mapString, Utility.convertMapDollarsToString(banknoteStorage.getDollars(BanknotesDenomination.FIFTY, 4)));
        mapString = "50 dollar(s)  - 1 bill(s), 20 dollar(s)  - 2 bill(s), 5 dollar(s)  - 1 bill(s), 2 dollar(s)  - 2 bill(s)";
        assertEquals(mapString, Utility.convertMapDollarsToString(banknoteStorage.getMinBillsDollars(99)));
    }
}
