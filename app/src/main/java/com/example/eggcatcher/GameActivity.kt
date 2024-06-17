package com.example.eggcatcher

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class GameActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout : ConstraintLayout
    lateinit var new_game : Button
    lateinit var mGameView :GameView
    lateinit var score: TextView
    lateinit var highScore: TextView
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        new_game = findViewById(R.id.new_game)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highScore = findViewById(R.id.highScore)

        val exitButton: Button = findViewById(R.id.exit)
        val instructionsButton: Button = findViewById(R.id.instructions)

        instructionsButton.setOnClickListener {
            val intent = Intent(this,InstructionsActivity::class.java)
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            finishAffinity()
        }

        sharedPreferences = getSharedPreferences("my_pref", 0)
        var highest = sharedPreferences.getInt("highest", 0)

        score.text = highest.toString()
        mGameView = GameView(this, this)

        new_game.setOnClickListener{
            mGameView.setBackgroundResource(R.drawable.background1)
            rootLayout.addView(mGameView)
            new_game.visibility = View.GONE
            score.visibility = View.GONE
            highScore.visibility = View.GONE

        }
    }

    override fun closeGame(mScore: Int) {
        highScore.text = getString(R.string.your_score)
        score.text = "$mScore"
        new_game.text = getString(R.string.play_again)
        rootLayout.removeView(mGameView)
        new_game.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        highScore.visibility = View.VISIBLE

        sharedPreferences = getSharedPreferences("my_pref", 0)
        var highest = sharedPreferences.getInt("highest", 0)
        if (mScore > highest) {
            highest = mScore
            val editor = sharedPreferences.edit()
            editor.putInt("highest", highest)
            editor.commit()
        }

        new_game.setOnClickListener {
            recreate()
        }
    }
}


