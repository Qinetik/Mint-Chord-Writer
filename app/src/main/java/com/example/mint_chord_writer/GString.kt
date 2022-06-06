package com.minthana.mint_chord_writer


class GString(finger: Finger, fret: Int) {
    private var finger: Finger = finger
    private var fret: Int = fret

    fun setFinger(finger: Finger) {
        this.finger = finger
    }

    fun setFret(fret: Int) {
        this.fret = fret
    }
}