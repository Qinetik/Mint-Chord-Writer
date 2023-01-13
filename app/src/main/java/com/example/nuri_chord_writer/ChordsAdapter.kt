package com.example.mint_chord_writer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.chord_list.view.*

class ChordCard(var name:String)

class ChordsViewHolder(v : View) : RecyclerView.ViewHolder(v) {
    val name = v.chordText
}

class ChordsAdapter(var ChordCardList:ArrayList<ChordCard>, val context: Context) : RecyclerView.Adapter<ChordsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChordsViewHolder {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.chord_list, parent, false)
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
            (context as EditActivity).moveToChord(position)
        }
    }

    fun updateList(list: ArrayList<ChordCard>) {
        ChordCardList = list
        this.notifyDataSetChanged()
    }
}