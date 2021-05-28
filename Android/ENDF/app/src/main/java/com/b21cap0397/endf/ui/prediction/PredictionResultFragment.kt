package com.b21cap0397.endf.ui.prediction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

        val tvDateResultValue: TextView = view.findViewById(R.id.tv_date_result_value)
        val tvGeocodeResultValue: TextView = view.findViewById(R.id.tv_geocode_result_value)

        val bundle = arguments
        if (bundle != null) {
            tvDateResultValue.text = bundle.getString(EXTRA_DATE)
            tvGeocodeResultValue.text = bundle.getString(EXTRA_GEOCODE)
        }

    }
}