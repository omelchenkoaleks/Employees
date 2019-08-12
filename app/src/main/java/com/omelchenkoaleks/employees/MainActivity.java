package com.omelchenkoaleks.employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.omelchenkoaleks.employees.adapters.EmployeeAdapter;
import com.omelchenkoaleks.employees.api.ApiFactory;
import com.omelchenkoaleks.employees.api.ApiService;
import com.omelchenkoaleks.employees.pojo.Employee;
import com.omelchenkoaleks.employees.pojo.EmployeeResponse;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private EmployeeAdapter mEmployeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.employees_recycler_view);
        mEmployeeAdapter = new EmployeeAdapter();
        // в адаптер нужно добавить вначале пустой список или будет ошибка null
        mEmployeeAdapter.setEmployees(new ArrayList<Employee>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mEmployeeAdapter);

        /*
            после этих двух строчек мы можем получать наши данные,
            используя реализованные методы ApiService Ретрофитом ...
          */
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();

        // ВОТ ТАК:

        /*
            первые две строчки стандартные:
                1 = в каком потоке загружать данные
                2 = в каком потоке выводить данные

                теперь указать, что делать когда мы получим данные:
                    используем метод subscribe с двумя параметрами, передаем два объекта
                    анонимного класса Consumer (первый выполняется в случае, если данные загружены
                    успешно, второй принимает исключение)
         */
        apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Exception {
                        mEmployeeAdapter.setEmployees(employeeResponse.getResponse());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "Ошибка получения данных!", Toast.LENGTH_SHORT).show();
                    }
                });

        /*
            ЗАМЕТКА КАК ПОЛУЧИТЬ ИНФОРМАЦИЮ ОБ ОШИБКЕ В ТОСТ:
                вместо text: "Ошибка получения данных!" можно написать
                throwable.getMessage() = и прочитать в тосте ошибку )
         */


        /* ------- Тест, чтобы проверить что работает: ------- */
//        List<Employee> employees = new ArrayList<>();
//        Employee employee1 = new Employee();
//        Employee employee2 = new Employee();
//        employee1.setFName("Aleksandr");
//        employee2.setFName("Inna");
//        employee1.setLName("Omelchenko");
//        employee2.setLName("Jaldack");
//        employees.add(employee1);
//        employees.add(employee2);
//        mEmployeeAdapter.setEmployees(employees);
    }
}
