package com.pdamkotasmg.goodday.fitur.kehadiran.cuti.adapter;

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
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.riwayatCuti.ListOfApprovalsItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsListApprovalAdapter extends RecyclerView.Adapter<DetailsListApprovalAdapter.ViewHolder> {
    Context context;
    private List<ListOfApprovalsItem> dataItems;
    private String dateServer;

    private final String TAG = "debug";

    public DetailsListApprovalAdapter(Context context, List<ListOfApprovalsItem> dataItems) {
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
//        holder.tvDetailsApprovalPosition.setText(dataItems.get(position).getApproverPosition());
        holder.tvDetailsApprovalNote.setText(dataItems.get(position).getApproverNote());

        // TODO convert Tanggal dari server
        if (dataItems.get(position).getApprovalDatetime() != null){
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                Date getApprovalDatetime = date.parse(dataItems.get(position).getApprovalDatetime());

                String dateApprovalDatetime = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss").format(getApprovalDatetime);

                holder.tvDetailsApprovalDateTime.setText(dateApprovalDatetime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


//        holder.tvDetailsApprovalDateTime.setText(dataItems.get(position).getApprovalDatetime());
        holder.tvDetailsApprovalStatus.setText(dataItems.get(position).getApprovalStatus());

        if (dataItems.get(position).getApprovalStatus().equalsIgnoreCase("Waiting")) {
            holder.tvDetailsApprovalStatus.setTextColor(context.getResources().getColor(R.color.yellowPortal));
        } else if (dataItems.get(position).getApprovalStatus().equalsIgnoreCase("Approved")) {
            holder.tvDetailsApprovalStatus.setTextColor(context.getResources().getColor(R.color.greenPortal));
        } else {
            holder.tvDetailsApprovalStatus.setTextColor(context.getResources().getColor(R.color.redPortal));
        }

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
