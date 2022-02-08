package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.adapter.details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.detailKoreksiKehadiran.ListOfApprovalsItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsListApprovalKehadiranAdapter extends RecyclerView.Adapter<DetailsListApprovalKehadiranAdapter.ViewHolder> {
    Context context;
    private List<ListOfApprovalsItem> dataItems;
    private String dateServer;

    private final String TAG = "debug";

    public DetailsListApprovalKehadiranAdapter(Context context, List<ListOfApprovalsItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_koreksi_kehadiran_persetujuan, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDetailsApprovalName.setText(dataItems.get(position).getApproverName() + " (" + dataItems.get(position).getApproverNpp() + ")");
        holder.tvDetailsApprovalPosition.setText(dataItems.get(position).getApproverPosition());
        holder.tvDetailsApprovalStatus.setText(dataItems.get(position).getApprovalStatus());
        holder.tvDetailsApprovalNote.setText(dataItems.get(position).getApproverNote());
        holder.tvDetailsApprovalDateTime.setText(dataItems.get(position).getApprovalDatetime());

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvKlik;
        private CircleImageView ciListFeedsProfileImage;
        private TextView tvDetailsApprovalName;
        private TextView tvDetailsApprovalPosition;
        private TextView tvDetailsApprovalNote;
        private TextView tvDetailsApprovalStatus;
        private TextView tvDetailsApprovalDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvKlik = itemView.findViewById(R.id.cv_klik);
            ciListFeedsProfileImage = itemView.findViewById(R.id.ci_list_feeds_profile_image);
            tvDetailsApprovalName = itemView.findViewById(R.id.tv_details_approval_name);
            tvDetailsApprovalPosition = itemView.findViewById(R.id.tv_details_approval_position);
            tvDetailsApprovalNote = itemView.findViewById(R.id.tv_details_approval_note);
            tvDetailsApprovalStatus = itemView.findViewById(R.id.tv_details_approval_status);
            tvDetailsApprovalDateTime = itemView.findViewById(R.id.tv_details_approval_date_time);
        }
    }
}
