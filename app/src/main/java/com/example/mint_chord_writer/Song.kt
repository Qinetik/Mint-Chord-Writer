package com.example.mint_chord_writer

data class Song(var name: String, var capo: Int = 0) {
    private var chords: ArrayList<Chord> = arrayListOf<Chord>()

    fun getSongName(): String {
        return name
    }

    fun setSongName(name: String) {
        this.name = name
    }
}