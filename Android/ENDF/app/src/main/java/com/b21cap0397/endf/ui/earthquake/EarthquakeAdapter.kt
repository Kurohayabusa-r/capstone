package com.b21cap0397.endf.ui.earthquake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.b21cap0397.endf.R
import com.b21cap0397.endf.data.entities.EarthquakeFiveEntity
import com.b21cap0397.endf.databinding.BaseGempaRowBinding

class EarthquakeAdapter : RecyclerView.Adapter<EarthquakeAdapter.ReportViewHolder>() {

    private val listGempa = ArrayList<EarthquakeFiveEntity>()

    fun setGempaData(gempaList: ArrayList<EarthquakeFiveEntity>) {
        this.listGempa.clear()
        this.listGempa.addAll(gempaList)
        notifyDataSetChanged()
    }

    class ReportViewHolder(private val binding: BaseGempaRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gempa: EarthquakeFiveEntity) {
            with(binding) {
                tvGempaMagnitude.text = itemView.resources.getString(R.string.gempa_skala, gempa.magnitude)
                tvGempaLokasi.text = gempa.wilayah
                tvGempaTanggal.text =
                    itemView.resources.getString(R.string.gempa_waktu, gempa.tanggal, gempa.jam)
                tvGempaKedalaman.text = gempa.kedalaman

                itemView.setOnClickListener {
                    val fragmentManager = (itemView.context as AppCompatActivity).supportFragmentManager
                    val eqFragment = EarthquakeDetailFragment()
                    val bundle = Bundle()
                    bundle.putParcelable(EarthquakeDetailFragment.EXTRA_EQ, gempa)
                    eqFragment.arguments = bundle
                    eqFragment.show(fragmentManager, "EQ_DETAIL")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val baseGempaRowBinding =
            BaseGempaRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(baseGempaRowBinding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = listGempa[position]
        holder.bind(report)
    }

    override fun getItemCount(): Int {
        return listGempa.size
    }
}