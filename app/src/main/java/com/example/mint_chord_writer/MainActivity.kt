package com.example.mint_chord_writer

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity(), CellClickListener {
    private val songList = ArrayList<Song>()
    private lateinit var songsAdapter: SongsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView: ListView = findViewById(R.id.mainSongList)
        songsAdapter = SongsAdapter(songList)
        val layoutManager = LinearLayoutManager(applicationContext)
        listView.layoutManager = layoutManager
        listView.itemAnimator = DefaultItemAnimator()
        listView.adapter = songsAdapter
        tempPrepareSongData()

        val routineButton = findViewById<Button>(R.id.mainCreateNew)

        routineButton.setOnClickListener{
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCellClickListener() {
        val intent = Intent(this, EditActivity::class.java)
        startActivity(intent)
    }

    private fun tempPrepareSongData() {
        var song = Song( "first Song", 0)
        songList.add(song)
        song = Song("second Song", 0)
        songList.add(song)
    }
}