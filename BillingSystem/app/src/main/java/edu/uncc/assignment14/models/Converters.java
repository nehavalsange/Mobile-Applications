package edu.uncc.assignment14.models;

import androidx.room.TypeConverter;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date fromTimestamp(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
}