package com.quastio.employeedirectory.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.quastio.employeedirectory.models.EmployeeDbModel
import com.quastio.employeedirectory.utils.AddressCoverter
import com.quastio.employeedirectory.utils.CompanyCoverter
import com.quastio.employeedirectory.utils.GeoConverter

@Database(
    entities = [EmployeeDbModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CompanyCoverter::class, AddressCoverter::class, GeoConverter::class)

abstract class EmployeeDb : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao

    companion object {
        @Volatile
        private var instance: EmployeeDb? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            EmployeeDb::class.java, "employee_list.db"
        ).build()
    }
}