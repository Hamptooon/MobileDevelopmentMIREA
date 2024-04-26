package ru.mirea.karacheviv.employeedb

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [SuperHero::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): SuperHeroDao?
}