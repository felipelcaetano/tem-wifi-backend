package br.com.temwifi.utils;

import java.text.Normalizer;

public class FormatUtils {

    /**
     * Remove any accent from a string
     *
     * @param input
     * @return          new string with no accents
     */
    public static String removeAccent(String input) {

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
