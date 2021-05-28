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

class PredictionViewModel : ViewModel() {

    private val _magnitudePrediction = MutableLiveData<String>()

    fun setMagnitudePrediction(data: List<Number>) {

        val url = "http://35.226.247.153/predict"
        val jsonParams = JSONObject()
        jsonParams.put("instances", JSONArray(data))
        val params = StringEntity(jsonParams.toString())
        params.setContentType("application/json")
        val client = AsyncHttpClient()
        client.setTimeout(10000)
        client.post(null, url, params, "application/json", object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val jsonString = String(responseBody)
                val jsonObject = JSONObject(jsonString)
                val result = jsonObject.getJSONArray("predictions").getJSONArray(0)[0]
                _magnitudePrediction.value = result.toString()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                if (error != null) {
                    println("[+] Status code: $statusCode, \nerror: ${error.message}")
                }
            }
        })
    }

    val magnitudePrediction: LiveData<String> = _magnitudePrediction
}