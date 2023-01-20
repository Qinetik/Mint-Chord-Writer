package com.example.mint_chord_writer

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

class Chord() : Serializable {
    var strings: Array<GString> = arrayOf(GString(Finger.NULL, -1, false), GString(Finger.NULL, -1, false), GString(Finger.NULL, -1, false), GString(Finger.NULL, -1, false), GString(Finger.NULL, -1, false), GString(Finger.NULL, -1, false))
    var startingFret: Int = 0
    var name: String = ""

    fun setGStringFingerByIndex(index: Int, finger: Finger) {
        strings[index].finger = finger
    }

    fun setGStringFretByIndex(index: Int, fret: Int) {
        strings[index].fret = fret
    }

    fun setGStringMuteByIndex(index: Int, mute: Boolean) {
        strings[index].mute = mute
    }

    fun getJson(): JSONObject {
        val json = JSONObject()
        json.put("name", this.name)
        json.put("startingFret", this.startingFret)
        json.put("strings", JSONArray(this.strings.map{ it.getJson() }))
        return json
    }

}