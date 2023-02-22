package com.example.mint_chord_writer

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nuri_chord_writer.DataStoreManager
import com.example.mint_chord_writer.databinding.ActivityMainBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private var songList = ArrayList<Song>()
    private lateinit var songsAdapter: SongsAdapter
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var mainActivityBinding: ActivityMainBinding
    private var processing = false

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
        lifecycleScope.launch {
            val songIds = JSONArray()
            for(i in songList) {
                songIds.put(i.id)
            }
            val process = async {dataStoreManager.save("song_ids", songIds.toString())}
            process.await()
            Log.d("aaa", "saving song_ids")
        }
        super.onPause()
    }

    override fun onBackPressed() {
        if(!processing) {
            finishAffinity()
            finish()
        }
    }

    private fun prepareSongs() {
        lifecycleScope.launch {
            try {
                delay(100)
                Log.d("aaa", "getting value from Main")
                var stringifiedSongIds = dataStoreManager.getSongIds()
                if (stringifiedSongIds != "null") {
                    Log.d("aaa", stringifiedSongIds)
                    val arr = JSONArray(stringifiedSongIds)
                    var sameTitleCount = HashMap<String, Int>()
                    var i = 0
                    while(i < arr.length()) {
                        val songId = arr.get(i).toString()
                        Log.d("aaa", songId)
                        if (dataStoreManager.read(songId).toString() != "null") {
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
                            i++
                        } else {
                            arr.remove(i)
                        }
                    }
                    processing = true
                    val saveProcess = async { dataStoreManager.save("song_ids", arr.toString()) }
                    saveProcess.await()
                    processing = false
                    Log.d("aaa", arr.toString())
                    //update the UI
                    mainActivityBinding.mainSongList.adapter?.notifyDataSetChanged()
                }
            } catch (e: java.lang.Exception) {
                Log.d("aaa", e.toString())
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
            val chord = Chord("")
            val jsonChord = JSONObject(jsonChords[i].toString())
            chord.name = jsonChord.get("name").toString()
            chord.startingFret = jsonChord.get("startingFret") as Int
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
            processing = true
            Log.d("aaa", "delete started")
            val saveProcess = async {dataStoreManager.remove(songId)}
            saveProcess.await()
            processing = false
            Log.d("aaa", "delete ended")
        }
    }
}