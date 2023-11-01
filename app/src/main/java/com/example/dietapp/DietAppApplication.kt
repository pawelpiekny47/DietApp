package com.example.dietapp

import android.app.Application
import com.example.dietapp.container.AppContainer
import com.example.dietapp.container.AppDataContainer

class DietAppApplication: Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}