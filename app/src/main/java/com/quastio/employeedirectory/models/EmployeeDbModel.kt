package com.quastio.employeedirectory.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "employee")
class EmployeeDbModel : Serializable {
    @PrimaryKey
    var id: Int? = null
    var name: String? = null
    var username: String? = null
    var email: String? = null
    var profileImage: String? = null
    var address: Address? = null
    var phone: String? = null
    var website: String? = null
    var company: Company? = null

}