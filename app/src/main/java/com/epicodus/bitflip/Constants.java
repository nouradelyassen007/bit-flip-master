package com.epicodus.bitflip;

import android.os.Build;

/**
 * Created by DroAlvarez on 12/1/16.
 */

public class Constants {
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String API_BASE_URL = "http://api.walmartlabs.com/v1/";
    public static final String API_KEY_QUERY_PARAMETER = "apiKey";
    public static final String FORMAT_QUERY_PARAMETER = "format";
    public static final String FORMAT_QUERY_ANSWER = "json";
    public static final String SEARCH_URL_ADDON = "search";
    public static final String SEARCH_QUERY_PARAMETER = "query";
    public static final String FIREBASE_CHILD_CATEGORIES = "categories";
    public static final String FIREBASE_CHILD_ITEMS = "items";
    public static final String FIREBASE_CHILD_USERS = "users";
    public static final String PREFERENCES_CATEGORY_KEY = "category";
}
