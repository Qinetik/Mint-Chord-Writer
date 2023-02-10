package com.example.mint_chord_writer

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mint_chord_writer.databinding.ActivityEditBinding
import com.example.nuri_chord_writer.DataStoreManager
import kotlinx.coroutines.launch


class EditActivity : AppCompatActivity() {
    private var fretButtons = arrayOf(arrayOf<Button>())
    private var currentFinger = Finger.THUMB
    var currentChordIndex = 0
    private var mutes = arrayOf(false, false, false, false, false, false)
    private lateinit var currentSong:Song
    private var chordCardList = arrayListOf<ChordCard>()
    private lateinit var editActivityBinding: ActivityEditBinding
    private lateinit var dataStoreManager: DataStoreManager
    private var longBars = arrayOf(ArrayList<View>(), ArrayList<View>(), ArrayList<View>(),
        ArrayList<View>(), ArrayList<View>(), ArrayList<View>())//key is fret #

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState)
        editActivityBinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(editActivityBinding.root)

        dataStoreManager = DataStoreManager(this)

        //hide navigationBar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView.findViewById(android.R.id.content)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.currentSong = intent.getSerializableExtra("selectedSong", Song::class.java)!!
        } else {
            this.currentSong = intent.getSerializableExtra("selectedSong") as Song
        }
        editActivityBinding.songTitle.setText(currentSong?.name)

        editActivityBinding.chordTitle.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.d("aaa", "afterChange")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("aaa", "preChange")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("aaa", "onChange")
                currentSong.chords[currentChordIndex].name = s.toString()
                updateChordListView(currentChordIndex, s.toString(), false)
            }
        })

        editActivityBinding.fretNumButton.setText("1st")
        editActivityBinding.fretNumButton.setOnClickListener {
            PopupMenu(this!!, editActivityBinding.fretNumButton).apply {
                menuInflater.inflate(R.menu.start_fret_menu, menu)
                setOnMenuItemClickListener { item ->
                    editActivityBinding.fretNumButton.setText(item.title)
                    setFretNum(item.title?.substring(0,1)?.toInt()?: 0 )
                    true
                }
                show()
            }
        }

        editActivityBinding.capoButton.setText(currentSong?.capo.toString())
        editActivityBinding.capoButton.setOnClickListener {
            PopupMenu(this!!, editActivityBinding.capoButton).apply {
                menuInflater.inflate(R.menu.capo_menu, menu)
                setOnMenuItemClickListener { item ->
                    editActivityBinding.capoButton.setText(item.title)
                    Log.d("aaa", "sleep")
                    currentSong.capo = item.title.toString().toInt()
                    true
                }
                show()
            }
        }

        editActivityBinding.fingerSelection.setOnCheckedChangeListener{fingerSelection, i ->
            when(i) {
                R.id.thumbFinger -> currentFinger = Finger.THUMB
                R.id.oneFinger -> currentFinger = Finger.ONE
                R.id.twoFinger -> currentFinger = Finger.TWO
                R.id.threeFinger -> currentFinger = Finger.THREE
                R.id.fourFinger -> currentFinger = Finger.FOUR
                R.id.nullFinger -> currentFinger = Finger.NULL
            }
        }

        editActivityBinding.fretNum.setOnFocusChangeListener { _ , hasFocus: Boolean ->
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

        val layout = findViewById<ConstraintLayout>(R.id.editScreen)
        layout.post({
            applyCurrentSong()
        })
        //applyCurrentSong()
    }

    override fun onPause() {
        saveCurrentWork()
        super.onPause()
    }

    private fun saveCurrentWork() {
        currentSong.name = editActivityBinding.songTitle.text.toString()
        lifecycleScope.launch {
            Log.d("aaa", "saving current Song")
            Log.d("aaa", currentSong.id)
            Log.d("aaa", currentSong.getJson().toString())
            dataStoreManager.save(currentSong.id, currentSong.getJson().toString())
            Log.d("aaa", "saving here")
        }
    }

    fun applyCurrentSong() {
        prepareChordArrays()
        renderChordNum()
        renderChordTitle()
    }

    fun goHome(view: View) {
        val data = Intent()
        data.putExtra("editedSong", currentSong)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    fun addNewChord(view: View) {
        Log.d("aaa", "addNewChord")
        currentSong.addNewChord(currentChordIndex+1)
        updateChordListView(currentChordIndex, null, false)
        currentChordIndex++
        renderChordNum()
        renderChordTitle()
    }

    fun deleteCurrentChord(view: View) {
        Log.d("aaa", "deleteChord")
        if(currentSong?.chords?.size > 1) {
            currentSong.removeChord(currentChordIndex)
            if(currentChordIndex > 0)
                currentChordIndex--
            updateChordListView(currentChordIndex, null, true)
            renderChordNum()
            renderChordTitle()
        }
    }

    fun prepareChordArrays() {
        for(i in currentSong.chords) {
            chordCardList.add(ChordCard(i.name))
        }
        editActivityBinding.chordListView.layoutManager = LinearLayoutManager(this)
        editActivityBinding.chordListView.adapter = ChordsAdapter(chordCardList, this)
    }

    fun updateChordListView(index: Int, name: String?, isRemove: Boolean) {
        if(name == null) {
            if(isRemove) {
                chordCardList.removeAt(index)
            } else {
                chordCardList.add(index+1, ChordCard(""))
            }
        } else {
            chordCardList[index].name = name
        }
        editActivityBinding.chordListView.adapter?.notifyDataSetChanged()
    }

    fun renderChordTitle() {
        val chordTitle: TextView = findViewById(R.id.chordTitle)
        chordTitle.text = currentSong?.chords[currentChordIndex].name
    }

    fun renderChordNum() {
        editActivityBinding.chordNum.text = (currentChordIndex + 1).toString() + "/" + currentSong?.chords?.size
        Log.d("aaa", editActivityBinding.chordNum.text as String)
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

    fun moveToChord(index: Int) {
        currentChordIndex = index
        renderChordNum()
        renderChordTitle()
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
        Log.d("aaa", "button clicked")
        val b = findViewById<Button>(view.id)
        b.text = convertFingerEnumToString(currentFinger)
        val position = view.tag.toString()
        if(view.alpha == 1.0F) {
            view.alpha = 0.0F
            currentSong.chords[currentChordIndex].removeGStringByIndex(getGstringIndex(position), getGstringFret(position))
            handleLongBar(getGstringFret(position))
        } else {
            view.alpha = 1.0F
            currentSong.chords[currentChordIndex].setGStringByIndex(getGstringIndex(position), getGstringFret(position), currentFinger)
            handleLongBar(getGstringFret(position))
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
        for(i in 0 .. 4) {
            handleLongBar(i)
        }
        val currentChord = currentSong.chords[currentChordIndex]
        editActivityBinding.fretNumButton.setText(convertStartFretNumToTitle(currentChord.startingFret))
        Log.d("aaa", currentChord.startingFret.toString())
        Log.d("aaa", convertStartFretNumToTitle(currentChord.startingFret))
        for(i in 0..5) {
            for(button in fretButtons[i]) {
                //buttonTag ex: "01"
                val fret = getGstringFret(button.tag.toString())
                if(currentChord.strings[i].keys[fret] == null) {
                    button.alpha = 0.0F
                } else {
                    button.alpha = 1.0F
                    button.text = convertFingerEnumToString(currentChord.strings[i].keys[fret]!!)
                }
            }
            mutes[i] = !currentChord.strings[i].mute
            muteClick(findViewById(resources.getIdentifier("string"+(i+1)+"MuteButton", "id", packageName)))
        }
    }

    fun muteClick(view: View) {
        var b = findViewById<ImageButton>(view.id)
        val index = view.tag.toString().toInt()
        mutes[index] = !mutes[index]
        currentSong.chords[currentChordIndex].strings[index].mute = mutes[index]
        if(mutes[index]) {
            b.setImageResource(R.drawable.black_x)
        } else {
            b.setImageResource(R.drawable.gray_x)
        }
    }

    fun handleLongBar(fret: Int) {
        clearFretLongBars(fret)
        var stringIndex = 0
        var startStringIndex = 0
        var endStringIndex = 0
        var targetFinger : Finger? = null
        val currentChord = currentSong.chords[currentChordIndex]
        while(stringIndex < 6) {
            val currentFinger = currentChord.strings[stringIndex].keys[fret]
            if(currentFinger != null) {//in case if current finger exists
                if(currentFinger == targetFinger) {//longBar continues
                    endStringIndex = stringIndex
                } else {//check for new longBar and start new longBar
                    if(endStringIndex - startStringIndex > 0 && targetFinger != null) {
                        createLongBar(fret, startStringIndex, endStringIndex, targetFinger)
                    }
                    startStringIndex = stringIndex
                    endStringIndex = stringIndex
                    targetFinger = currentFinger
                }
                stringIndex++
            } else {//in case if current finger doesn't exist
                if(endStringIndex - startStringIndex > 0 && targetFinger != null) {
                    createLongBar(fret, startStringIndex, endStringIndex, targetFinger)
                }
                stringIndex++
                startStringIndex = stringIndex
                endStringIndex = stringIndex
                targetFinger = null
            }
        }
        //at the end
        if(endStringIndex - startStringIndex > 0 && targetFinger != null) {
            createLongBar(fret, startStringIndex, endStringIndex, targetFinger)
        }
    }

    fun clearFretLongBars(fret: Int) {
        val layout = findViewById<ConstraintLayout>(R.id.editScreen)
        while(longBars[fret].size > 0) {
            layout.removeView(longBars[fret].removeAt(0))
        }
    }

    fun createLongBar(fret: Int, startString: Int, endString: Int, finger: Finger) {
        val longBar = TextView(this)
        val startingButton = fretButtons[startString][fret]
        val endingButton = fretButtons[endString][fret]
        longBar.layoutParams = ConstraintLayout.LayoutParams(startingButton.width, (endingButton.y - startingButton.y + startingButton.height).toInt())
        longBar.x = startingButton.x
        longBar.y = startingButton.y
        longBar.text = convertFingerEnumToString(finger)
        longBar.gravity = Gravity.CENTER
        longBar.elevation = 10F
        longBar.setTextColor(ContextCompat.getColor(this, R.color.white))
        val layout = findViewById<ConstraintLayout>(R.id.editScreen)
        longBar.background = ContextCompat.getDrawable(this, R.drawable.finger_bar)
        longBars[fret].add(longBar)
        layout?.addView(longBar)
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