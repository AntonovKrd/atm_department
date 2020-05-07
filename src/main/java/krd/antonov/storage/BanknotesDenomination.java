package krd.antonov.storage;

public enum BanknotesDenomination {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    ONE_HUNDRED(100),
    EMPTY(0);
    private final int value;

    BanknotesDenomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
