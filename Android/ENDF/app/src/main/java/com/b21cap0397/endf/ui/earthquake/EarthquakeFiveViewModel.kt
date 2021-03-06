package com.b21cap0397.endf.ui.earthquake

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.b21cap0397.endf.data.entities.EarthquakeFiveEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class EarthquakeFiveViewModel : ViewModel() {

    private val gempaList = MutableLiveData<ArrayList<EarthquakeFiveEntity>>()

    fun setEarthquakeListLoopJ() {
        val gempaObjects = ArrayList<EarthquakeFiveEntity>()

        val url = "http://52.221.244.247:8000/api/bmkg/gempa/limasr"

        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val jsonString = String(responseBody)
                val jsonArray = JSONArray(jsonString)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val gempaLimaSr = EarthquakeFiveEntity()

                    gempaLimaSr.id = jsonObject.getString("id")
                    gempaLimaSr.tanggal = jsonObject.getString("tanggal")
                    gempaLimaSr.jam = jsonObject.getString("jam")
                    gempaLimaSr.magnitude = jsonObject.getString("magnitude")
                    gempaLimaSr.kedalaman = jsonObject.getString("kedalaman")
                    gempaLimaSr.wilayah = jsonObject.getString("wilayah")

                    val koordinat = jsonObject.getString("koordinat").split(",")
                    gempaLimaSr.latitude = koordinat[0]
                    gempaLimaSr.longitude = koordinat[1]

                    gempaObjects.add(gempaLimaSr)
                }
                gempaList.postValue(gempaObjects)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {

            }

        })
    }

    fun setEarthquakeListFirebase() {
        val gempaObjects = ArrayList<EarthquakeFiveEntity>()
        val db = Firebase.firestore
        db.collection("earthquakes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("FB OK", "${document.id} => ${document.data}")

                    val gempaLimaSr = EarthquakeFiveEntity()
                    gempaLimaSr.tanggal = document.data["occurence_time"].toString()
                    gempaLimaSr.latitude = document.data["latitude"].toString()
                    gempaLimaSr.longitude = document.data["longitude"].toString()
                    gempaLimaSr.magnitude = document.data["magnitude"].toString()
                    gempaLimaSr.kedalaman = document.data["depth"].toString()
                    gempaLimaSr.wilayah = document.data["region"].toString()
                    gempaLimaSr.timestamp = document.data["time"].toString()

                    gempaObjects.add(gempaLimaSr)
                }
                val sortedEqList = gempaObjects.sortedByDescending { it.tanggal }
                gempaList.postValue(ArrayList(sortedEqList.toList()))
            }
            .addOnFailureListener { exception ->
                Log.d("FB ERR", "Error getting documents: ", exception)
            }
    }


    fun getEarthquakeList(): LiveData<ArrayList<EarthquakeFiveEntity>> = gempaList
}
