package com.example.mint_chord_writer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mint_chord_writer.databinding.MainSongsListBinding

class SongsViewHolder(v: MainSongsListBinding) : RecyclerView.ViewHolder(v.root) {
    val songName = v.songName
}

class SongsAdapter(var songs: ArrayList<Song>) : RecyclerView.Adapter<SongsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        return SongsViewHolder(MainSongsListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.songName.text = songs[position].name
        holder.itemView.setOnClickListener{view ->
            val intent = Intent(view.context, EditActivity::class.java)
            intent.putExtra("selectedSong", songs[position])
            view.context.startActivity(intent)
        }
    }
}
