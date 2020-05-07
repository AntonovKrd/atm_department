package krd.antonov.storage;

import krd.antonov.storage.exceptions.BanknoteException;
import krd.antonov.utility.Utility;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BanknoteStorage {
    private final static Logger log = Logger.getLogger(BanknoteStorage.class);
    private HashMap<BanknotesDenomination, Integer> dollarsMap;

    public BanknoteStorage(int oneDollarsCount, int twoDollarsCount, int fiveDollarsCount, int tenDollarsCount,
                           int twentyDollarsCount, int fiftyDollarsCount, int oneHundredDollarsCount) throws BanknoteException {
        this.dollarsMap = new HashMap<>();
        this.dollarsMap.put(BanknotesDenomination.ONE, oneDollarsCount);
        this.dollarsMap.put(BanknotesDenomination.TWO, twoDollarsCount);
        this.dollarsMap.put(BanknotesDenomination.FIVE, fiveDollarsCount);
        this.dollarsMap.put(BanknotesDenomination.TEN, tenDollarsCount);
        this.dollarsMap.put(BanknotesDenomination.TWENTY, twentyDollarsCount);
        this.dollarsMap.put(BanknotesDenomination.FIFTY, fiftyDollarsCount);
        this.dollarsMap.put(BanknotesDenomination.ONE_HUNDRED, oneHundredDollarsCount);
        this.dollarsMap.put(BanknotesDenomination.EMPTY, 0);
        checkDollarsMap();
        log.info("BanknoteStorage is created");
    }

    private void checkDollarsMap() throws BanknoteException {
        if (dollarsMap.entrySet().stream().anyMatch(e -> e.getValue() < 0)) {
            throw new BanknoteException("Incorrect counts banknotes");
        }
    }

    public HashMap<BanknotesDenomination, Integer> getAllDollars() {
        HashMap<BanknotesDenomination, Integer> dollars = new HashMap<>(dollarsMap);
        dollars.remove(BanknotesDenomination.EMPTY);
        return dollars;
    }

    public void insertDollars(BanknotesDenomination denomination, int count) throws BanknoteException {
        if (denomination != BanknotesDenomination.EMPTY && count > 0) {
            long countBills = dollarsMap.get(denomination).longValue() + count;
            if (countBills < Integer.MAX_VALUE) {
                dollarsMap.put(denomination, dollarsMap.get(denomination) + count);
                log.info(count + " banknote's " + denomination.name() + " dollar('s)  inserted");
            } else throw new BanknoteException("storage is overflow");
        } else throw new BanknoteException("bill collector is empty");
    }

    public long getSumDollars() {
        log.info("the amount of the banknote storage is displayed");
        return dollarsMap.keySet().stream().mapToLong(key -> key.getValue() * dollarsMap.get(key).longValue()).sum();
    }

    public HashMap<BanknotesDenomination, Integer> getDollars(BanknotesDenomination denomination, int count) throws BanknoteException {
        HashMap<BanknotesDenomination, Integer> dollars = new HashMap<>();
        if (denomination.equals(BanknotesDenomination.EMPTY) || count <= 0 || dollarsMap.get(denomination) - count < 0) {
            throw new BanknoteException("Not enough bills in the banknote storage or incorrect amount requested");
        } else {
            dollarsMap.put(denomination, dollarsMap.get(denomination) - count);
            dollars.put(denomination, count);
            log.info(count + " banknote's " + denomination.name() + " dollar('s) given out");
        }
        return dollars;
    }

    public HashMap<BanknotesDenomination, Integer> getMinBillsDollars(int sum) throws BanknoteException {
        if (sum <= 0) throw new BanknoteException("incorrect amount requested");
        HashMap<BanknotesDenomination, Integer> copyDollarsMap = new HashMap<>(dollarsMap);
        HashMap<BanknotesDenomination, Integer> dollars = new HashMap<>();
        Integer[] denominations = copyDollarsMap.keySet().stream().map(BanknotesDenomination::getValue).toArray(Integer[]::new);
        Arrays.sort(denominations, Collections.reverseOrder());
        AtomicInteger incrementor = new AtomicInteger();
        while (sum > 0) {
            int denomination = denominations[incrementor.getAndIncrement()];
            if (denomination == 0)
                throw new BanknoteException("Impossible to give out the requested amount. Not enough bills.");
            int billsCount = sum / denomination;
            if (billsCount == 0) continue;
            BanknotesDenomination mapKey = copyDollarsMap.keySet().stream().filter(key -> key.getValue() == denomination).findFirst().get();
            if (copyDollarsMap.get(mapKey) < billsCount)
                billsCount = copyDollarsMap.get(mapKey); //if not enough, take how much is
            copyDollarsMap.put(mapKey, copyDollarsMap.get(mapKey) - billsCount);
            dollars.put(mapKey, billsCount);
            sum -= denomination * billsCount;
        }
        dollarsMap = copyDollarsMap;
        log.info(Utility.convertMapDollarsToString(dollars) + " given out");
        return dollars;
    }
}