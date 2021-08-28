package com.example.mint_chord_writer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), CellClickListener {
    private val songList = ArrayList<Song>()
    private lateinit var songsAdapter: SongsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.mainSongList)
        songsAdapter = SongsAdapter(songList, this)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = songsAdapter
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
        var song = Song(0, "first Song")
        songList.add(song)
        song = Song(0, "second Song")
        songList.add(song)
    }
}