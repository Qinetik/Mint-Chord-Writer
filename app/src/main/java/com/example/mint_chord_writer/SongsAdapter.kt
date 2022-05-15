package com.minthana.mint_chord_writer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_songs_list.view.*

class SongsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val songName = itemView.songName
}

class SongsAdapter(var songs: ArrayList<Song>) : RecyclerView.Adapter<SongsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        return SongsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_songs_list, parent, false))
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.songName.text = songs[position].name
        holder.itemView.setOnClickListener{view ->
            val intent = Intent(view.context, EditActivity::class.java)
            view.context.startActivity(intent)
        }
    }
}
