package br.com.temwifi.utils.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateUtils {

    /**
     * Transform a string into a LocalDateTime with the pattern dd/Mm/yyyy
     *
     * @param dateTime
     * @return          formatted date
     */
    public static String formatPrettyDate(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDateTime.parse(dateTime).format(formatter);
    }
}
