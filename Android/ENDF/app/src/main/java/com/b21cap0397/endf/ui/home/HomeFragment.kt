package com.b21cap0397.endf.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.b21cap0397.endf.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvHome: RecyclerView = view.findViewById(R.id.rv_home)

        if (activity != null) {
            val homeViewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[HomeViewModel::class.java]

            val reports = homeViewModel.getReports()
            val homeAdapter = HomeAdapter()
            homeAdapter.setReports(reports)

            rvHome.layoutManager = LinearLayoutManager(context)
            rvHome.adapter = homeAdapter
            rvHome.setHasFixedSize(true)
        }
    }
}