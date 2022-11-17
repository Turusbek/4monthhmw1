package com.example.a4monthhmw1.ui.fragment.note

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a4monthhmw1.databinding.ItemNoteBinding
import com.example.a4monthhmw1.model.NoteModel
import com.example.a4monthhmw1.ui.App

class NoteAdapter(private val noteClikInterfase: NoteClicInterfase) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var list: ArrayList<NoteModel> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addNote(list: ArrayList<NoteModel>) {
        this.list = list
        notifyDataSetChanged()
    }


    fun deleteNote(position: Int) {
        App.db.getDao().deleteNote(list.removeAt(position))
        notifyItemChanged(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoteViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.itemView.setOnClickListener {
            noteClikInterfase.noteClik(list[position])
        }
    }

    override fun getItemCount(): Int = list.size
    

    class NoteViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(model: NoteModel) {
            binding.itemTvTitle.text = model.title
            binding.itemTvDes.text = model.description
        }
    }

    interface NoteClicInterfase {
        fun noteClik(model: NoteModel)
    }
}
