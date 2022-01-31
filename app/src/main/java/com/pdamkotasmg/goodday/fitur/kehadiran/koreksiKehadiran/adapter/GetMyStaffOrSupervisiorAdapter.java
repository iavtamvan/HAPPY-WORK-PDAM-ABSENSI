package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.KoreksiKehadiranActivity;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.myStaff.DataItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetMyStaffOrSupervisiorAdapter extends RecyclerView.Adapter<GetMyStaffOrSupervisiorAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;

    public GetMyStaffOrSupervisiorAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_get_staff_or_supervisior, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvListGetStaffOrSupervisiorNpp.setText(dataItems.get(position).getNpp());
        holder.tvListGetStaffOrSupervisiorNama.setText(dataItems.get(position).getName());
        holder.tvListGetStaffOrSupervisiorJabatan.setText(dataItems.get(position).getPosition());

        holder.cvKlik.setOnClickListener(v -> {
            ((KoreksiKehadiranActivity) context).npp = dataItems.get(position).getNpp();
            ((KoreksiKehadiranActivity) context).edtRequestFor.setText(dataItems.get(position).getName() + " (" + dataItems.get(position).getNpp() + ")");
            ((KoreksiKehadiranActivity) context).tvTutupDialog.performClick();


            Toast.makeText(context, "Data diperbarui", Toast.LENGTH_SHORT).show();
            Log.d("debug", "NPP Updated : " + ((KoreksiKehadiranActivity) context).npp);
            Log.d("debug", "edtRequestFor Updated : " + ((KoreksiKehadiranActivity) context).edtRequestFor.getText().toString().trim());

        });
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvKlik;
        private CircleImageView ciListFeedsProfileImage;
        private TextView tvListGetStaffOrSupervisiorNpp;
        private TextView tvListGetStaffOrSupervisiorNama;
        private TextView tvListGetStaffOrSupervisiorJabatan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            ciListFeedsProfileImage = itemView.findViewById(R.id.ci_list_feeds_profile_image);
            tvListGetStaffOrSupervisiorNpp = itemView.findViewById(R.id.tv_list_get_staff_or_supervisior_npp);
            tvListGetStaffOrSupervisiorNama = itemView.findViewById(R.id.tv_list_get_staff_or_supervisior_nama);
            tvListGetStaffOrSupervisiorJabatan = itemView.findViewById(R.id.tv_list_get_staff_or_supervisior_jabatan);
        }
    }
}
