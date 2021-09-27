package it.uniroma3.projectBD.format;

import it.uniroma3.projectBD.value.Phone;

import java.util.List;

import static it.uniroma3.projectBD.format.Regexps.US_PHONE_REGEXP;

public class PhoneDecoder implements TypeDecoder {

    static final private FormatRule US_PHONE_RULE = new FormatRule(US_PHONE_REGEXP);

    @Override
    public Phone decode(String value) {
        if (hasPhoneFormat(value.trim())) {
            final List<String> numbers = US_PHONE_RULE.extractAll(value.trim());
            return new Phone(numbers.get(0) + "-" + numbers.get(1) + "-" + numbers.get(2));
            //return numbers.get(0) + "-" + numbers.get(1) + "-" + numbers.get(2);
        } else if (value.trim().matches("\\d{10}")) { // e.g., 2016597074
            return new Phone(value.trim().substring(0, 3) + "-" + value.trim().substring(3, 6) +
                    "-" + value.trim().substring(6, 10));
            //return value.trim().substring(0,3) + "-" + value.trim().substring(3,6) + "-" + value.trim().substring(6,10);
        }
        return null;
    }

    private boolean hasPhoneFormat(String value) {
        return value.matches("\\d{3}-\\d{3}-\\d{4}") ||        // 201-659-7074
                value.matches("\\d{3}\\ \\d{3}\\ \\d{4}") || //202 654 0999
                value.matches("\\d{3}/\\d{3}/\\d{4}") ||    //401/919/5050
                value.matches("\\d{3}-\\d{3}-\\d{4}\\,\\ \\d{3}-\\d{3}-\\d{4}") ||  //702-791-7111, 800-347-9000
                value.matches("\\d{3}\\) \\d{3}-\\d{4}") ||        // 201) 659-7074
                value.matches("\\d{3}\\.\\d{3}\\.\\d{4}") ||        //512.656.7033
                value.matches("\\d{3} \\d{3}-\\d{4}") ||        //	513 721-8483
                value.matches("\\(\\d{3}\\) \\d{3}-\\d{4}") ||        // (201) 659-7074
                value.matches("\\(\\d{3}\\)\\d{3}-\\d{4}") ||        //	(619)220-8992
                value.matches("\\d{3}/\\d{3}-\\d{4}.*");        // 202/338-3830
        //(619) 239-2222
        //202 654 0999
    }

}
