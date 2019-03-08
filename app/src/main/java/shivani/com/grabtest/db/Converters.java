package shivani.com.grabtest.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

import shivani.com.grabtest.vo.Source;

/**
 * Created by Shivani on 06/03/19.
 */
class Converters {
    @TypeConverter
    public static Source fromString(String value) {
        return new Gson().fromJson(value, Source.class);
    }

    @TypeConverter
    public static String fromSource(Source source) {
        Gson gson = new Gson();
        return gson.toJson(source);
    }
}
