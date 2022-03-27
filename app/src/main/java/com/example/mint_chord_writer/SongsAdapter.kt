package com.example.mint_chord_writer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_edit.view.*

class SongsAdapter(private var songs: MutableList<Song>) : RecyclerView.Adapter<SongsAdapter.SongsViewHolder>() {
    class SongsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        return SongsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_main, parent, false))
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val curSong = songs[position]
        holder.itemView.apply{
            //songTitle.text = curSong.name
        }
    }
}
