package com.example.a4monthhmw1.ui.fragment.addNote

import com.example.a4monthhmw1.base.BaseFragment
import com.example.a4monthhmw1.databinding.FragmentAddNoteBinding
import com.example.a4monthhmw1.model.NoteModel
import com.example.a4monthhmw1.ui.App


class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(FragmentAddNoteBinding::inflate) {
    override fun setupUI() {
        if (arguments != null) {
            val title = arguments?.getString("title")
            val desc = arguments?.getString("desc")
            val id = arguments?.getInt("id")

            binding.etTitle.setText(title.toString())
            binding.etDes.setText(desc.toString())
            binding.btnSaveNote.setOnClickListener {
                App.db.getDao().updateNote(
                    NoteModel(
                        id, binding.etTitle.text.toString(), binding.etDes.text.toString()
                    )
                )
                controller.navigateUp()
            }
        } else {
            binding.btnSaveNote.setOnClickListener {
                val title = binding.etTitle.text.toString()
                val description = binding.etDes.text.toString()
                App.db.getDao()
                    .addNote(NoteModel(title = title, description = description))
                controller.navigateUp()

            }
        }
    }
}

