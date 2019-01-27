package br.com.temwifi.utils.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LocalDateTimeUtils {

    /**
     * Return exact time from Sao Paulo, Brazil
     *
     * @return          date and time right now in Sao Paulo, Brazil
     */
    public static String now() {
        return ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("America/Sao_Paulo")).toString();
    }
}
