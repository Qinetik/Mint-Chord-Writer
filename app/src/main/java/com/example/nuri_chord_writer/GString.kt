package com.example.mint_chord_writer

import java.io.Serializable


class GString(finger: Finger, fret: Int, mute: Boolean) : Serializable {
    var finger: Finger = finger
    var fret: Int = fret //if fret is negative, then doesn't exist
    var mute: Boolean = mute

    override fun toString(): String {
        return finger.toString() + fret.toString() + mute
    }
}