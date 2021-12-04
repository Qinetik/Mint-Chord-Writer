package com.example.mint_chord_writer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val mainButton = findViewById<Button>(R.id.editToMain)
        mainButton.setOnClickListener{
            finish();
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("destroyed")
    }
}