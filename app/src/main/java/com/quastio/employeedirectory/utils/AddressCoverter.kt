package com.quastio.employeedirectory.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quastio.employeedirectory.models.Address
import com.quastio.employeedirectory.models.Company
import java.io.Serializable
import java.lang.reflect.Type

object AddressCoverter : Serializable {
    @TypeConverter
    fun fromAddressValue(address: Address?): String? {
        if (address == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Address>() {}.type
        return gson.toJson(address, type)
    }

    @TypeConverter
    fun toAddress(addressString: String?): Address? {
        if (addressString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Address?>() {}.type
        return gson.fromJson(addressString, type)
    }
}