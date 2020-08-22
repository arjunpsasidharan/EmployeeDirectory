package com.quastio.employeedirectory.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.quastio.employeedirectory.R
import com.quastio.employeedirectory.models.EmployeeDbModel
import kotlinx.android.synthetic.main.activity_employee_details.*

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var employeeDbModel: EmployeeDbModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)
        intent?.let {
            employeeDbModel=it.getSerializableExtra("employee") as  EmployeeDbModel
        }

        if (this::employeeDbModel.isInitialized){
            with(employeeDbModel){
                Glide.with(this@EmployeeDetailsActivity)
                    .load(profileImage)
                    .into(employee_iv)
                name_tv.text=name
                user_name_tv.text=username
                email_tv.text=email
                street_tv.text=address?.street
                city_tv.text=address?.city
            }
        }
    }
}