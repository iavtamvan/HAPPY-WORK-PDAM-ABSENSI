package com.pdamkotasmg.happywork.fitur.feeds.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pdamkotasmg.happywork.R
import com.pdamkotasmg.happywork.fitur.feeds.model.DataItem
import de.hdodenhof.circleimageview.CircleImageView

class FeedsAdapter(var context: Context, private val dataItems: List<DataItem?>?) : RecyclerView.Adapter<FeedsAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_feeds, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load("https://www.pdamkotasmg.co.id/packages/upload/photo/" + dataItems?.get(position)?.fav).into(holder.ciListFeedsProfileImage)
        holder.tvListFeedsJudulBerita?.text = dataItems?.get(position)?.title
        holder.tvListFeedsTanggalBerita?.text = dataItems?.get(position)?.tglPublish
        holder.tvListFeedsNamaPenerbit?.text = dataItems?.get(position)?.category
        holder.tvListFeedsIsiBerita?.text = dataItems?.get(position)?.desc
    }

    override fun getItemCount(): Int {
        return dataItems!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvKlik: CardView?
        val ciListFeedsProfileImage: CircleImageView
        val tvListFeedsJudulBerita: TextView?
        val tvListFeedsNamaPenerbit: TextView?
        val tvListFeedsTanggalBerita: TextView?
        val tvListFeedsIsiBerita: TextView?

        init {
            cvKlik = itemView.findViewById(R.id.cv_klik)
            ciListFeedsProfileImage = itemView.findViewById(R.id.ci_list_feeds_profile_image)
            tvListFeedsJudulBerita = itemView.findViewById(R.id.tv_list_feeds_judul_berita)
            tvListFeedsNamaPenerbit = itemView.findViewById(R.id.tv_list_feeds_nama_penerbit)
            tvListFeedsTanggalBerita = itemView.findViewById(R.id.tv_list_feeds_tanggal_berita)
            tvListFeedsIsiBerita = itemView.findViewById(R.id.tv_list_feeds_isi_berita)
        }
    }
}