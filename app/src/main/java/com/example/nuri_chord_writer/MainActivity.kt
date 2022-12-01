package com.example.mint_chord_writer

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val songList = ArrayList<Song>()
    private lateinit var songsAdapter: SongsAdapter

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

        tempPrepareSongData()
    }

    private fun tempPrepareSongData() {
        var song = Song( "first Song", 0)
        songList.add(song)
        song = Song("second Song", 0)
        songList.add(song)
    }

    fun createNewSong(view: View) {
        val intent = Intent(this, EditActivity::class.java)
        val newSong = Song("newSong", 0)
        intent.putExtra("selectedSong", newSong)
        startActivity(intent)
    }
}