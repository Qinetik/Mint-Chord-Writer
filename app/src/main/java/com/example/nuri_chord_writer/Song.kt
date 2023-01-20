package com.example.mint_chord_writer

import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

data class Song(var name: String, var capo: Int = 0, var id: String) : Serializable {
    var chords: ArrayList<Chord> = arrayListOf<Chord>()

    init {
        chords.add(Chord())
    }

    fun addNewChord(index: Int) {
        this.chords.add(index, Chord())
    }

    fun removeChord(index: Int) {
        this.chords.removeAt(index)
        print(this.chords.size)
    }

    fun getJson(): JSONObject {
        val json = JSONObject()
        json.put("name", this.name)
        json.put("capo", this.capo)
        json.put("chords", JSONArray(this.chords.map{ it.getJson() }))
        return json
    }
}