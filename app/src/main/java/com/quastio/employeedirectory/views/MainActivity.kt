package com.quastio.employeedirectory.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.quastio.employeedirectory.R
import com.quastio.employeedirectory.adapters.EmployeeListAdapter
import com.quastio.employeedirectory.models.EmployeeDbModel
import com.quastio.employeedirectory.models.ResultWrapper
import com.quastio.employeedirectory.viewmodels.EmployeeViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), EmployeeListAdapter.Interaction {
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var employeeListAdapter: EmployeeListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        employeeListAdapter= EmployeeListAdapter(this)
        linearLayoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        employee_recycler.layoutManager=linearLayoutManager
        employee_recycler.adapter=employeeListAdapter


        employeeViewModel=ViewModelProvider(this).get(EmployeeViewModel::class.java)

        employeeViewModel.getEmployeeData().observe(this, Observer {
            when(it){
                is ResultWrapper.Success->{
                    Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
                    updateRecyclerView(it.data)
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

        employeeViewModel.searchData.observe(this, Observer {
            when(it){
                is ResultWrapper.Success->{
                    updateRecyclerView(it.data)
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

        search_et.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            employeeViewModel.searchEmployee(s.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun updateRecyclerView(data: List<EmployeeDbModel>) {
        if (this::employeeListAdapter.isInitialized){
            employeeListAdapter.submitList(data)
        }

    }

    override fun onItemSelected(position: Int, item: EmployeeDbModel) {
        val intent=Intent(this,EmployeeDetailsActivity::class.java)
        intent.putExtra("employee",item)
        startActivity(intent)

    }
}