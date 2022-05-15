package com.minthana.mint_chord_writer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_edit.view.*


class SongAdapter(private var songs: MutableList<Song>) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_main, parent, false))
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = songs[position]
        holder.itemView.apply{
            songTitle.text = curSong.name

        }
    }
}