package com.example.mint_chord_writer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mint_chord_writer.databinding.ChordListBinding

class ChordCard(var name:String)

class ChordsViewHolder(v: ChordListBinding) : RecyclerView.ViewHolder(v.root) {
    val button = v.chordButton
}

class ChordsAdapter(var ChordCardList:ArrayList<ChordCard>, val context: Context) : RecyclerView.Adapter<ChordsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChordsViewHolder {
        val cellForRow = ChordListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ChordsViewHolder(cellForRow)
    }

    override fun getItemCount() = ChordCardList.size

    override fun onBindViewHolder(holder: ChordsViewHolder, position: Int) {
        if(ChordCardList[position].name.length > 0) {
            holder.button.text = ChordCardList[position].name
        } else {
            holder.button.text = (position+1).toString()
        }

        if ((context as EditActivity).currentChordIndex == position) {
            holder.button.setBackgroundColor(ContextCompat.getColor(context, R.color.mint))
        } else {
            holder.button.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
        }

        if (context.cutIndex == position) {
            holder.button.alpha = 0.5F
        } else {
            holder.button.alpha = 1F
        }

        holder.button.setOnClickListener{
            context.moveToChord(position)
        }
    }

    fun updateList(list: ArrayList<ChordCard>) {
        ChordCardList = list
        this.notifyDataSetChanged()
    }
}