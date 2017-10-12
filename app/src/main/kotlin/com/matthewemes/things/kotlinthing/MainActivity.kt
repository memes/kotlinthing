package com.matthewemes.things.kotlinthing

import android.app.Activity
import android.os.Bundle
import com.google.android.things.pio.PeripheralManagerService
import timber.log.Timber

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate: enter, savedInstanceState = %s", savedInstanceState)
        super.onCreate(savedInstanceState)

        val gpios = PeripheralManagerService().gpioList
        if (gpios.isEmpty()) {
            Timber.i("Didn't find any GPIO pins")
        } else {
            Timber.i("Found GPIO pins: %s", gpios.joinToString())
        }
        Timber.d("onCreate: exit")
    }
}
