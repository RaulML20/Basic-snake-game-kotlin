package com.example.snake

import android.graphics.Color.rgb


class Snake (var pos: Position, orientation : Int) {
    var color = rgb(80, 204, 56)
    var orientationS = orientation

    @JvmName("getColor1")
    fun getColor() : Int{
        return color
    }

    fun getPosition() : Position{
        return pos
    }

    fun cambiarOrientacion(x: Float, screen: Int){
        if(x > screen){
            if(orientationS == 0){
                orientationS = 3
            }else{
                orientationS--
            }
        }else{
            if(orientationS < 3){
                orientationS++
            }else{orientationS = 0}
        }
    }
}
