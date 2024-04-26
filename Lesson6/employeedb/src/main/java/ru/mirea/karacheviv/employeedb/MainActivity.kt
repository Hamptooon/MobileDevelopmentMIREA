package ru.mirea.karacheviv.employeedb

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db: AppDatabase? = App.instance?.database
        val superHeroDao: SuperHeroDao? = db?.employeeDao()
        var employee = SuperHero()
        employee.id = 1
        employee.name = "Soviet Fist"
        employee.strength = 10000
        // запись сотрудников в базу
        // запись сотрудников в базу
        superHeroDao?.insert(employee)
        // Загрузка всех работников
        // Загрузка всех работников
        val employees: List<Any?>? = superHeroDao?.all
        // Получение определенного работника с id = 1
        // Получение определенного работника с id = 1
        employee = superHeroDao?.getById(1)!!
        // Обновление полей объекта
        // Обновление полей объекта
        employee.strength = 20000
        superHeroDao.update(employee)
        Log.d("MainActivityLog", employee.name + " " + employee.strength)
    }
}