package com.example.mint_chord_writer

class Song(capo: Int, name: String) {
    private var songName: String = name
    private var capo: Int = capo
    private var chords: ArrayList<Chord> = arrayListOf<Chord>()

    fun getSongName(): String {
        return songName
    }

    fun setSongName(name: String) {
        this.songName = name
    }

    fun getCapo(): Int {
        return capo
    }

    fun setCapo(capo: Int) {
        this.capo = capo
    }
}