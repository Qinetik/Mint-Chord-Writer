package com.minthana.mint_chord_writer

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val currentSong = intent.getSerializableExtra("selectedSong") as Song?
        val capoText: TextView = findViewById(R.id.capoText)
        capoText.text = currentSong?.capo.toString()
        val songTitle: TextView = findViewById(R.id.songTitle)
        songTitle.text = currentSong?.name
        val chordNum: TextView = findViewById(R.id.chordNum)
        chordNum.text = "1/"+currentSong?.chords?.size


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