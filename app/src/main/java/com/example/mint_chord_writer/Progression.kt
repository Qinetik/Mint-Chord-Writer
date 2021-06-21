package com.example.mint_chord_writer

class Progression(pitch: Int) {
    var capo: Int = pitch
    var chords: ArrayList<Chord> = arrayListOf<Chord>()

}