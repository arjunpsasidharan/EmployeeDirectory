package com.quastio.employeedirectory.repositories

import androidx.lifecycle.MutableLiveData
import com.quastio.employeedirectory.db.EmployeeDb
import com.quastio.employeedirectory.models.EmployeeDbModel
import com.quastio.employeedirectory.models.ResultWrapper
import com.quastio.employeedirectory.restclients.RestClient
import com.quastio.employeedirectory.utils.MyApplication
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

object EmployeeRepository {
    var job: CompletableJob? = null
    var searchJob: CompletableJob? = null

    fun <T> getEmployeeData(): MutableLiveData<ResultWrapper<List<EmployeeDbModel>>> {
        job = Job()
        return object : MutableLiveData<ResultWrapper<List<EmployeeDbModel>>>() {
            override fun onActive() {
                super.onActive()
                job?.let {
                    CoroutineScope(Dispatchers.IO + it).launch {
                        try {
                            val dbData = EmployeeDb.invoke(MyApplication.context).employeeDao()
                                .getAllEmployees()
                            if (dbData != null && dbData.isNotEmpty()) {
                                withContext(Dispatchers.Main) {
                                    value = ResultWrapper.Success(dbData)
                                    it.complete()
                                }
                            } else {
                                val data = RestClient.employeeApiService.getEmployees()
                                val dbList = mutableListOf<EmployeeDbModel>()

                                if (data != null && data.isNotEmpty()) {
                                    data.forEach { employeePojo ->
                                        val employeeDbModel = EmployeeDbModel()
                                        employeeDbModel.id = employeePojo.id
                                        employeeDbModel.email = employeePojo.email
                                        employeeDbModel.name = employeePojo.name
                                        employeeDbModel.company = employeePojo.company
                                        employeeDbModel.phone = employeePojo.phone
                                        employeeDbModel.profileImage = employeePojo.profileImage
                                        employeeDbModel.username = employeePojo.username
                                        employeeDbModel.website = employeePojo.website
                                        employeeDbModel.address = employeePojo.address
                                        dbList.add(employeeDbModel)
                                    }
                                    EmployeeDb.invoke(MyApplication.context).employeeDao()
                                        .insertEmployees(dbList)
                                }
                                withContext(Dispatchers.Main) {
                                    value = ResultWrapper.Success(dbList)
                                    it.complete()
                                }
                            }
                        } catch (throwable: Throwable) {
                            when (throwable) {
                                is IOException -> {
                                    withContext(Dispatchers.Main) {
                                        value = ResultWrapper.NetworkError
                                        it.complete()
                                    }

                                }
                                is HttpException -> {
                                    withContext(Dispatchers.Main) {
                                        value = ResultWrapper.Error(
                                            throwable.code(),
                                            throwable.message()
                                        )
                                        it.complete()
                                    }
                                }
                                else -> {
                                    withContext(Dispatchers.Main) {
                                        value = ResultWrapper.NetworkError
                                        it.complete()
                                    }

                                }
                            }
                        }

                    }
                }
            }
        }

    }

    fun <T> getFilterResult(key: String): MutableLiveData<ResultWrapper<List<EmployeeDbModel>>> {

        searchJob = Job()
        return object : MutableLiveData<ResultWrapper<List<EmployeeDbModel>>>() {
            override fun onActive() {
                super.onActive()
                searchJob?.let {
                    CoroutineScope(Dispatchers.IO + it).launch {
                        val dbData =
                            EmployeeDb.invoke(MyApplication.context).employeeDao()
                                .getFilterEmployee("%" + key + "%")
                        if (dbData != null && dbData.isNotEmpty()) {
                            withContext(Dispatchers.Main) {
                                value = ResultWrapper.Success(dbData)
                                it.complete()
                            }
                        }

                    }
                }
            }
        }
    }
}