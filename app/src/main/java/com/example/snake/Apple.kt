package com.example.snake

import android.graphics.Color

class Apple(var pos: Position) {
    private var color = Color.rgb(255,0,0)

    @JvmName("getColor1")
    fun getColor() : Int{
        return color
    }

    fun getPosition() : Position{
        return pos
    }
}