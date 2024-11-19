package com.example.lab5v2024_2025

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    //var mySensor :MySensor? = null //odkomentować do zadania 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //do zadania 1
        //rozciągnięcie płótna na całą aktywność
        setContentView(MyGLSurfaceView(applicationContext))

        /* do zadana 2
        //odtworzenie fasady z pliku xml w celu wykonania testów
        setContentView(R.layout.activity_main)
        //nowy objekt sensora
        mySensor=MySensor(this)
        */

    }
}