package com.b21cap0397.endf.ui.earthquake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.b21cap0397.endf.R
import com.b21cap0397.endf.data.entities.EarthquakeFiveEntity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EarthquakeDetailFragment : BottomSheetDialogFragment(), OnMapReadyCallback {

    companion object {
        const val EXTRA_EQ = "extra_eq"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_eq_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val bundle = arguments
        if (bundle != null) {
            val eq: EarthquakeFiveEntity? = bundle.getParcelable(EXTRA_EQ)
            val coordinate = LatLng(eq?.latitude!!.toDouble(), eq.longitude.toDouble())
            googleMap.addMarker(
                MarkerOptions()
                    .position(coordinate)
                    .title(eq.wilayah)
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 6.0f))

        }
    }
}


