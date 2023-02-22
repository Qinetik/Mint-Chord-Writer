package com.example.mint_chord_writer

import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable


data class GString(var mute: Boolean) : Serializable {
    //if keys Int Finger
    var keys: LinkedHashMap<Int, Finger> = LinkedHashMap<Int, Finger>()

    fun getJson():JSONObject {
        val json = JSONObject()
        val keys = JSONObject()
        for(i in this.keys.keys) {
            keys.put(i.toString(), this.keys[i])
        }
        json.put("keys", keys)
        json.put("mute", this.mute)
        return json
    }

    override fun toString(): String {
        return getJson().toString()
    }

    fun copy() : GString {
        var copy = GString(this.mute)
        copy.keys = LinkedHashMap(this.keys)
        return copy
    }
}