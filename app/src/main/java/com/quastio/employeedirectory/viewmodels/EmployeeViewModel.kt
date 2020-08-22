package com.quastio.employeedirectory.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.quastio.employeedirectory.models.EmployeeDbModel
import com.quastio.employeedirectory.models.ResultWrapper
import com.quastio.employeedirectory.repositories.EmployeeRepository

class EmployeeViewModel:ViewModel() {
    private var _employeeData: MutableLiveData<ResultWrapper<List<EmployeeDbModel>>>?=null
    private val _searchString=MutableLiveData<String>()


    init {
        _employeeData=EmployeeRepository.getEmployeeData<EmployeeDbModel>()

    }
    val searchData= Transformations
        .switchMap(_searchString){
            if (it.equals("")){
                EmployeeRepository.getEmployeeData<EmployeeDbModel>()
            }else{
                EmployeeRepository.getEmployeeData<EmployeeDbModel>()

            }
        }
    fun getEmployeeData(): LiveData<ResultWrapper<List<EmployeeDbModel>>> {
        return if (_employeeData!=null)
            _employeeData as LiveData<ResultWrapper<List<EmployeeDbModel>>>
        else {
            EmployeeRepository.getEmployeeData<EmployeeDbModel>()
        }
    }

    fun searchEmployee(key:String){
        if (_searchString.value==key){
            return
        }

        _searchString.value=key
    }
}