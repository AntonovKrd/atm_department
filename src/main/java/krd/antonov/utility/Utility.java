package krd.antonov.utility;

import krd.antonov.storage.BanknotesDenomination;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;


public class Utility {

    public static String convertMapDollarsToString(Map<BanknotesDenomination, Integer> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(BanknotesDenomination::getValue).reversed()))
                .map(entry -> entry.getKey().getValue() + " dollar(s)  - " + entry.getValue() + " bill(s)")
                .collect(Collectors.joining(", "));
    }
}
