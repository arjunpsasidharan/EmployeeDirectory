package com.quastio.employeedirectory.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quastio.employeedirectory.models.Company
import java.io.Serializable
import java.lang.reflect.Type


object CompanyCoverter : Serializable{

    @TypeConverter
    fun fromCompanyValues(company: Company?): String? {
        if (company == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Company>() {}.type
        return gson.toJson(company, type)
    }

    @TypeConverter
    fun toCompany(companyString: String?): Company? {
        if (companyString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Company?>() {}.type
        return gson.fromJson(companyString, type)
    }

}