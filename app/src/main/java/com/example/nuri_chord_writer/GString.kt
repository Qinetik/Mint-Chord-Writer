package com.example.mint_chord_writer

import org.json.JSONObject
import java.io.Serializable


class GString(finger: Finger, fret: Int, mute: Boolean) : Serializable {
    var finger: Finger = finger
    var fret: Int = fret //if fret is negative, then doesn't exist
    var mute: Boolean = mute

    fun getJson():JSONObject {
        val json = JSONObject()
        json.put("finger", this.finger)
        json.put("fret", this.fret)
        json.put("mute", this.mute)
        return json
    }
    override fun toString(): String {
        return getJson().toString()
    }
}