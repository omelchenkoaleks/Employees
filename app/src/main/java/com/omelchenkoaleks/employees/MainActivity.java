package com.omelchenkoaleks.employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.omelchenkoaleks.employees.adapters.EmployeeAdapter;
import com.omelchenkoaleks.employees.pojo.Employee;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private EmployeeAdapter mEmployeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.employees_recycler_view);
        mEmployeeAdapter = new EmployeeAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mEmployeeAdapter);

        // чтобы проверить что работает:
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        employee1.setFName("Aleksandr");
        employee2.setFName("Inna");
        employee1.setLName("Omelchenko");
        employee2.setLName("Jaldack");
        employees.add(employee1);
        employees.add(employee2);
        mEmployeeAdapter.setEmployees(employees);
    }
}
