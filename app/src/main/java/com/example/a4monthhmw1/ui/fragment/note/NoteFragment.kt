package com.example.a4monthhmw1.ui.fragment.note

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.DropBoxManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4monthhmw1.R
import com.example.a4monthhmw1.base.BaseFragment
import com.example.a4monthhmw1.databinding.FragmentNoteBinding
import com.example.a4monthhmw1.model.NoteModel
import com.example.a4monthhmw1.ui.App

class NoteFragment :
    BaseFragment<FragmentNoteBinding>(FragmentNoteBinding::inflate),NoteAdapter.NoteClicInterfase {
    private lateinit var adapter: NoteAdapter
    private lateinit var layoutManager: LinearLayoutManager
    override fun setupUI() {
        adapter = NoteAdapter(this)
        binding.rvNote.adapter = adapter
        adapter.addNote(App.db.getDao().getAllNote()as ArrayList<NoteModel>)
    }
    override fun setupObserver() {
        super.setupObserver()
        deleteNote()
        binding.btnAdd.setOnClickListener {
            controller.navigate(R.id.addNoteFragment)
        }
        binding.btnSort.setOnClickListener {
            showSortDialog()
        }
        layoutManager = LinearLayoutManager(requireContext())
        binding.rvNote.layoutManager = layoutManager

        binding.imgNote.setOnClickListener {
           setListLayoutManager()
        }
    }

    private fun setListLayoutManager() {
        if (binding.rvNote.layoutManager == layoutManager) {
            binding.rvNote.layoutManager = GridLayoutManager(requireActivity(), 2)
        } else {
            binding.rvNote.layoutManager = layoutManager
        }
    }


    private fun showSortDialog() {
        val sortOptions = arrayOf("Sort(ABC)","Newest","Oldest")
        val mBuilder = AlertDialog.Builder(requireContext())
        mBuilder.setTitle("Sort by")
        mBuilder.setIcon(R.drawable.ic_baseline_sort_24)
        mBuilder.setSingleChoiceItems(sortOptions,-1){
            dialogInterface, i ->
            if (i==0){

            }
            if (i==1){

            }
            if (i==3){

            }
            dialogInterface.dismiss()
        }.show()

    }

    private fun deleteNote() {
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper
                    .RIGHT or ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Удалить заметку?")
                    .setNegativeButton("Нет") { _: DialogInterface?, _: Int ->
                        adapter.notifyItemChanged(viewHolder.adapterPosition)
                    }
                    .setPositiveButton("Да") { _: DialogInterface?, _: Int ->
                        adapter.deleteNote(viewHolder.adapterPosition)
                        adapter.notifyItemChanged(viewHolder.adapterPosition)
                    }
                    .show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvNote)
    }

    override fun noteClik(model: NoteModel) {
        val bundle = Bundle()
        bundle.putString("title",model.title)
        bundle.putString("desc",model.description)
        model.id?.let { bundle.putInt("id",it) }
        controller.navigate(R.id.addNoteFragment,bundle)
    }

}



