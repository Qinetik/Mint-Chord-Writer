package com.example.mint_chord_writer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mint_chord_writer.databinding.ChordListBinding

class ChordCard(var name:String)

class ChordsViewHolder(v: ChordListBinding) : RecyclerView.ViewHolder(v.root) {
    val name = v.chordText
}

class ChordsAdapter(var ChordCardList:ArrayList<ChordCard>, val context: Context) : RecyclerView.Adapter<ChordsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChordsViewHolder {
        val cellForRow = ChordListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ChordsViewHolder(cellForRow)
    }

    override fun getItemCount() = ChordCardList.size

    override fun onBindViewHolder(holder: ChordsViewHolder, position: Int) {
        if(ChordCardList[position].name.length > 0) {
            holder.name.text = ChordCardList[position].name
        } else {
            holder.name.text = (position+1).toString()
        }

        if ((context as EditActivity).currentChordIndex == position) {
            holder.name.text = "*"+holder.name.text
        }

        holder.itemView.setOnClickListener{
            context.moveToChord(position)
        }
    }

    fun updateList(list: ArrayList<ChordCard>) {
        ChordCardList = list
        this.notifyDataSetChanged()
    }
}