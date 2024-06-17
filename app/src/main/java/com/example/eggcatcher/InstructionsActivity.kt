package com.example.eggcatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class InstructionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

        val instructionsButton = findViewById<Button>(R.id.backToGame)

        instructionsButton.setOnClickListener {
            val intent = Intent(this,GameActivity::class.java)
            startActivity(intent)
        }
    }
}