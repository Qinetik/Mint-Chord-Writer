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

        val fratNumItems = listOf("1st", "2nd", "3rd", "4th", "5th")
        val adapter = ArrayAdapter(this, R.layout.frat_num_item, fratNumItems)
        fratNum.editText?.setText(fratNumItems[0])
        (fratNum.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        fingerSelection.setOnCheckedChangeListener{fingerSelection, i ->
            when(i) {
                R.id.thumbFinger -> println("T")
                R.id.oneFinger -> println("1")
                R.id.twoFinger -> println("2")
                R.id.threeFinger -> println("3")
                R.id.fourFinger -> println("4")
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

        /*
        fratNum.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                println("Text Changed: $s")
                val fratNumber = s.toString().toIntOrNull()
                when(fratNumber) {
                    3 -> fratNumExt.text = "rd"
                    2 -> fratNumExt.text = "nd"
                    1, 0, null -> fratNum.text = Editable.Factory.getInstance().newEditable("2")
                    else -> fratNumExt.text = "th"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println("Before Text Changed: $s")
            }

            @SuppressLint("ResourceType")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.length!! < 1) {
                    fratNum.text = Editable.Factory.getInstance().newEditable("2")
                    println("setting it back to default")
                }
            }
        })*/

        val mainButton = findViewById<Button>(R.id.editToMain)
        mainButton.setOnClickListener{
            finish();
        }
    }

    override fun onBackPressed() {
        println("back press")
        editTextLayOut.requestFocus()
    }

    override fun onDestroy() {
        super.onDestroy()
        println("destroyed")
    }
}