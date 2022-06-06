package com.minthana.mint_chord_writer

class Chord() {
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