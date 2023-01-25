package com.example.mint_chord_writer

import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable


class GString(mute: Boolean) : Serializable {
    //if keys Int Finger
    var keys: LinkedHashMap<Int, Finger> = LinkedHashMap<Int, Finger>()
    var mute: Boolean = mute

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
}