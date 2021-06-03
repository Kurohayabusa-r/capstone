package com.b21cap0397.endf.ui.earthquake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.b21cap0397.endf.R
import com.b21cap0397.endf.data.entities.EarthquakeFiveEntity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.switchmaterial.SwitchMaterial

class EartquakeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var earthquakeAdapter: EarthquakeAdapter
    private lateinit var swMapMode: SwitchMaterial
    private lateinit var rvHome: RecyclerView
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var gMap: FragmentContainerView

    private lateinit var listGeocode: ArrayList<String>
    private lateinit var listEqs: ArrayList<EarthquakeFiveEntity>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_eartquake, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swMapMode = view.findViewById(R.id.sw_eq)
        mapFragment =
            (childFragmentManager.findFragmentById(R.id.eq_all_map) as? SupportMapFragment)!!
        gMap = view.findViewById(R.id.eq_all_map)

        val progressbar: ProgressBar = view.findViewById(R.id.progress_bar)

        val homeViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[EarthquakeFiveViewModel::class.java]

        showRecyclerView()

        homeViewModel.setEarthquakeListFirebase()

        homeViewModel.getEarthquakeList().observe(
            viewLifecycleOwner, {
                if (it != null) {
                    progressbar.visibility = View.GONE
                    earthquakeAdapter.setGempaData(it)
                    listEqs = ArrayList()
                    listEqs.addAll(it)
                }
            }
        )

        swMapMode.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    mapFragment.getMapAsync(this)
                    gMap.visibility = View.VISIBLE
                    rvHome.visibility = View.GONE
                }
                else -> {
                    gMap.visibility = View.GONE
                    rvHome.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun showRecyclerView() {
        rvHome = view?.findViewById(R.id.rv_home)!!
        rvHome.setHasFixedSize(true)
        rvHome.layoutManager = LinearLayoutManager(context)
        earthquakeAdapter = EarthquakeAdapter()
        rvHome.adapter = earthquakeAdapter
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val defaultView = LatLng(-2.4833826, 117.8902853)
        googleMap.clear()
        listEqs.forEach { eq ->
            val eqLocation = eq.wilayah
            val eqGeocode = LatLng(eq.latitude.toDouble(), eq.longitude.toDouble())
            googleMap.addMarker(
                MarkerOptions()
                    .position(eqGeocode)
                    .title(eqLocation)
                    .draggable(false)
            )
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultView, 3.3f))
    }

}