package com.example.snake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.media.MediaPlayer
import com.example.snake.Classification.Companion.musicFin

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val play = findViewById<Button>(R.id.play)
        val clas = findViewById<Button>(R.id.clas)

        play.setOnClickListener {
            if(!musicFin){
                val mp: MediaPlayer = MediaPlayer.create(this, R.raw.game)
                mp.isLooping
                mp.start()
            }
            musicFin = true
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        clas.setOnClickListener {
            val list = Intent(this, PointList::class.java)
            startActivity(list)
        }
    }
}