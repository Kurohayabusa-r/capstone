package com.b21cap0397.endf.ui.prediction

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.b21cap0397.endf.BuildConfig
import com.b21cap0397.endf.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.switchmaterial.SwitchMaterial
import java.util.*

class PredictionFragment : Fragment(), OnMapReadyCallback {

    var geocode: String? = null
    var date: String? = null

    private lateinit var tvLocationValue: TextView
    private lateinit var tvDateValue: TextView
    private lateinit var swMap: SwitchMaterial
    private lateinit var btnPredict: Button
    private lateinit var mapFragment: SupportMapFragment

    private val AUTOCOMPLETE_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_prediction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvLocationValue = view.findViewById(R.id.tv_location_value)
        tvDateValue = view.findViewById(R.id.tv_date_value)
        swMap = view.findViewById(R.id.sw_map)
        mapFragment = (childFragmentManager.findFragmentById(R.id.google_map) as? SupportMapFragment)!!
        btnPredict = view.findViewById(R.id.bt_predict)

        val apiKey = BuildConfig.MAPS_API_KEY
        context?.let { Places.initialize(it, apiKey) }

        tvDateValue.setOnClickListener {
            val cal = Calendar.getInstance()
            val mYear = cal.get(Calendar.YEAR)
            val mMonth = cal.get(Calendar.MONTH)
            val mDay = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                view.context,
                { _, year, month, dayOfMonth ->
                    date = "$year-${month + 1}-$dayOfMonth"
                    tvDateValue.text = date
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

        tvLocationValue.setOnClickListener {
            val fields = listOf(Place.Field.LAT_LNG, Place.Field.NAME)
            val intent = context?.let { appContext ->
                Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .setCountry("ID")
                    .build(appContext)
            }
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

        swMap.setOnCheckedChangeListener { _, isChecked ->
            val gMap: FragmentContainerView = view.findViewById(R.id.google_map)
            when (isChecked) {
                true -> {
                    mapFragment.getMapAsync(this)
                    gMap.visibility = View.VISIBLE
                }
                else -> {
                    gMap.visibility = View.GONE
                }
            }

        }

        btnPredict.setOnClickListener {
            if (geocode == null || date == null) {
                val toast = Toast.makeText(
                    view.context,
                    getString(R.string.empty_predict_message),
                    Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                val predictionResultFragment = PredictionResultFragment()
                val bundle = Bundle()
                bundle.putString(PredictionResultFragment.EXTRA_DATE, date)
                bundle.putString(PredictionResultFragment.EXTRA_GEOCODE, geocode)
                predictionResultFragment.arguments = bundle
                predictionResultFragment.show(childFragmentManager, "PREDICTION_RESULT")
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (data != null) {
                try {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    tvLocationValue.text = place.name.toString()
                    geocode = formatGeocode(place.latLng.toString())
                    mapFragment.getMapAsync(this)
                } catch (e: Exception) {
                    tvLocationValue.text = getString(R.string.location_value)
                    geocode = null
                    mapFragment.getMapAsync(this)
                }
            }


        }
    }

    private fun formatGeocode(latLong: String): String {
        var result = ""
        val pattern = Regex("""\(([^]]+)\)""")
        val match = pattern.find(latLong)
        if (match != null) {
            result = match.groupValues[1]
        }
        return result
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.clear()
        val gc = geocode?.split(",")
        val lat = gc?.get(0)?.toDouble()
        val long = gc?.get(1)?.toDouble()
        if (lat != null && long != null ) {
            val coordinate = LatLng(lat, long)
            googleMap.addMarker(
                MarkerOptions()
                    .position(coordinate)
                    .title("Test")
                    .draggable(true)
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 8.0f))
        } else {
            val defaultView = LatLng(-2.4833826, 117.8902853)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultView, 3.3f))
        }
    }
}

