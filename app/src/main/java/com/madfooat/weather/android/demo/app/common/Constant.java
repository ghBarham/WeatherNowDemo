package com.madfooat.weather.android.demo.app.common;

public class Constant {

    public static String LOG_TAG = "weatherApp";

    public static class API_URLS {
        public static final String GET_COUNTRIES_URL = "https://ezcmd.com/apps/api_ezhigh/get_countries/c495fc777f230b87a73d70ee7b373290/263";
        public static final String GET_CITIES_URL = "https://ezcmd.com/apps/api_ezhigh/get_hierarchy/c495fc777f230b87a73d70ee7b373290/263?level=1&country_code=";
        public static final String CITY_WEATHER_URL = "https://api.darksky.net/forecast/2213b41472d49c9bd8f85241eefa0477/";
    }

    public static class API_RESPONSE_KEYS {
        public static final String SUCCESS_STATUS = "success";
        public static final String REMAINING_LOOKUPS = "remaining_lookups";

        public static final String WEATHER_API_CURRENTLY = "currently";
        public static final String WEATHER_API_TEMPERATUURE ="temperature";
    }


    public static class EXCEPTION_CODE {
        public static final String UNEXPECTED = "CODE0";
        public static final String DATA_API_NOT_RESPONDING = "CODE1";
        public static final String DATA_API_RESPONSE_STATUS_FAILED = "CODE2";
    }
}
