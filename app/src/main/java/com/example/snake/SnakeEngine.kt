package com.example.snake

import android.annotation.SuppressLint
import android.content.Context
import android.view.SurfaceView
import android.view.SurfaceHolder
import android.view.MotionEvent
import android.graphics.*

import com.example.snake.Classification.Companion.pointsList

@SuppressLint("ViewConstructor")
class SnakeEngine (mainActivity: MainActivity, size : Point) : SurfaceView(mainActivity), Runnable{

    var points = 0
    var numBlocksWidth = 0
    val snakes = mutableListOf<Snake>()
    lateinit var thread: Thread
    var fin = false
    private var context2 : Context? = context
    var appleState = true
    private var screenX = 0
    private var screenY = 0
    private var blockSize = 0
    private var canvas: Canvas? = null
    private var surfaceHolder: SurfaceHolder? = null
    var paint : Paint
    private var paint2 : Paint
    var paintApple : Paint
    var numBlocksHigh = 0
    lateinit var snake: Snake
    lateinit var apple: Apple
    private var isPlaying = false

    init{
        context2 = mainActivity
        screenX = size.x
        screenY = size.y
        blockSize = screenX / 28
        numBlocksWidth = screenX / blockSize
        numBlocksHigh = screenY / blockSize
        surfaceHolder = holder
        paint = Paint()
        paint2 = Paint()
        paintApple = Paint()
        newGame()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun run() {
        while (isPlaying) {
            //va muy rapido, no es jugable
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            update()
            fin = isSnakeDeath()
            eatApple()
            draw()
            if(fin){
                isPlaying = false

                //Añadir clasificación
                val num = points
                var found = false

                for (i in pointsList) {
                    if (i == num) {
                        found = true
                        break
                    }
                }

                if(!found){
                    pointsList.add(points)
                }

                canvas = surfaceHolder!!.lockCanvas()
                canvas?.drawColor(Color.argb(255, 0, 0, 0))

                val paintDead = Paint()
                val paintPoints = Paint()
                paintDead.color = Color.RED
                paintPoints.color = Color.WHITE
                paintPoints.textAlign = Paint.Align.CENTER
                paintPoints.textSize = 70f
                paintDead.textAlign = Paint.Align.CENTER
                paintDead.textSize = 100f
                val point = "GAME OVER"
                val point2 = "Puntuación: $points"
                canvas?.drawText(point, (screenX/2).toFloat(), ((screenY/2) - 200).toFloat(), paintDead)
                canvas?.drawText(point2, (screenX/2).toFloat(), ((screenY/2)).toFloat(), paintPoints)
                surfaceHolder!!.unlockCanvasAndPost(canvas)
            }
        }
    }

    //Añadir puntuación
    fun points(){
        val paintText = Paint()
        paintText.color = Color.WHITE
        paintText.textSize = 60f
        val point = "Puntuación: $points"
        canvas?.drawText(point, 100F, 120F, paintText)
    }

    fun pause() {
        isPlaying = false
        try {
            thread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun resume() {
        isPlaying = true
        thread = Thread(this)
        thread.start()
    }

    private fun newGame() {
        val pos = Position(50 / 2, numBlocksHigh / 2)
        snake = Snake(pos, 0)
        snakes.add(snake)
        points()
        spawnApple()
    }

    private fun spawnApple(){
        val num = (2..(numBlocksWidth-2)).random()
        val num2 = (2..(numBlocksHigh-2)).random()
        val pos2 = Position(num, num2)
        apple = Apple(pos2)
    }

    //Comer manzana y crear otro elemento Snake
    private fun eatApple(){
        if(apple.pos.x == snake.pos.x && apple.pos.y == snake.pos.y){
            val num = (2..(numBlocksWidth-2)).random()
            val num2 = (2..(numBlocksHigh-2)).random()
            val pos2 = Position(num, num2)
            apple.pos = pos2
            points++

            val last = snakes.size-1
            val pos = Position(snakes[last].getPosition().x, snakes[last].getPosition().y)
            val orientation = snakes[last].orientationS
            val snakeNew = Snake(pos, orientation)
            snakeNew.color = Color.rgb(83, 144, 43)
            snakes.add(snakeNew)
        }
    }

    //Comprobar si la serpiente está muerta
    private fun isSnakeDeath() : Boolean{
        if(snakes[0].getPosition().x == 0 || snakes[0].getPosition().y == 0 || snakes[0].getPosition().x == numBlocksWidth || snakes[0].getPosition().y == numBlocksHigh){
            return true
        }

        snakes.forEach{
            if(it != snakes[0]){
                if(it.getPosition().x == snakes[0].getPosition().x && it.getPosition().y == snakes[0].getPosition().y){
                    return true
                }
            }
        }
        return false
    }

    fun update() {
        for (i in snakes.size - 1 downTo 0) {
            if(i != 0){
                snakes[i].pos.x = snakes[i-1].getPosition().x
                snakes[i].pos.y = snakes[i-1].getPosition().y
            }
        }

        when (snakes[0].orientationS) {
            0 -> {
                snakes[0].pos.y -= 1
            }
            1 -> {
                snakes[0].pos.x -= 1
            }
            2 -> {
                snakes[0].pos.y += 1
            }
            else -> {
                snakes[0].pos.x += 1
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun draw(){
        if (surfaceHolder?.surface!!.isValid) {
            canvas = surfaceHolder!!.lockCanvas()
            canvas?.drawColor(Color.rgb(157,207,221))
            paint.color = snake.getColor()
            paint2.color = Color.rgb(83, 144, 43)

            //PRINT HEAD

            canvas?.drawRect(
                (snakes[0].getPosition().x * blockSize ).toFloat(),
                ((snakes[0].getPosition().y * blockSize).toFloat()),
                (((snakes[0].getPosition().x * blockSize) + blockSize).toFloat()),
                ((snakes[0].getPosition().y * blockSize) + blockSize).toFloat(),
                paint2)

            //PRINT BODY
            for (i in snakes){
                if(i != snakes[0]){
                    canvas?.drawRect(
                        (i.getPosition().x * blockSize).toFloat(),
                        ((i.getPosition().y * blockSize).toFloat()),
                        ((i.getPosition().x * blockSize) + blockSize).toFloat(),
                        ((i.getPosition().y * blockSize) + blockSize).toFloat(),
                        paint
                    )
                }
            }

            //PRINT APPLE

            if(appleState){
                paintApple.color = apple.getColor()
                canvas?.drawRect(
                    (apple.getPosition().x * blockSize).toFloat(),
                    ((apple.getPosition().y * blockSize).toFloat()),
                    ((apple.getPosition().x * blockSize) + blockSize).toFloat(),
                    ((apple.getPosition().y * blockSize) + blockSize).toFloat(),
                    paintApple)
            }

            points()
            // Unlock the canvas and reveal the graphics for this frame
            surfaceHolder!!.unlockCanvasAndPost(canvas)

        }
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val x =  motionEvent.x
        when (motionEvent.action) {
            MotionEvent.ACTION_UP -> {
                snakes[0].cambiarOrientacion(x, screenX/2)
            }
        }
        return true
    }
}