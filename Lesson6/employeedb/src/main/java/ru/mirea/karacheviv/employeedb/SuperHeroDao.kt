package ru.mirea.karacheviv.employeedb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface SuperHeroDao {
    @get:Query("SELECT * FROM superhero")
    val all: List<Any?>?

    @Query("SELECT * FROM superhero WHERE id = :id")
    fun getById(id: Long): SuperHero?

    @Insert
    fun insert(employee: SuperHero?)

    @Update
    fun update(employee: SuperHero?)

    @Delete
    fun delete(employee: SuperHero?)
}