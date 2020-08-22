package com.quastio.employeedirectory.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Company :Serializable{
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("catchPhrase")
    @Expose
    var catchPhrase: String? = null

    @SerializedName("bs")
    @Expose
    var bs: String? = null
}