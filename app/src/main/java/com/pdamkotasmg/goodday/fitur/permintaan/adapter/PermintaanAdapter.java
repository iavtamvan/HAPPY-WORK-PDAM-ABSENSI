package com.pdamkotasmg.goodday.fitur.permintaan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.activity.DetailKoreksiKehadiranActivity;
import com.pdamkotasmg.goodday.fitur.permintaan.model.DataItem;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.List;

public class PermintaanAdapter extends RecyclerView.Adapter<PermintaanAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;

    public PermintaanAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_request, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (dataItems.get(position).getRequestStatusCode().equalsIgnoreCase("REJECTED")){
            holder.divRequestDitolak.setVisibility(View.VISIBLE);
            holder.divRequestWaiting.setVisibility(View.GONE);
            holder.divRequestDisetujui.setVisibility(View.GONE);
        } else if (dataItems.get(position).getRequestStatusCode().equalsIgnoreCase("WAITING")){
            holder.divRequestWaiting.setVisibility(View.VISIBLE);
            holder.divRequestDisetujui.setVisibility(View.GONE);
            holder.divRequestDitolak.setVisibility(View.GONE);
        } else {
            holder.divRequestDisetujui.setVisibility(View.VISIBLE);
            holder.divRequestWaiting.setVisibility(View.GONE);
            holder.divRequestDitolak.setVisibility(View.GONE);
        }

        holder.tvListItemRequestTypeName.setText(dataItems.get(position).getRequestTypeName());
        holder.tvListItemRequestForName.setText(dataItems.get(position).getRequestedForName());
        holder.tvListItemRequestJabatan.setText(dataItems.get(position).getRequestedForJabatan());
        holder.tvListItemRequestSatker.setText(dataItems.get(position).getRequestedForBagian());
        holder.tvListItemRequestNumber.setText(dataItems.get(position).getRequestNumber());
        holder.tvListItemRequestFrom.setText(dataItems.get(position).getRequestedAt());

        holder.cvKlik.setOnClickListener(v -> {
            if (dataItems.get(position).getRequestTypeCode().equalsIgnoreCase("RAC")){
                Intent intent = new Intent(context, DetailKoreksiKehadiranActivity.class);
                intent.putExtra(Config.BUNDLE_NUMBER_REQUEST, dataItems.get(position).getRequestNumber());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvKlik;
        private LinearLayout divRequestDisetujui;
        private LinearLayout divRequestDitolak;
        private LinearLayout divRequestWaiting;
        private TextView tvListItemRequestTypeName;
        private TextView tvListItemRequestForName;
        private TextView tvListItemRequestJabatan;
        private TextView tvListItemRequestSatker;
        private TextView tvListItemRequestNumber;
        private TextView tvListItemRequestFrom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            divRequestDisetujui = itemView.findViewById(R.id.div_request_disetujui);
            divRequestDitolak = itemView.findViewById(R.id.div_request_ditolak);
            divRequestWaiting = itemView.findViewById(R.id.div_request_waiting);
            tvListItemRequestTypeName = itemView.findViewById(R.id.tv_list_item_request_type_name);
            tvListItemRequestForName = itemView.findViewById(R.id.tv_list_item_request_for_name);
            tvListItemRequestJabatan = itemView.findViewById(R.id.tv_list_item_request_jabatan);
            tvListItemRequestSatker = itemView.findViewById(R.id.tv_list_item_request_satker);
            tvListItemRequestNumber = itemView.findViewById(R.id.tv_list_item_request_number);
            tvListItemRequestFrom = itemView.findViewById(R.id.tv_list_item_request_from);
        }
    }
}
