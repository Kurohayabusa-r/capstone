package com.b21cap0397.endf.ui.earthquake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.b21cap0397.endf.R

class EartquakeFragment : Fragment() {

    private lateinit var rvHome: RecyclerView
    private lateinit var earthquakeAdapter: EarthquakeAdapter

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
        )[EarthquakeFiveViewModel::class.java]

        homeViewModel.setEarthquakeListLoopJ()
//        homeViewModel.setEarthquakeListFirebase()
        homeViewModel.getEarthquakeList().observe(
            viewLifecycleOwner, {
                if (it != null) {
                    earthquakeAdapter.setGempaData(it)
                }
            }
        )
    }

    private fun showRecyclerView() {
        rvHome = view?.findViewById(R.id.rv_home)!!
        rvHome.setHasFixedSize(true)
        rvHome.layoutManager = LinearLayoutManager(context)
        earthquakeAdapter = EarthquakeAdapter()
        rvHome.adapter = earthquakeAdapter
    }

}