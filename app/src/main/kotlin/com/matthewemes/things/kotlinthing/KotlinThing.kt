package com.matthewemes.things.kotlinthing

import android.app.Application
import timber.log.Timber

class KotlinThing : Application() {
    val app: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.d("Created new instance")
        app.inject(this)
    }
}