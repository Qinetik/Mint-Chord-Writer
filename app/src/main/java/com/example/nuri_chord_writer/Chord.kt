package com.example.mint_chord_writer

import java.io.Serializable

class Chord() : Serializable {
    private var strings: Array<GString> = arrayOf(GString(Finger.NULL, 0), GString(Finger.NULL, 0), GString(Finger.NULL, 0), GString(Finger.NULL, 0), GString(Finger.NULL, 0), GString(Finger.NULL, 0))

    fun getStrings(): Array<GString> {
        return strings
    }

    fun setGStringFingerByIndex(index: Int, finger: Finger) {
        strings[index].setFinger(finger)
    }

    fun setGStringFretByIndex(index: Int, fret: Int) {
        strings[index].setFret(fret)
    }

}