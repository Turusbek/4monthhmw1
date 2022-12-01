package com.example.a4monthhmw1.ui.fragment.boarde

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a4monthhmw1.R
import com.example.a4monthhmw1.databinding.BoardItemBinding

class BoardAdapter(private val listener: StartListener) :
    RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {
    val titleList = listOf("Заметки", "Контакты", "Конец")
    val detList = listOf("Добавить", "Доступ", "Конец")
    val list = listOf(R.raw.work_flow_json, R.raw.note_json, R.raw.notes_document)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val binding =
            BoardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.onBind(position)
        holder.binding.btnBoardStart.setOnClickListener {
            listener.start()
        }
    }


    override fun getItemCount(): Int = titleList.size
    inner class BoardViewHolder(val binding: BoardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            binding.imBoardItem.setAnimation(list[position])
            binding.tvBoardTitle.text = titleList[position]
            binding.tvBoardDes.text = detList[position]
            if (position == 2) {
                binding.btnBoardStart.visibility = View.VISIBLE
            } else {
                binding.btnBoardStart.visibility = View.GONE
            }
        }

    }

    interface StartListener {
        fun start()
    }

}