package com.example.nuri_chord_writer

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.core.graphics.contains
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {
    private var fratButtons = arrayOf(arrayOf<Button>())
    private var currentFinger = "T"
    private var mutes = arrayOf<Boolean>(false, false, false, false, false, false)
    private lateinit var currentSong:Song

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        currentSong = intent.getSerializableExtra("selectedSong") as Song
        val capoText: TextView = findViewById(R.id.capoText)
        capoText.text = currentSong?.capo.toString()
        val songTitle: TextView = findViewById(R.id.songTitle)
        songTitle.text = currentSong?.name
        val chordNum: TextView = findViewById(R.id.chordNum)
        chordNum.text = "1/"+currentSong?.chords?.size

        val fratNumItems = listOf("1st", "2nd", "3rd", "4th", "5th")
        val adapter = ArrayAdapter(this, R.layout.frat_num_item, fratNumItems)
        fratNum.editText?.setText(fratNumItems[0])
        (fratNum.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        fingerSelection.setOnCheckedChangeListener{fingerSelection, i ->
            when(i) {
                R.id.thumbFinger -> currentFinger = "T"
                R.id.oneFinger -> currentFinger = "1"
                R.id.twoFinger -> currentFinger = "2"
                R.id.threeFinger -> currentFinger = "3"
                R.id.fourFinger -> currentFinger = "4"
            }
        }

        fratNum.setOnFocusChangeListener { _ , hasFocus: Boolean ->
            println("what happening here?")
            if (hasFocus) {
                println("focus? how does this work")
            } else {
                println("focus lost")
            }
        }

        fratButtons = arrayOf(
                arrayOf(findViewById<Button>(R.id.finger0_0), findViewById<Button>(R.id.finger0_1), findViewById<Button>(R.id.finger0_2), findViewById<Button>(R.id.finger0_3), findViewById<Button>(R.id.finger0_4)),
                arrayOf(findViewById<Button>(R.id.finger1_0), findViewById<Button>(R.id.finger1_1), findViewById<Button>(R.id.finger1_2), findViewById<Button>(R.id.finger1_3), findViewById<Button>(R.id.finger1_4)),
                arrayOf(findViewById<Button>(R.id.finger2_0), findViewById<Button>(R.id.finger2_1), findViewById<Button>(R.id.finger2_2), findViewById<Button>(R.id.finger2_3), findViewById<Button>(R.id.finger2_4)),
                arrayOf(findViewById<Button>(R.id.finger3_0), findViewById<Button>(R.id.finger3_1), findViewById<Button>(R.id.finger3_2), findViewById<Button>(R.id.finger3_3), findViewById<Button>(R.id.finger3_4)),
                arrayOf(findViewById<Button>(R.id.finger4_0), findViewById<Button>(R.id.finger4_1), findViewById<Button>(R.id.finger4_2), findViewById<Button>(R.id.finger4_3), findViewById<Button>(R.id.finger4_4)),
                arrayOf(findViewById<Button>(R.id.finger5_0), findViewById<Button>(R.id.finger5_1), findViewById<Button>(R.id.finger5_2), findViewById<Button>(R.id.finger5_3), findViewById<Button>(R.id.finger5_4))
        )

        applyCurrentSong()
    }

    fun applyCurrentSong() {
        println(currentSong)

    }

    fun goHome(view: View) {
        finish()
    }

    override fun onBackPressed() {
        println("back press")
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        println("destroyed")
    }

    fun fratClick(view: View) {
        if(view.alpha == 1.0F) {
            view.alpha = 0.0F
        } else {
            view.alpha = 1.0F
            var b = findViewById<Button>(view.id)
            b.text = currentFinger
            handleFratLine(view.getTag().toString())
        }
    }

    fun handleFratLine(position: String) {
        for(buttons in fratButtons[position.substring(0,1).toInt()]) {
            if(buttons.tag.toString() != position) {
                buttons.alpha = 0.0F
            }
        }
    }

    fun muteClick(view: View) {
        var b = findViewById<ImageButton>(view.id)
        val index = view.getTag().toString().toInt()
        if(mutes[index]) {
            b.setImageResource(R.drawable.gray_x)
            mutes[index] = false
        } else {
            b.setImageResource(R.drawable.black_x)
            mutes[index] = true
        }
    }
}