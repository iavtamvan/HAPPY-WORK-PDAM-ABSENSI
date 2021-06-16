package com.pdamkotasmg.happywork.fitur.kehadiran.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.pdamkotasmg.happywork.R;
import com.pdamkotasmg.happywork.fitur.kehadiran.model.DataItem;
import com.pdamkotasmg.happywork.utils.Config;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KehadiranAdapter extends RecyclerView.Adapter<KehadiranAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;

    public KehadiranAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_kehadiran, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        try {
//            @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat("EEE, dd MMM yyyy");
//            Date date = fmt.parse(dataItems.get(position).getRecordDate());
//            holder.tvListKehadiranDate.setText(String.valueOf(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        String dateServer = new SimpleDateFormat("EEE, dd MMM yyyy").format(dataItems.get(position).getRecordDate());
        holder.tvListKehadiranDate.setText(dateServer);
        Log.d("debug", "Absensi In : " + dataItems.get(position).getIn());
        Log.d("debug", String.valueOf("Absensi Out : " + dataItems.get(position).getOut() == null));

        ImagePopup imagePopupMasuk = new ImagePopup(context);
        imagePopupMasuk.setWindowHeight(800); // Optional
        imagePopupMasuk.setWindowWidth(800); // Optional
        imagePopupMasuk.setBackgroundColor(Color.TRANSPARENT);  // Optional
        imagePopupMasuk.setFullScreen(false); // Optional
        imagePopupMasuk.setHideCloseIcon(false);  // Optional
        imagePopupMasuk.setImageOnClickClose(true);  // Optional

        ImagePopup imagePopupKeluar = new ImagePopup(context);
        imagePopupKeluar.setWindowHeight(800); // Optional
        imagePopupKeluar.setWindowWidth(800); // Optional
        imagePopupKeluar.setBackgroundColor(Color.TRANSPARENT);  // Optional
        imagePopupKeluar.setFullScreen(false); // Optional
        imagePopupKeluar.setHideCloseIcon(false);  // Optional
        imagePopupKeluar.setImageOnClickClose(true);  // Optional

        // masuk
        if (dataItems.get(position).getIn().getRecordTime() == null) {
            holder.tvListKehadiranMasuk.setText("--:--");
            holder.tvListKehadiranMasukStatus.setText("");
//            imagePopupMasuk.initiatePopup(context.getDrawable(R.drawable.ic_person));
            imagePopupMasuk.initiatePopupWithGlide("https://img.freepik.com/free-vector/people-doing-selfies_52683-4081.jpg?size=626&ext=jpg");
        } else {
            if (dataItems.get(position).getIn().isIsTelat().equalsIgnoreCase("true") && dataItems.get(position).getIn().isIsShiftIn().equalsIgnoreCase("true")) {
                holder.tvListKehadiranMasukStatus.setText("Terlambat");
                Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getIn().getPhoto()).into(holder.ciListKehadiranMasuk);
                holder.tvListKehadiranMasuk.setText(dataItems.get(position).getIn().getRecordTime());
                imagePopupMasuk.initiatePopupWithGlide(Config.BASE_URL_IMAGE + dataItems.get(position).getIn().getPhoto());
            } else {
                holder.tvListKehadiranMasukStatus.setText("Tepat waktu");
                Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getIn().getPhoto()).into(holder.ciListKehadiranMasuk);
                holder.tvListKehadiranMasuk.setText(dataItems.get(position).getIn().getRecordTime());
                imagePopupMasuk.initiatePopupWithGlide(Config.BASE_URL_IMAGE + dataItems.get(position).getIn().getPhoto());

            }
        }

        // keluar
        if (dataItems.get(position).getOut().getRecordTime() == null) {
            holder.tvListKehadiranKeluar.setText("--:--");
            holder.tvListKehadiranKeluarStatus.setText("");
//            imagePopupKeluar.initiatePopup(context.getDrawable(R.drawable.ic_person));
            imagePopupKeluar.initiatePopupWithGlide("https://img.freepik.com/free-vector/people-doing-selfies_52683-4081.jpg?size=626&ext=jpg");
        } else {
            if (dataItems.get(position).getOut().isIsTelat().equalsIgnoreCase("true") && dataItems.get(position).getOut().isIsShiftIn().equalsIgnoreCase("true")) {
                holder.tvListKehadiranKeluarStatus.setText("Terlambat");
                Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getOut().getPhoto()).into(holder.ciListKehadiranKeluar);
                holder.tvListKehadiranKeluar.setText(dataItems.get(position).getOut().getRecordTime());
                imagePopupKeluar.initiatePopupWithGlide(Config.BASE_URL_IMAGE + dataItems.get(position).getOut().getPhoto());
            } else {
                holder.tvListKehadiranKeluarStatus.setText("Tepat waktu");
                Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getOut().getPhoto()).into(holder.ciListKehadiranKeluar);
                holder.tvListKehadiranKeluar.setText(dataItems.get(position).getOut().getRecordTime());
                imagePopupKeluar.initiatePopupWithGlide(Config.BASE_URL_IMAGE + dataItems.get(position).getOut().getPhoto());
            }
        }

        holder.ciListKehadiranMasuk.setOnClickListener(v -> imagePopupMasuk.viewPopup());
        holder.ciListKehadiranKeluar.setOnClickListener(v -> imagePopupKeluar.viewPopup());


