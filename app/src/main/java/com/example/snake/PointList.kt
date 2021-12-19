package com.example.snake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.snake.Classification.Companion.pointsList

class PointList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point_list)

        var cont = 0
        val list = findViewById<ListView>(R.id.listP)
        val pointSequence = mutableListOf<String>()
        val icon = mutableListOf(R.drawable.tro)
        val pointsSorted = pointsList.sortedDescending()
        val size = pointsSorted.size-1

        while(cont < size){
            icon.add(R.drawable.tro2)
            cont++
        }

        if(pointsSorted.isNotEmpty()){
            pointsSorted.forEach {
                val result = "Pn: $it"
                pointSequence.add(result)
            }

            val adapter = ListAdapter(this, pointSequence, icon)
            list.adapter = adapter
        }
    }
}