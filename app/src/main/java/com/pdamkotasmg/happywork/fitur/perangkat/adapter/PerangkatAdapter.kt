package com.pdamkotasmg.happywork.fitur.perangkat.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pdamkotasmg.happywork.R
import com.pdamkotasmg.happywork.fitur.perangkat.model.DataItem
import com.pdamkotasmg.happywork.utils.Config

class PerangkatAdapter(var context: Context?, private val dataItems: List<DataItem?>?) : RecyclerView.Adapter<PerangkatAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_riwayat_perangkat, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvListPerangkatVersionAplication?.text = Config.APLICATION_NAME + " v." + dataItems?.get(position)?.appVersion

        holder.tvListPerangkatNameNamaHp?.text = dataItems?.get(position)?.buildBrand?.toUpperCase() + " , Android (" + dataItems?.get(position)?.osVersion?.toUpperCase() + ")"
        holder.tvListPerangkatIpCity?.text = dataItems?.get(position)?.ipAddress + " - " + dataItems?.get(position)?.locationCity
        holder.tvListPerangkatStatusTgl?.text = dataItems?.get(position)?.createdAt
    }

    override fun getItemCount(): Int {
        return dataItems!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvListPerangkatVersionAplication: TextView?
        val tvListPerangkatNameNamaHp: TextView?
        val tvListPerangkatIpCity: TextView?
        val tvListPerangkatStatusTgl: TextView?

        init {
            tvListPerangkatVersionAplication = itemView.findViewById(R.id.tv_list_perangkat_version_aplication)
            tvListPerangkatNameNamaHp = itemView.findViewById(R.id.tv_list_perangkat_name_nama_hp)
            tvListPerangkatIpCity = itemView.findViewById(R.id.tv_list_perangkat_ip_city)
            tvListPerangkatStatusTgl = itemView.findViewById(R.id.tv_list_perangkat_status_tgl)
        }
    }
}