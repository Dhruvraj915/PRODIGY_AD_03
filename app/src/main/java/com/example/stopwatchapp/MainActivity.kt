package com.example.stopwatchapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var stopwatchText: TextView
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button

    private var isRunning = false
    private var startTime = 0L
    private var elapsedTime = 0L

    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable: Runnable = object : Runnable {
        override fun run() {
            val now = System.currentTimeMillis()
            val updatedTime = now - startTime + elapsedTime
            val minutes = (updatedTime / 1000) / 60
            val seconds = (updatedTime / 1000) % 60
            val millis = updatedTime % 1000

            stopwatchText.text = String.format("%02d:%02d:%03d", minutes, seconds, millis)

            if (isRunning) {
                handler.postDelayed(this, 10)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stopwatchText = findViewById(R.id.stopwatchText)
        startButton = findViewById(R.id.startButton)
        pauseButton = findViewById(R.id.pauseButton)
        resetButton = findViewById(R.id.resetButton)

        startButton.setOnClickListener {
            if (!isRunning) {
                startTime = System.currentTimeMillis()
                isRunning = true
                handler.post(updateRunnable)
            }
        }

        pauseButton.setOnClickListener {
            if (isRunning) {
                elapsedTime += System.currentTimeMillis() - startTime
                isRunning = false
            }
        }

        resetButton.setOnClickListener {
            isRunning = false
            elapsedTime = 0L
            stopwatchText.text = "00:00:000"
        }
    }
}
