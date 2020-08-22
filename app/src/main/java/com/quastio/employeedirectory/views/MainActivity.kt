package com.quastio.employeedirectory.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.quastio.employeedirectory.R
import com.quastio.employeedirectory.models.ResultWrapper
import com.quastio.employeedirectory.viewmodels.EmployeeViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var employeeViewModel: EmployeeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        employeeViewModel=ViewModelProvider(this).get(EmployeeViewModel::class.java)

        employeeViewModel.getEmployeeData().observe(this, Observer {
            when(it){
                is ResultWrapper.Success->{
                    Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
                }
                is ResultWrapper.Error -> {
                    Toast.makeText(this, "code  " + it.code + "  error " + it.error, Toast.LENGTH_SHORT)
                        .show()

                }
                ResultWrapper.NetworkError -> {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}