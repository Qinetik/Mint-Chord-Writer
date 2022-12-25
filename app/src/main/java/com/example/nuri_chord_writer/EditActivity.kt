package com.example.mint_chord_writer

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {
    private var fretButtons = arrayOf(arrayOf<Button>())
    private var currentFinger = Finger.THUMB
    private var currentChordIndex = 0
    private var mutes = arrayOf<Boolean>(false, false, false, false, false, false)
    private lateinit var currentSong:Song

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //hide navigationBar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView.findViewById(android.R.id.content)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        currentSong = intent.getSerializableExtra("selectedSong") as Song
        val songTitle: TextView = findViewById(R.id.songTitle)
        songTitle.text = currentSong?.name

        chordTitle.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.d("a", "afterChange")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("a", "preChange")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("a", s.toString())
                currentSong.chords[currentChordIndex].name = s.toString()
            }
        })

        fretNumButton.setText("1st")
        fretNumButton.setOnClickListener {
            PopupMenu(this!!, fretNumButton).apply {
                menuInflater.inflate(R.menu.start_fret_menu, menu)
                setOnMenuItemClickListener { item ->
                    fretNumButton.setText(item.title)
                    setFretNum(item.title.substring(0,1).toInt())
                    true
                }
                show()
            }
        }

        capoButton.setText(currentSong?.capo.toString())
        capoButton.setOnClickListener {
            PopupMenu(this!!, capoButton).apply {
                menuInflater.inflate(R.menu.capo_menu, menu)
                setOnMenuItemClickListener { item ->
                    capoButton.setText(item.title)
                    true
                }
                show()
            }
        }

        fingerSelection.setOnCheckedChangeListener{fingerSelection, i ->
            when(i) {
                R.id.thumbFinger -> currentFinger = Finger.THUMB
                R.id.oneFinger -> currentFinger = Finger.ONE
                R.id.twoFinger -> currentFinger = Finger.TWO
                R.id.threeFinger -> currentFinger = Finger.THREE
                R.id.fourFinger -> currentFinger = Finger.FOUR
                R.id.nullFinger -> currentFinger = Finger.NULL
            }
        }

        fretNum.setOnFocusChangeListener { _ , hasFocus: Boolean ->
            Log.d("focus check","what happening here?")
            if (hasFocus) {
                Log.d("focus check","focus? how does this work")
            } else {
                Log.d("focus check","focus lost")
            }
        }

        fretButtons = arrayOf(
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
        renderChordNum()
        renderChordTitle()
    }

    fun goHome(view: View) {
        finish()
    }

    fun addNewChord(view: View) {
        currentSong.addNewChord()
        currentChordIndex = currentSong?.chords?.size-1
        renderChordNum()
        renderChordTitle()
    }

    fun deleteCurrentChord(view: View) {
        if(currentSong?.chords?.size > 1) {
            currentSong.removeChord(currentChordIndex)
            if(currentChordIndex > 0)
                currentChordIndex--
        }
        renderChordNum()
        renderChordTitle()
    }

    fun renderChordTitle() {
        val chordTitle: TextView = findViewById(R.id.chordTitle)
        chordTitle.text = currentSong?.chords[currentChordIndex].name
    }

    fun renderChordNum() {
        chordNum.text = (currentChordIndex + 1).toString() + "/" + currentSong?.chords?.size
        loadChord()
    }

    fun movePrevChord(view: View) {
        if(currentChordIndex > 0) {
            currentChordIndex--
            renderChordNum()
            renderChordTitle()
        }
    }

    fun moveNextChord(view: View) {
        if(currentChordIndex < currentSong?.chords?.size-1) {
            currentChordIndex++
            renderChordNum()
            renderChordTitle()
        }
    }

    override fun onBackPressed() {
        println("back press")
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        println("destroyed")
    }

    fun fretClick(view: View) {
        if(view.alpha == 1.0F) {
            view.alpha = 0.0F
            val b = findViewById<Button>(view.id)
            b.text = convertFingerEnumToString(currentFinger)
            val position = view.getTag().toString()
            currentSong.chords[currentChordIndex].setGStringFretByIndex(getGstringIndex(position), -1)
        } else {
            view.alpha = 1.0F
            val b = findViewById<Button>(view.id)
            b.text = convertFingerEnumToString(currentFinger)
            val position = view.getTag().toString()
            currentSong.chords[currentChordIndex].setGStringFingerByIndex(getGstringIndex(position), currentFinger)
            currentSong.chords[currentChordIndex].setGStringFretByIndex(getGstringIndex(position), getGstringFret(position))
            handleFretLine(position)
        }
    }

    fun handleFretLine(position: String) {
        for(buttons in fretButtons[getGstringIndex(position)]) {
            if(buttons.tag.toString() != position) {
                buttons.alpha = 0.0F
            }
        }
    }

    fun loadChord() {
        val currentChord = currentSong.chords[currentChordIndex]
        fretNumButton.setText(convertStartFretNumToTitle(currentChord.startingFret))
        for(i in 0..5) {
            for(button in fretButtons[i]) {
                //buttonTag ex: "01"
                if(getGstringFret(button.tag.toString()) != currentChord.strings[i].fret) {
                    button.alpha = 0.0F
                } else {
                    button.alpha = 1.0F
                    button.text = convertFingerEnumToString(currentChord.strings[i].finger)
                }
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

    fun setFretNum(startingFret: Int) {
        currentSong.chords[currentChordIndex].startingFret = startingFret-1
    }

    //helper functions below
    fun getGstringIndex(position: String): Int {
        return position.substring(0,1).toInt()
    }

    fun getGstringFret(position: String): Int {
        return position.substring(1,2).toInt()
    }

    fun convertFingerEnumToString(finger: Finger): String {
        return when (finger) {
            Finger.THUMB -> "T"
            Finger.ONE -> "1"
            Finger.TWO -> "2"
            Finger.THREE -> "3"
            Finger.FOUR -> "4"
            Finger.NULL -> ""
        }
    }

    fun convertStartFretNumToTitle(startingFret: Int): String {
        return when (startingFret) {
            1 -> "2nd"
            2 -> "3rd"
            3 -> "4th"
            4 -> "5th"
            5 -> "6th"
            6 -> "7th"
            else -> "1st"
        }
    }
}