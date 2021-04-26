package com.pdamkotasmg.happywork.fitur.kehadiran.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pdamkotasmg.happywork.R
import com.pdamkotasmg.happywork.fitur.feeds.model.DataItem
import de.hdodenhof.circleimageview.CircleImageView

class KehadiranAdapter(var context: Context, private val dataItems: List<DataItem?>?) : RecyclerView.Adapter<KehadiranAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_kehadiran, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load("https://www.pdamkotasmg.co.id/packages/upload/photo/" + dataItems?.get(position)?.fav).into(holder.ciListKehadiranMasuk)
        Glide.with(context).load("https://www.pdamkotasmg.co.id/packages/upload/photo/" + dataItems?.get(position)?.fav).into(holder.ciListKehadiranKeluar)
        holder.tvListKehadiranDate.text = dataItems?.get(position)?.tglPublish
    }

    override fun getItemCount(): Int {
        return dataItems!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cvKlik: LinearLayout
        var ciListKehadiranMasuk: CircleImageView
        var tvListKehadiranMasuk: TextView
        var ciListKehadiranKeluar: CircleImageView
        var tvListKehadiranKeluar: TextView
        var tvListKehadiranDate: TextView

        init {
            cvKlik = itemView.findViewById(R.id.cv_klik)
            ciListKehadiranMasuk = itemView.findViewById(R.id.ci_list_kehadiran_masuk)
            tvListKehadiranMasuk = itemView.findViewById(R.id.tv_list_kehadiran_masuk)
            ciListKehadiranKeluar = itemView.findViewById(R.id.ci_list_kehadiran_keluar)
            tvListKehadiranKeluar = itemView.findViewById(R.id.tv_list_kehadiran_keluar)
            tvListKehadiranDate = itemView.findViewById(R.id.tv_list_kehadiran_date)
        }
    }
}