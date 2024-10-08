package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class
    private Map<String, List<String>> languages;
    private Map<String, Map<String, String>> translations;
    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */

    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        languages = new HashMap<>();
        translations = new HashMap<>();
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryInfo = jsonArray.getJSONObject(i);
                String alpha3 = countryInfo.getString("alpha3");

                List<String> languagesList = new ArrayList<>();
                Map<String, String> translationsMap = new HashMap<>();

                for (String key : countryInfo.keySet()) {
                    if (!"alpha3".equals(key) && !"alpha2".equals(key) && !"id".equals(key)) {
                        languagesList.add(key);
                        translationsMap.put(key, countryInfo.getString(key));
                    }

                }
                languages.put(alpha3, languagesList);
                translations.put(alpha3, translationsMap);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> countryLanguages = languages.get(country);
        if (countryLanguages == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(countryLanguages);
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object

        List<String> countryCodes = new ArrayList<>(translations.keySet());
        countryCodes.sort(String::compareTo);
        return new ArrayList<>(countryCodes);
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed
        Map<String, String> countryTranslations = translations.get(country);
        if (countryTranslations != null) {
            return countryTranslations.get(language);
        }
        else {
            return null;
        }
    }
}
