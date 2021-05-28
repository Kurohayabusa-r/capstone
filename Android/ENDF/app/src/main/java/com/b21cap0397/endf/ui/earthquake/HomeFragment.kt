package com.b21cap0397.endf.ui.earthquake

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

    private lateinit var rvHome: RecyclerView
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_eartquake, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecyclerView()

        val homeViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[GempaLimaSrViewModel::class.java]

//        homeViewModel.setGempaLimaSrDataFromApi()
        homeViewModel.setGempaLimaSrDataFromFirebase()
        homeViewModel.getGempaLimaSrData().observe(
            viewLifecycleOwner, {
                if (it != null) {
                    homeAdapter.setGempaData(it)
                }
            }
        )
    }

    private fun showRecyclerView() {
        rvHome = view?.findViewById(R.id.rv_home)!!
        rvHome.setHasFixedSize(true)
        rvHome.layoutManager = LinearLayoutManager(context)
        homeAdapter = HomeAdapter()
        rvHome.adapter = homeAdapter
    }
}