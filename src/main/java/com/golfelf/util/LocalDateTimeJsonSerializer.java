/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * A json serializer to serialize a LocalDateTime in the ISO_LOCAL_DATE_TIME format.
 * Used by gson libraries to serialize
 */
public class LocalDateTimeJsonSerializer implements JsonSerializer<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDateTime));
    }
}
