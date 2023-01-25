package com.example.mint_chord_writer

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nuri_chord_writer.DataStoreManager
import androidx.lifecycle.viewModelScope
import com.example.mint_chord_writer.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

class MainActivity : AppCompatActivity() {
    private var songList = ArrayList<Song>()
    private lateinit var songsAdapter: SongsAdapter
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)


        mainActivityBinding.mainCreateNew.setOnClickListener {
            this.createNewSong()
        }
        val layoutManager = LinearLayoutManager(applicationContext)
        mainActivityBinding.mainSongList.layoutManager = layoutManager
        mainActivityBinding.mainSongList.itemAnimator = DefaultItemAnimator()

        dataStoreManager = DataStoreManager(this)
    }

    override fun onResume() {
        songList = ArrayList<Song>()
        songsAdapter = SongsAdapter(songList, this)
        mainActivityBinding.mainSongList.adapter = songsAdapter
        super.onResume()
        prepareSongs()
    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.launch {
            Log.d("aaa", "mainActivity onPause")
            val songIds = JSONArray()
            for(i in songList) {
                songIds.put(i.id)
            }
            dataStoreManager.save("song_ids", songIds.toString())
        }
    }

    private fun prepareSongs() {
        lifecycleScope.launch {
            delay(100)
            Log.d("aaa", "getting value from Main")
            var stringifiedSongIds = dataStoreManager.getSongIds()
            if(stringifiedSongIds != "null") {
                Log.d("aaa", stringifiedSongIds)
                val arr = JSONArray(stringifiedSongIds)
                var sameTitleCount = HashMap<String, Int>()
                for (i in 0 until arr.length()) {
                    val songId = arr.get(i).toString()
                    Log.d("aaa", songId)
                    val songData = JSONObject(dataStoreManager.read(songId).toString())
                    Log.d("aaa", songData.toString())
                    var song = prepareSongData(songId, songData)
                    if (sameTitleCount[song.name] == null) {
                        sameTitleCount[song.name] = 0
                    } else {
                        sameTitleCount[song.name] = sameTitleCount[song.name]!! + 1
                        song.name += "-" + sameTitleCount[song.name]
                    }
                    songList.add(song)
                }
                Log.d("aaa", songList.size.toString())
                //update the UI
                mainActivityBinding.mainSongList.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun prepareSongData(songId:String, songJson: JSONObject): Song {
        val song = Song(songJson.get("name").toString(), songJson.get("capo") as Int, songId)
        val jsonChords = JSONArray(songJson.get("chords").toString())
        for(i in 0 until jsonChords.length()) {
            if (i >= song.chords.size) {
                song.addNewChord(song.chords.size)
            }
            val chord = Chord()
            val jsonChord = JSONObject(jsonChords[i].toString())
            chord.name = jsonChord.get("name").toString()
            val jsonStrings = JSONArray(jsonChord.get("strings").toString())
            for(j in 0 until jsonStrings.length()) {
                val jsonString = JSONObject(jsonStrings[j].toString())
                val keys = JSONObject(jsonString.get("keys").toString())
                for(k in keys.keys()) {
                    chord.setGStringByIndex(j, k.toInt(), Finger.valueOf(keys.get(k).toString()))
                }
                chord.setGStringMuteByIndex(j, jsonString.get("mute") as Boolean)
            }
            song.chords[i] = chord
        }
        return song
    }

    fun createNewSong() {
        val intent = Intent(this, EditActivity::class.java)
        val newSong = Song("newSong", 0, UUID.randomUUID().toString())
        songList.add(newSong)
        intent.putExtra("selectedSong", newSong)
        startActivity(intent)
    }

    fun deleteSong(position: Int) {
        val songId = songList.removeAt(position).id
        Log.d("aaa", "song removed: $position by $songId")
        mainActivityBinding.mainSongList.adapter?.notifyDataSetChanged()
        lifecycleScope.launch{
            dataStoreManager.remove(songId)
        }
    }
}