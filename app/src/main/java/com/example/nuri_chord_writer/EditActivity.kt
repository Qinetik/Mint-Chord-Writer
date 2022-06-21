package com.example.nuri_chord_writer

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_edit.*

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

        fingerSelection.setOnCheckedChangeListener{fingerSelection, i ->
            when(i) {
                R.id.thumbFinger -> println("Thumb")
                R.id.oneFinger -> println("1")
                R.id.twoFinger -> println("2")
                R.id.threeFinger -> println("3")
                R.id.fourFinger -> println("4")
            }
        }

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