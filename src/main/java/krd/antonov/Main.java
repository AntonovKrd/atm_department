package krd.antonov;

import krd.antonov.storage.BanknoteStorage;
import krd.antonov.storage.exceptions.BanknoteException;
import krd.antonov.utility.Utility;

public class Main {
    public static void main(String[] args) throws BanknoteException {
        Department department = new Department();
        BanknoteStorage banknoteStorage = new BanknoteStorage(10, 10, 10,
                10, 10, 10, 10);
        BanknoteStorage banknoteStorage2 = new BanknoteStorage(10, 10, 10,
                10, 10, 10, 10);
        department.addStorage(banknoteStorage);
        System.out.println("add first storage with " + banknoteStorage.getSumDollars() + " dollars");
        department.addStorage(banknoteStorage2);
        System.out.println("add second storage with " + banknoteStorage2.getSumDollars() + " dollars");
        System.out.println("Total sum : " + department.getTotalSum());
        System.out.println("issue " + Utility.convertMapDollarsToString(banknoteStorage2.getMinBillsDollars(1500)) + " from banknoteStorage2");
        System.out.println("Total sum : " + department.getTotalSum());
        department.restoreAllStorages();
        System.out.println("every storage is restored");
        System.out.println("Total sum : " + department.getTotalSum());
    }
}
