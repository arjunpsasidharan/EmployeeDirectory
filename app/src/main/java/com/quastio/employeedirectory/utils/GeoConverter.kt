package com.quastio.employeedirectory.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quastio.employeedirectory.models.Geo
import java.io.Serializable
import java.lang.reflect.Type

object GeoConverter : Serializable {
    @TypeConverter
    fun fromGeoValues(geo: Geo?): String? {
        if (geo == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Geo>() {}.type
        return gson.toJson(geo, type)
    }

    @TypeConverter
    fun toGeo(geoString: String?): Geo? {
        if (geoString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Geo?>() {}.type
        return gson.fromJson(geoString, type)
    }
}