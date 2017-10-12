package com.matthewemes.things.kotlinthing

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService
import timber.log.Timber

class MainActivity : Activity() {

    companion object {
        val GPIO_PIN_NAME = "BCM6"
        val BLINK_INTERVAL_MS = 1000L
    }

    private val handler = Handler()
    private var gpio: Gpio? = null
    private var blinker: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate: enter, savedInstanceState = %s", savedInstanceState)
        super.onCreate(savedInstanceState)
        val service = PeripheralManagerService()
        try {
            gpio = service.openGpio(GPIO_PIN_NAME)
            gpio?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
            if (blinker == null) {
                blinker = Runnable {
                    if (gpio != null) {
                        try {
                            gpio!!.value = !gpio!!.value
                            handler.postDelayed(blinker, BLINK_INTERVAL_MS)
                        } catch (t: Throwable) {
                            Timber.e(t, "Caught an exception in blinker runnable")
                        }
                    }
                }
            }
            handler.post(blinker)
        } catch (t: Throwable) {
            Timber.e(t, "Caught exception setting up GPIO")
        }
        Timber.d("onCreate: exit")
    }

    override fun onDestroy() {
        Timber.d("onDestroy: enter")
        super.onDestroy()
        handler.removeCallbacks(blinker)
        try {
            gpio?.value = false
            gpio?.close()
            gpio = null
        } catch (t: Throwable) {
            Timber.e(t, "Caught an exception shutting off GPIO")
        }
        Timber.d("onDestroy: exit")
    }
}
