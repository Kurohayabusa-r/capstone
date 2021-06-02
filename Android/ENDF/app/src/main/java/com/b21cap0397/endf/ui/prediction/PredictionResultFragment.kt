package com.b21cap0397.endf.ui.prediction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.b21cap0397.endf.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PredictionResultFragment : BottomSheetDialogFragment() {

    companion object {
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_GEOCODE = "extra_geocode"
    }

    private lateinit var params: List<Number>
    private lateinit var timeoutMessage: TextView

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
        )[PredictionResultViewModel::class.java]

        val tvMagnitudeResultValue: TextView = view.findViewById(R.id.tv_prediction_result_value)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val contentWrapper: ConstraintLayout = view.findViewById(R.id.constraint_wrapper)
        val btnTryAgain: Button = view.findViewById(R.id.btn_try_again)

        val bundle = arguments
        if (bundle != null) {
            val fullDate = bundle.getString(EXTRA_DATE)
            val fullDateSplitted = fullDate?.split("-")?.map { item -> item.toInt() }

            val geoCode = bundle.getString(EXTRA_GEOCODE)
            val geoCodeSplitted = geoCode?.split(",")?.map { item -> item.toDouble() }

            params =
                listOf(
                    geoCodeSplitted!![0],
                    geoCodeSplitted[1],
                    fullDateSplitted!![1],
                    fullDateSplitted[2],
                    fullDateSplitted[0]
                )

            viewModel.setPredictionResult(params)
        }

        viewModel.magnitudePrediction.observe(viewLifecycleOwner, {
            progressBar.visibility = View.GONE
            tvMagnitudeResultValue.text = it
            contentWrapper.visibility = View.VISIBLE
        })

        viewModel.isTimeout.observe(viewLifecycleOwner, { connectionError ->
            when (connectionError) {
                true -> {
                    timeoutMessage = view.findViewById(R.id.tv_timeout_message)
                    timeoutMessage.text = getString(R.string.connection_error)
                    progressBar.visibility = View.GONE
                    timeoutMessage.visibility = View.VISIBLE
                    btnTryAgain.visibility = View.VISIBLE
                }
            }
        })

        btnTryAgain.setOnClickListener {
            viewModel.setPredictionResult(params)
            progressBar.visibility = View.VISIBLE
            timeoutMessage.visibility = View.INVISIBLE
            btnTryAgain.visibility = View.INVISIBLE
        }
    }
}