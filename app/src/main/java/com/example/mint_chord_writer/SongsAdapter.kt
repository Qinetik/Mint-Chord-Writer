package com.example.mint_chord_writer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView


internal class SongsAdapter(private var songsList: List<Song>) : RecyclerView.Adapter<SongsAdapter.ViewHolder>() {
    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var songName: TextView = view.findViewById(R.id.songName)
        var capo: TextView = view.findViewById(R.id.capo)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_song_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = songsList[position]
        holder.songName.text = song.getSongName()
        holder.capo.text = song.getCapo().toString()
    }

    override fun getItemCount(): Int {
        return songsList.size
    }
}