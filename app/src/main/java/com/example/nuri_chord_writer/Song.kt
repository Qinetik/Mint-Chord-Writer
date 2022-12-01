package com.example.mint_chord_writer

import java.io.Serializable

data class Song(var name: String, var capo: Int = 0) : Serializable {
    var chords: ArrayList<Chord> = arrayListOf<Chord>()

    init {
        chords.add(Chord())
    }

    fun addNewChord() {
        this.chords.add(Chord())
    }

    override fun toString(): String {
        return name
    }
}