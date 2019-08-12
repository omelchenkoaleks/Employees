package com.omelchenkoaleks.employees.api;

import com.omelchenkoaleks.employees.pojo.EmployeeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/*
    здесь будут указаны все запросы на данный сайт (указанный сайт)

    т.е. здесь хранятся все методы по работе с сетью
 */
public interface ApiService {
    /*
        для того, чтобы слушать, что происходит с запросом - его нужно
        обернуть в объект EmployeeResponse в объект Observable
     */
    @GET("testTask.json")
    Observable<EmployeeResponse> getEmployees();
}
