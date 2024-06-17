package com.example.eggcatcher

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import java.lang.Exception

class GameView(var c: GameActivity, var gameTask: GameTask) : View(c) {
    private var myPaint: Paint? = null
    private var level = 1
    private var time = 0
    private var score = 0
    private var mainPosition = 0
    private val eggs = ArrayList<HashMap<String, Any>>()

    var viewWidth = 0
    var viewHeight = 0

    init {
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if (time % 700 < 10 + level) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            eggs.add(map)
        }

        time += 10 + level
        val mainWidth = viewWidth / 4
        val mainHeight = mainWidth - 50
        myPaint!!.style = Paint.Style.FILL

        val main = resources.getDrawable(R.drawable.basket, null)
        main.setBounds(
            mainPosition * viewWidth / 3 + viewWidth / 15 + 25,
            viewHeight - 150 - mainHeight,
            mainPosition * viewWidth / 3 + viewWidth / 15 + mainWidth - 25,
            viewHeight - 150
        )
        main.draw(canvas)

        myPaint!!.color = Color.GREEN

        val highScore = 0

        val eggsToRemove = mutableListOf<Int>()

        for (i in eggs.indices) {
            try {
                val eggWidth = viewWidth / 10
                val eggHeight = viewHeight / 15

                val eggX = eggs[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                var eggY = time - eggs[i]["startTime"] as Int
                val egg = resources.getDrawable(R.drawable.egg, null)

                egg.setBounds(
                    eggX + (viewWidth / 15),
                    eggY - eggHeight,
                    eggX + (viewWidth / 15) + eggWidth,
                    eggY
                )
                egg.draw(canvas)

                if (eggs[i]["lane"] as Int == mainPosition) {
                    if (eggY > viewHeight - 150 - mainHeight && eggY < viewHeight - 150) {
                        score++
                        eggsToRemove.add(i)
                        level = 1 + Math.abs(score / 8)
                    }
                }

                if (eggY > viewHeight) {
                    gameTask.closeGame(score)
                    return
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        for (index in eggsToRemove.reversed()) {
            eggs.removeAt(index)
        }

        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 50f
        canvas.drawText("Score: $score", 80f, 80f, myPaint!!)
        canvas.drawText("Level: $level", 380f, 80f, myPaint!!)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if (x1 < viewWidth / 2) {
                    if (mainPosition > 0) {
                        mainPosition--
                    }
                }
                if (x1 > viewWidth / 2) {
                    if (mainPosition < 2) {
                        mainPosition++
                    }
                }
                invalidate()
            }
        }
        return true
    }
}
