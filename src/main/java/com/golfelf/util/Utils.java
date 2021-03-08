/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

/*
 * A class with helper methods
 */
public class Utils {
    public static Gson getGsonWithFormatters() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonDeserializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson;
    }
}
