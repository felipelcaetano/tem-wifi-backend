package br.com.temwifi.utils.formato;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatUtils {

    public static String bigDecimalToString(BigDecimal bigDecimal) {

        return new DecimalFormat("###,###,###,##0.00",
                new DecimalFormatSymbols(new Locale ("pt", "BR")))
                .format(bigDecimal).replaceAll("-", "");
    }

    public static BigDecimal stringToBigDecimal(String string, Integer signum) {

        BigDecimal bigDecimal = new BigDecimal(string.replaceAll("\\.", "").replaceAll(",", "."));

        if(signum == null) return bigDecimal;

        if(signum.equals(-1)) {
            bigDecimal = bigDecimal.negate();
        }

        return bigDecimal;
    }
}
