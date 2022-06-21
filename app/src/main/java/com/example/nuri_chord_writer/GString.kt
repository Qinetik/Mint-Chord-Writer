package com.example.nuri_chord_writer

import java.io.Serializable


class GString(finger: Finger, fret: Int) : Serializable {
    private var finger: Finger = finger
    private var fret: Int = fret

    fun setFinger(finger: Finger) {
        this.finger = finger
    }

    fun setFret(fret: Int) {
        this.fret = fret
    }
}