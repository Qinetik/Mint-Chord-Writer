package com.example.mint_chord_writer

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

data class Chord(var name: String) : Serializable {
    var strings: Array<GString> = arrayOf(GString(false), GString(false), GString(false), GString(false), GString(false), GString(false))
    var startingFret: Int = 0

    fun setGStringByIndex(index: Int, fret: Int, finger: Finger) {
        strings[index].keys[fret] = finger
    }

    fun removeGStringByIndex(index: Int, fret: Int) {
        strings[index].keys.remove(fret)
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

    fun copy(): Chord {
        var copy = Chord(name)
        copy.startingFret = startingFret
        for(i in 0 .. 5) {
            copy.strings[i] = strings[i].copy()
        }
        return copy
    }

}