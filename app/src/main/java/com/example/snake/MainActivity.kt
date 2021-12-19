package com.example.snake

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display

class MainActivity : AppCompatActivity() {
    var snakeEngine: SnakeEngine? = null
    lateinit var snake: Snake

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        snakeEngine = SnakeEngine(this, size)
        setContentView(snakeEngine)
    }

    override fun onResume() {
        super.onResume()
        snakeEngine!!.resume()
    }

    override fun onPause() {
        super.onPause()
        snakeEngine!!.pause()
    }
}