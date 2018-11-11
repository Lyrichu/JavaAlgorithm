package pers.lyrichu.java.util.scripts;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

public class ArrayToMap {
    public static void main(String[] args) {
        String[][] countries = { { "United States", "New York" }, { "United Kingdom", "London" },
                { "Netherland", "Amsterdam" }, { "Japan", "Tokyo" }, { "France", "Paris" } };
        Map countriesCapitals = ArrayUtils.toMap(countries);
        System.out.println("Capital of Japan is:"+countriesCapitals.get("Japan"));
        System.out.println("Capital of France is:"+countriesCapitals.get("France"));
    }
}
