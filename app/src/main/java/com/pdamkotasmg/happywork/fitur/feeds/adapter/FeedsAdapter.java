package com.pdamkotasmg.happywork.fitur.feeds.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.feeds.model.DataItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;
    public FeedsAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_feeds, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load("https://www.pdamkotasmg.co.id/packages/upload/photo/" + dataItems.get(position).getFav()).into(holder.ciListFeedsProfileImage);
        holder.tvListFeedsJudulBerita.setText(dataItems.get(position).getTitle());
        holder.tvListFeedsTanggalBerita.setText(dataItems.get(position).getTglPublish());
        holder.tvListFeedsNamaPenerbit.setText(dataItems.get(position).getCategory());
        holder.tvListFeedsIsiBerita.setText(dataItems.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvKlik;
        private CircleImageView ciListFeedsProfileImage;
        private TextView tvListFeedsJudulBerita;
        private TextView tvListFeedsNamaPenerbit;
        private TextView tvListFeedsTanggalBerita;
        private TextView tvListFeedsIsiBerita;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            ciListFeedsProfileImage = itemView.findViewById(R.id.ci_list_feeds_profile_image);
            tvListFeedsJudulBerita = itemView.findViewById(R.id.tv_list_feeds_judul_berita);
            tvListFeedsNamaPenerbit = itemView.findViewById(R.id.tv_list_feeds_nama_penerbit);
            tvListFeedsTanggalBerita = itemView.findViewById(R.id.tv_list_feeds_tanggal_berita);
            tvListFeedsIsiBerita = itemView.findViewById(R.id.tv_list_feeds_isi_berita);
        }
    }
}
