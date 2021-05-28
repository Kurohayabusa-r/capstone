package com.b21cap0397.endf.ui.prediction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.b21cap0397.endf.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PredictionResultFragment : BottomSheetDialogFragment() {

    companion object {
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_GEOCODE = "extra_geocode"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_prediction_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[PredictionViewModel::class.java]


        val tvDateResultValue: TextView = view.findViewById(R.id.tv_date_result_value)
        val tvGeocodeResultValue: TextView = view.findViewById(R.id.tv_geocode_result_value)
        val tvMagnitudeResultValue: TextView = view.findViewById(R.id.tv_prediction_magnitude_value)

        val bundle = arguments
        if (bundle != null) {
            val fullDate = bundle.getString(EXTRA_DATE)
            val fullDateSplitted = fullDate?.split("-")?.map { item -> item.toInt() }
            val geoCode = bundle.getString(EXTRA_GEOCODE)
            val geoCodeSplitted = geoCode?.split(",")?.map { item -> item.toDouble() }
            val params: List<Number> =
                listOf(
                    geoCodeSplitted!![0],
                    geoCodeSplitted[1],
                    fullDateSplitted!![1],
                    fullDateSplitted[2],
                    fullDateSplitted[0]
                )

            viewModel.setMagnitudePrediction(params)
            tvDateResultValue.text = fullDate
            tvGeocodeResultValue.text = geoCode
        }

        viewModel.magnitudePrediction.observe(viewLifecycleOwner, Observer {
            tvMagnitudeResultValue.text = it
        })
    }
}