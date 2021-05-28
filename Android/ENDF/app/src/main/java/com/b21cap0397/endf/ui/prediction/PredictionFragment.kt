package com.b21cap0397.endf.ui.prediction

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.b21cap0397.endf.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class PredictionFragment : Fragment(), OnMapReadyCallback {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_prediction, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var geocode: String? = null
        var date: String? = null

        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.pred_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        val tvDateValue: TextView = view.findViewById(R.id.tv_date_value)
        tvDateValue.setOnClickListener {
            val cal = Calendar.getInstance()
            val mYear = cal.get(Calendar.YEAR)
            val mMonth = cal.get(Calendar.MONTH)
            val mDay = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                view.context,
                { _, year, month, dayOfMonth ->
                    date = "$year-${month+1}-$dayOfMonth"
                    tvDateValue.text = date
                }, mYear, mMonth, mDay)
            datePickerDialog.show()
        }

        val btnPredict: Button = view.findViewById(R.id.bt_predict)
        btnPredict.setOnClickListener {
            val predictionResultFragment = PredictionResultFragment()
            val bundle = Bundle()
            bundle.putString(PredictionResultFragment.EXTRA_DATE, date)
            bundle.putString(PredictionResultFragment.EXTRA_GEOCODE, geocode)
            predictionResultFragment.arguments = bundle
            predictionResultFragment.show(childFragmentManager, "PREDICTION_RESULT")
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val coordinate = LatLng(-6.20906, 106.83539)
        googleMap.addMarker(
            MarkerOptions()
                .position(coordinate)
                .title("Test")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 6.0f))
    }
}
