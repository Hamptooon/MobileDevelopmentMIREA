package ru.mirea.karacheviv.yandexmaps

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class App : Application() {
    private var MAPKIT_API_KEY = "829b99db-dee5-4db1-a086-7864766ae376"

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
    }

}