package com.b21cap0397.endf.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.b21cap0397.endf.MainActivity
import com.b21cap0397.endf.data.entities.NewsEntity
import com.b21cap0397.endf.databinding.BaseNewsRowBinding
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val listNews = ArrayList<NewsEntity>()

    fun setNewsData(newsList: ArrayList<NewsEntity>) {
        this.listNews.clear()
        this.listNews.addAll(newsList)
        notifyDataSetChanged()
    }


    class NewsViewHolder(private val binding: BaseNewsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsEntity) {
            with(binding) {

                Glide.with(itemView.context)
                    .load(news.image)
                    .into(ivNewsImage)
                tvNewsTitle.text = news.title
                tvNewsAuthor.text = news.author
                tvNewsPublished.text = news.published

                itemView.setOnClickListener {
                    val fragmentManager =
                        (itemView.context as MainActivity).supportFragmentManager
                    val wvFragment = WebViewFragment()
                    val bundle = Bundle()
                    bundle.putString(WebViewFragment.URL, news.link)
                    wvFragment.arguments = bundle
                    wvFragment.show(fragmentManager, WebViewFragment::class.java.simpleName)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val baseNewsRowBinding =
            BaseNewsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(baseNewsRowBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val report = listNews[position]
        holder.bind(report)
    }

    override fun getItemCount(): Int {
        return listNews.size
    }
}