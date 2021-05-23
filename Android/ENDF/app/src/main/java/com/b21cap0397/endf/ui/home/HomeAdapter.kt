package com.b21cap0397.endf.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.b21cap0397.endf.R
import com.b21cap0397.endf.data.entities.ReportEntity
import com.b21cap0397.endf.databinding.BaseRvRowBinding
import com.bumptech.glide.Glide

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ReportViewHolder>() {

    private val listReports = ArrayList<ReportEntity>()

    fun setReports(reports: List<ReportEntity>) {
        this.listReports.clear()
        this.listReports.addAll(reports)
    }

    class ReportViewHolder(private val binding: BaseRvRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(report: ReportEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(R.drawable.ic_default_report)
                    .into(binding.ivUserAvatar)

                tvReportTitle.text = report.title
                tvReportLocation.text = report.location
                tvReportBy.text = report.user
                tvReportDescription.text = report.description

                itemView.setOnClickListener { }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val baseRvRowBinding =
            BaseRvRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(baseRvRowBinding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = listReports[position]
        holder.bind(report)
    }

    override fun getItemCount(): Int {
        return listReports.size
    }
}