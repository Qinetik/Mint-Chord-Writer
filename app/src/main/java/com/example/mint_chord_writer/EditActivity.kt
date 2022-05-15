package com.minthana.mint_chord_writer

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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