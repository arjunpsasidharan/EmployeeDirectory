package com.quastio.employeedirectory.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quastio.employeedirectory.models.EmployeeDbModel
import com.quastio.employeedirectory.models.ResultWrapper
import com.quastio.employeedirectory.repositories.EmployeeRepository

class EmployeeViewModel:ViewModel() {
    private var _employeeData: MutableLiveData<ResultWrapper<List<EmployeeDbModel>>>?=null

    init {
        _employeeData=EmployeeRepository.getEmployeeData<EmployeeDbModel>()

    }

    fun getEmployeeData(): LiveData<ResultWrapper<List<EmployeeDbModel>>> {
        return if (_employeeData!=null)
            _employeeData as LiveData<ResultWrapper<List<EmployeeDbModel>>>
        else {
            EmployeeRepository.getEmployeeData<EmployeeDbModel>()
        }
    }
}