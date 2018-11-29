package com.example.monash.mytodolist;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class RoomCoverter {

    @TypeConverter
    public static Long getTimestamp(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    @TypeConverter
    public static Date getDate(Long value) {
        if (value != null) {
            return new Date(value);
        }
        return null;
    }

}
