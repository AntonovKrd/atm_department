package krd.antonov;

import krd.antonov.storage.BanknoteStorage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Department {
    List<BanknoteStorage> storageDepartment = new ArrayList<>();

    public boolean addStorage(BanknoteStorage storage) {
        if (storage == null) return false;
        else return storageDepartment.add(storage);
    }

    public boolean deleteStorage(BanknoteStorage storage) {
        if (storage == null) return false;
        else return storageDepartment.remove(storage);
    }

    public BigInteger getTotalSum() {
        final BigInteger[] sum = {BigInteger.ZERO};
        storageDepartment.forEach(storage -> sum[0] = sum[0].add(BigInteger.valueOf(storage.getSumDollars())));
        return sum[0];
    }

    public void restoreAllStorages() {
        storageDepartment.forEach(BanknoteStorage::restore);
    }
}
