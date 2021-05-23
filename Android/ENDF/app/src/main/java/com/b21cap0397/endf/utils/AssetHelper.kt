package com.b21cap0397.endf.utils

import android.app.Application
import android.content.Context
import com.b21cap0397.endf.data.entities.ReportEntity
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

object AssetHelper {
    lateinit var application: Application

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getReports(): ArrayList<ReportEntity> {
        val results = ArrayList<ReportEntity>()
        val fileName = "dummy_report.json"
        val jsArray = JSONArray(getJsonDataFromAsset(application, fileName))

        for (i in 0 until jsArray.length()) {
            val jsonObject = jsArray.getJSONObject(i)
            val report = jsonToDataClass(jsonObject)
            results.add(report)
        }
        return results
    }

    private fun jsonToDataClass(jsonObject: JSONObject): ReportEntity {
        val report = ReportEntity()
        report.title = jsonObject.getString("title")
        report.user = jsonObject.getString("user")
        report.location = jsonObject.getString("location")
        report.description = jsonObject.getString("description")
        return report
    }
}