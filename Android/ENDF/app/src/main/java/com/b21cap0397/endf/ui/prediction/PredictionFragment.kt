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
import com.b21cap0397.endf.R
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*

class PredictionFragment : Fragment() {

    var geocode: String? = null
    var date: String? = null

    private lateinit var tvLocationValue: TextView
    private lateinit var tvDateValue: TextView

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

        val apiKey = getString(R.string.api_key)
        context?.let { Places.initialize(it, apiKey) }

        tvLocationValue.setOnClickListener {
            val fields = listOf(Place.Field.LAT_LNG, Place.Field.NAME)
            val intent = context?.let { appContext ->
                Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .setCountry("ID")
                    .build(appContext)
            }
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

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

        val btnPredict: Button = view.findViewById(R.id.bt_predict)
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
                } catch (e: Exception) {
                    tvLocationValue.text = getString(R.string.location_value)
                    geocode = null
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
}

