package com.b21cap0397.endf.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.b21cap0397.endf.data.entities.NewsEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewsViewModel : ViewModel() {

    private val newsList = MutableLiveData<ArrayList<NewsEntity>>()

    fun setNewsFromFirebase() {
        val newsObjects = ArrayList<NewsEntity>()
        val db = Firebase.firestore
        db.collection("posts")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("FB OK", "${document.id} => ${document.data}")

                    val news = NewsEntity()
                    news.image = document.data["image"].toString()
                    news.title = document.data["title"].toString()
                    news.author = document.data["author"].toString()
                    news.link = document.data["link"].toString()
                    news.published = document.data["published"].toString()

                    newsObjects.add(news)
                }

                newsList.postValue(newsObjects)
            }
            .addOnFailureListener { exception ->
                Log.d("FB ERR", "Error getting documents: ", exception)
            }
    }


    fun getNewsData(): LiveData<ArrayList<NewsEntity>> = newsList
}