package com.example.application.constants;

public interface ApplicationConstants {
    class Phone{
        public static final int CODE_US = 1;
        public static final String COUNTRY_CODE = String.format("+%s", CODE_US);
    }

    class Regex {
        public static final String EMAIL = "(?i)(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        public static final String DOUBLE = "[0-9]*\\.[0-9]*";
        public static final String INTEGER = "[0-9]*";
        public static final String URL = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        public static final String DURATION_TIME_INTERVAL = "^([0-9][0-9]|[0-9]):([0-5][0-9]|[0-9])$";
        public static final String PHONE_US = "^((\\+1|1)?( |-)?)?(\\([2-9][0-9]{2}\\)|[2-9][0-9]{2})( |-)?([2-9][0-9]{2}( |-)?[0-9]{4})$";
    }

    String FAKE_URL = "https://twitter.com/XfouzV/status/455285567534944256/photo/1";
}
