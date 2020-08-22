package com.quastio.employeedirectory.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.quastio.employeedirectory.models.EmployeeDbModel

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployees(employeesList: List<EmployeeDbModel>): List<Long>

    @Query("SELECT * FROM employee")
    fun getAllEmployees(): List<EmployeeDbModel>

}