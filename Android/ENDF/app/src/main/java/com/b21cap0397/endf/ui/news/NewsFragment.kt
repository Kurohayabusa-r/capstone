package com.b21cap0397.endf.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.b21cap0397.endf.R

class NewsFragment : Fragment() {

    private lateinit var rvNews: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecyclerView()

        val newsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[NewsViewModel::class.java]

        newsViewModel.setNewsFromFirebase()
        newsViewModel.getNewsData().observe(
            viewLifecycleOwner, {
                if (it != null) {
                    newsAdapter.setNewsData(it)
                }
            }
        )
    }

    private fun showRecyclerView() {
        rvNews = view?.findViewById(R.id.rv_news)!!
        rvNews.setHasFixedSize(true)
        rvNews.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter()
        rvNews.adapter = newsAdapter
    }
}