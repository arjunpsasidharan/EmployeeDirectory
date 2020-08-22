package com.quastio.employeedirectory.restclients

import com.google.gson.GsonBuilder
import com.quastio.employeedirectory.models.EmployeePojo
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import java.util.concurrent.TimeUnit

object RestClient {


    private val defaultHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(defaultHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create()
                )
            )
    }

    val employeeApiService: EmployeeApiService by lazy {
        retrofitBuilder.build().create(EmployeeApiService::class.java)
    }


    interface EmployeeApiService {
        @GET(END_POINT)
        suspend fun getEmployees(): List<EmployeePojo>

    }

    private const val BASE_URL = "http://www.mocky.io/v2/"
    private const val END_POINT = "5d565297300000680030a986"

}