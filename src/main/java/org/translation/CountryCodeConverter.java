package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO CheckStyle: Wrong lexicographical order for 'java.util.HashMap' import (remove this comment once resolved)

/**
 * This class provides the service of converting country codes to their names.
 */

public class CountryCodeConverter {

    // TODO Task: pick appropriate instance variable(s) to store the data necessary for this class
    private final Map<String, String> countryCodeMap;
    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */

    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {
        countryCodeMap = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // TODO Task: use lines to populate the instance variable(s)
            for (String line : lines) {
                String[] parts = line.split("\t");
                String countryName = parts[0].trim();
                String countryCode = parts[2].trim();
                countryCodeMap.put(countryCode, countryName);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // TODO Task: update this code to use an instance variable to return the correct value
        return countryCodeMap.getOrDefault(code.toUpperCase(), "Code does not exist");
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // TODO Task: update this code to use an instance variable to return the correct value
        for (Map.Entry<String, String> entry : countryCodeMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(country)) {
                return entry.getKey();
            }
        }
        return "Country not found";
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // TODO Task: update this code to use an instance variable to return the correct value
        return countryCodeMap.size() - 1;
    }
}
