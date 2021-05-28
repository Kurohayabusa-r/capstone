package com.b21cap0397.endf.ui.prediction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.b21cap0397.endf.R
import com.b21cap0397.endf.data.entities.GempaLimaSrEntity
import com.b21cap0397.endf.ui.home.EqDetailFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PredictionFragment : Fragment(), OnMapReadyCallback {

//    private lateinit var predictionViewModel: PredictionViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_prediction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.pred_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        val tvDown: TextView = view.findViewById(R.id.tv_down)
        tvDown.text = "DOWN"

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val coordinate = LatLng(-6.20906,106.83539)
        googleMap.addMarker(
            MarkerOptions()
                .position(coordinate)
                .title("Test")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 6.0f))
    }

}
