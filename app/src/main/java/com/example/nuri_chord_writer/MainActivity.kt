package com.example.mint_chord_writer

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
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
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val songList = ArrayList<Song>()
    private lateinit var songsAdapter: SongsAdapter
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val songsRecyclerView: RecyclerView = findViewById(R.id.mainSongList)
        songsAdapter = SongsAdapter(songList)
        val layoutManager = LinearLayoutManager(applicationContext)
        songsRecyclerView.layoutManager = layoutManager
        songsRecyclerView.itemAnimator = DefaultItemAnimator()
        songsRecyclerView.adapter = songsAdapter

        dataStoreManager = DataStoreManager(this)
        lifecycleScope.launch {
            Log.d("aaa", "getting value abcd")
            dataStoreManager.getSongIds()
        }
        tempPrepareSongData()
    }

    override fun onPause() {
        super.onPause()
        Log.d("aaa", "save value abcd")
        /*lifecycleScope.launch {
            Log.d("aaa", "save value abcd")
            dataStoreManager.save("song_ids", "['abcd', '1234']")
        }*/
    }



    private fun tempPrepareSongData() {
        var song = Song( "first Song", 0)
        songList.add(song)
        song = Song("second Song", 0)
        songList.add(song)
    }

    fun createNewSong() {
        val intent = Intent(this, EditActivity::class.java)
        val newSong = Song("newSong", 0)
        intent.putExtra("selectedSong", newSong)
        startActivity(intent)
    }
}