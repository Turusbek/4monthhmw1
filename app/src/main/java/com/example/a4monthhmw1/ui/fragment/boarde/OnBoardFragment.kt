package com.example.a4monthhmw1.ui.fragment.boarde

import com.example.a4monthhmw1.base.BaseFragment
import com.example.a4monthhmw1.databinding.FragmentOnBoardBinding
import com.example.a4monthhmw1.ui.App

class OnBoardFragment : BaseFragment<FragmentOnBoardBinding>(FragmentOnBoardBinding::inflate),
    BoardAdapter.StartListener {
    private lateinit var adapter: BoardAdapter

    override fun setupUI() {
        adapter = BoardAdapter(this)
        binding.onBoardPager.adapter = adapter

    }

    override fun start() {
        App.prefs.saveBoardState()
        controller.navigateUp()
    }
}