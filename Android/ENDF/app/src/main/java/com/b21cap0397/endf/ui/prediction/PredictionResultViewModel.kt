package com.b21cap0397.endf.ui.prediction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONArray
import org.json.JSONObject


class PredictionResultViewModel : ViewModel() {

    private val _magnitudePrediction = MutableLiveData<String>()
    private val _isTimeout = MutableLiveData<Boolean>()

    fun setPredictionResult(data: List<Number>) {

        val url = "http://34.101.64.239/predict"

        val jsonParams = JSONObject()
        jsonParams.put("instances", JSONArray(data))

        val params = StringEntity(jsonParams.toString())
        params.setContentType("application/json")

        val client = AsyncHttpClient()
        client.setMaxRetriesAndTimeout(200, 10)
        client.connectTimeout = 2000
        client.responseTimeout = 5000

        client.setURLEncodingEnabled(true)
        client.post(null, url, params, "application/json", object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val jsonString = String(responseBody)
                val jsonObject = JSONObject(jsonString)
                val result = jsonObject.getJSONArray("predictions").getJSONArray(0)[0]
                _magnitudePrediction.value = String.format("%.2f", result.toString().toDouble())
                _isTimeout.value = false
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                if (error != null) {
                    println("[+] Status code: $statusCode, \nerror: ${error.message}")
                    _isTimeout.value = true
                }
            }
        })
    }

    val magnitudePrediction: LiveData<String> = _magnitudePrediction
    val isTimeout: LiveData<Boolean> = _isTimeout
}
