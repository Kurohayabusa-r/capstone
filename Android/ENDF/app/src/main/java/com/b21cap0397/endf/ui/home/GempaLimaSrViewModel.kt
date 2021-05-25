package com.b21cap0397.endf.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.b21cap0397.endf.data.entities.GempaLimaSrEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class GempaLimaSrViewModel : ViewModel() {

    private val gempaList = MutableLiveData<ArrayList<GempaLimaSrEntity>>()

//    fun setGempaLimaSrDataFromApi() {
//        val gempaObjects = ArrayList<GempaLimaSrEntity>()
//
//        val url = "http://52.221.244.247:8000/api/bmkg/gempa/limasr"
//
//        val client = AsyncHttpClient()
//        client.get(url, object : AsyncHttpResponseHandler() {
//            override fun onSuccess(
//                statusCode: Int,
//                headers: Array<out Header>?,
//                responseBody: ByteArray
//            ) {
//                val jsonString = String(responseBody)
//                val jsonArray = JSONArray(jsonString)
//                for (i in 0 until jsonArray.length()) {
//                    val jsonObject = jsonArray.getJSONObject(i)
//                    val gempaLimaSr = GempaLimaSrEntity()
//
//                    gempaLimaSr.id = jsonObject.getString("id")
//                    gempaLimaSr.tanggal = jsonObject.getString("tanggal")
//                    gempaLimaSr.jam = jsonObject.getString("jam")
//                    gempaLimaSr.koordinat = jsonObject.getString("koordinat")
//                    gempaLimaSr.magnitude = jsonObject.getString("magnitude")
//                    gempaLimaSr.kedalaman = jsonObject.getString("kedalaman")
//                    gempaLimaSr.wilayah = jsonObject.getString("wilayah")
//                    gempaLimaSr.potensi = jsonObject.getString("potensi")
//
//                    gempaObjects.add(gempaLimaSr)
//                }
//                gempaList.postValue(gempaObjects)
//            }
//
//            override fun onFailure(
//                statusCode: Int,
//                headers: Array<out Header>?,
//                responseBody: ByteArray?,
//                error: Throwable?
//            ) {
//
//            }
//
//        })
//    }

    fun setGempaLimaSrDataFromFirebase() {
        val gempaObjects = ArrayList<GempaLimaSrEntity>()
        val db = Firebase.firestore
        db.collection("earthquakes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("FB OK", "${document.id} => ${document.data}")

                    val gempaLimaSr = GempaLimaSrEntity()
                    gempaLimaSr.tanggal = document.data["occurence_time"].toString()
                    gempaLimaSr.koordinat =
                        "${document.data["latitude"].toString()},${document.data["longitude"]}"
                    gempaLimaSr.magnitude = document.data["magnitude"].toString()
                    gempaLimaSr.kedalaman = document.data["depth"].toString()
                    gempaLimaSr.wilayah = document.data["region"].toString()

                    gempaObjects.add(gempaLimaSr)
                }

                gempaList.postValue(gempaObjects)
            }
            .addOnFailureListener { exception ->
                Log.d("FB ERR", "Error getting documents: ", exception)
            }
    }


    fun getGempaLimaSrData(): LiveData<ArrayList<GempaLimaSrEntity>> = gempaList

}