package br.com.temwifi.utils.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalDateUtils {

    public static Long calculaDiferencaMeses(LocalDate inicio, LocalDate fim) {

        return ChronoUnit.MONTHS.between(
                inicio.withDayOfMonth(1),
                fim.withDayOfMonth(1)
        );
    }

    public static String formatPrettyDate(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDateTime.parse(dateTime).format(formatter);
    }
}
