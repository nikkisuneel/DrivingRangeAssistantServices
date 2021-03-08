/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * A json deserializer to deserialize a LocalDateTime from a json string in the
 * ISO_LOCAL_DATE_TIME format. Used by gson libraries to deserialize.
 */
public class LocalDateTimeJsonDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}