//        if (!dataItems.get(position).getIn().equals(null) && !dataItems.get(position).getOut().equals(null)) {
//            Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show();
//        } else {
//            // masuk
//            Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getIn().getPhoto()).into(holder.ciListKehadiranMasuk);
//            holder.tvListKehadiranMasuk.setText(dataItems.get(position).getIn().getRecordTime());
//
//            // keluar
//            Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getOut().getPhoto()).into(holder.ciListKehadiranKeluar);
//            holder.tvListKehadiranKeluar.setText(dataItems.get(position).getOut().getRecordTime());
//        }

//        if (dataItems.get(position).getIsShiftIn() == 1) { // masuk
//
//            Log.d("debug", "Status absen: masuk");
//            Log.d("debug", "Status absen Jam: " + dataItems.get(position).getRecordDate());
//        } else { // keluar
//
//            Log.d("debug", "Status absen: keluar");
//            Log.d("debug", "Status absen Jam: " + dataItems.get(position).getRecordDate());
//        }

//        Glide.with(context).load(Config.BASE_URL_IMAGE + dataItems.get(position).getPhoto()).into(holder.ciListKehadiranKeluar);
//        holder.tvListKehadiranDate.setText(dataItems.get(position).getTglPublish());

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvKlik;
        private CircleImageView ciListKehadiranMasuk;
        private TextView tvListKehadiranMasuk;
        private CircleImageView ciListKehadiranKeluar;
        private TextView tvListKehadiranKeluar;
        private TextView tvListKehadiranDate;
        private TextView tvListKehadiranMasukStatus;
        private TextView tvListKehadiranKeluarStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            ciListKehadiranMasuk = itemView.findViewById(R.id.ci_list_kehadiran_masuk);
            tvListKehadiranMasuk = itemView.findViewById(R.id.tv_list_kehadiran_masuk);
            ciListKehadiranKeluar = itemView.findViewById(R.id.ci_list_kehadiran_keluar);
            tvListKehadiranKeluar = itemView.findViewById(R.id.tv_list_kehadiran_keluar);
            tvListKehadiranDate = itemView.findViewById(R.id.tv_list_kehadiran_date);
            tvListKehadiranMasukStatus = itemView.findViewById(R.id.tv_list_kehadiran_masuk_status);
            tvListKehadiranKeluarStatus = itemView.findViewById(R.id.tv_list_kehadiran_keluar_status);
        }
    }
}
