package com.example.mint_chord_writer

import android.util.Log
import java.io.Serializable

class Chord() : Serializable {
    var strings: Array<GString> = arrayOf(GString(Finger.NULL, -1, false), GString(Finger.NULL, -1, false), GString(Finger.NULL, -1, false), GString(Finger.NULL, -1, false), GString(Finger.NULL, -1, false), GString(Finger.NULL, -1, false))
    var startingFret: Int = 0
    var name: String = ""

    fun setGStringFingerByIndex(index: Int, finger: Finger) {
        strings[index].finger = finger
    }

    fun setGStringFretByIndex(index: Int, fret: Int) {
        strings[index].fret = fret
    }

    fun setGStringMuteByIndex(index: Int, mute: Boolean) {
        strings[index].mute = mute
    }

    fun printChord() {
        for(i in strings) {
            Log.d("Gstring", i.toString())
        }
    }

}