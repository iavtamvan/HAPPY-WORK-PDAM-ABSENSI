package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.model.DataItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.postKoreksiKehadiran.DetailsItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailsKehadiranAdapter extends RecyclerView.Adapter<DetailsKehadiranAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;
    private DetailsKehadiranAdapter detailsKehadiranAdapter;

    public List<DetailsItem> detailsItemArray = new ArrayList<>();
    private DetailsItem detailsItems;
    private String dateServer;
    private String dateServerKontol;

    private final String TAG = "debug";

    public DetailsKehadiranAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_koreksi_kehadiran_details, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        dateServer = new SimpleDateFormat("EEE, dd MMM yyyy").format(dataItems.get(position).getRecordDate());
        dateServerKontol = new SimpleDateFormat("yyyy-MM-dd").format(dataItems.get(position).getRecordDate());
        holder.tvListKoreksiKehadiranDetailsDate.setText(dateServer);

        holder.tvListKoreksiKehadiranDetailsShift.setText(dataItems.get(position).getIn().getShiftRemark());

        // masuk
        if (dataItems.get(position).getIn().getRecordTime() == null) {
            holder.tvListKoreksiKehadiranDetailsStartTime.setText("--:-- | ");
        } else {
            holder.tvListKoreksiKehadiranDetailsStartTime.setText(dataItems.get(position).getIn().getRecordTime() + " | ");
        }

        // keluar
        if (dataItems.get(position).getOut().getRecordTime() == null) {
            holder.tvListKoreksiKehadiranDetailsEndTime.setText("--:--");
        } else {
            holder.tvListKoreksiKehadiranDetailsEndTime.setText(dataItems.get(position).getOut().getRecordTime());
        }

        detailsItems = new DetailsItem();
        for (int i = 0; i < dataItems.size(); i++) {
            detailsItems.setReason("-");
            detailsItems.setRecordDateBefore(dateServerKontol);
            detailsItems.setActualTimeInAfter(dataItems.get(position).getIn().getRecordTime());
            detailsItems.setActualTimeOutAfter(dataItems.get(position).getOut().getRecordTime());
            detailsItemArray.add(detailsItems);
            break;
        }

        holder.cvKlik.setOnClickListener(v -> {
            Log.d(TAG, "Klik : " + position);
            showBottomSheetDialog(holder, position);
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvKlik;
        private TextView tvListKoreksiKehadiranDetailsDate;
        private TextView tvListKoreksiKehadiranDetailsStartTime;
        private TextView tvListKoreksiKehadiranDetailsEndTime;
        private TextView tvListKoreksiKehadiranDetailsShift;
        private TextView tvListKoreksiKehadiranDetailsStatusUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            tvListKoreksiKehadiranDetailsDate = itemView.findViewById(R.id.tv_list_koreksi_kehadiran_details_date);
            tvListKoreksiKehadiranDetailsStartTime = itemView.findViewById(R.id.tv_list_koreksi_kehadiran_details_start_time);
            tvListKoreksiKehadiranDetailsEndTime = itemView.findViewById(R.id.tv_list_koreksi_kehadiran_details_end_time);
            tvListKoreksiKehadiranDetailsShift = itemView.findViewById(R.id.tv_list_koreksi_kehadiran_details_shift);
            tvListKoreksiKehadiranDetailsStatusUpdate = itemView.findViewById(R.id.tv_list_koreksi_kehadiran_details_status_update);
        }
    }


    private EditText edtBottomDialogKoreksiKehadiranDate;
    private EditText edtBottomDialogKoreksiKehadiranShift;
    private EditText edtBottomDialogKoreksiKehadiranReason;
    private EditText edtBottomDialogKoreksiKehadiranBeforeStartTime;
    private EditText edtBottomDialogKoreksiKehadiranBeforeEndTime;
    private EditText edtBottomDialogKoreksiKehadiranAfterStartTime;
    private EditText edtBottomDialogKoreksiKehadiranAfterEndTime;
    private Button btnUpdateDataArrayLocal;

    @SuppressLint({"NotifyDataSetChanged", "ResourceAsColor"})
    private void showBottomSheetDialog(@NonNull ViewHolder holder, int position) {
        final BottomSheetDialog bottomSheetDialogParrentSb = new BottomSheetDialog(context);
        bottomSheetDialogParrentSb.setContentView(R.layout.bottom_sheet_dialog_detail_koreksi_kehadiran);
        edtBottomDialogKoreksiKehadiranDate = bottomSheetDialogParrentSb.findViewById(R.id.edt_bottom_dialog_koreksi_kehadiran_date);
        edtBottomDialogKoreksiKehadiranShift = bottomSheetDialogParrentSb.findViewById(R.id.edt_bottom_dialog_koreksi_kehadiran_shift);
        edtBottomDialogKoreksiKehadiranReason = bottomSheetDialogParrentSb.findViewById(R.id.edt_bottom_dialog_koreksi_kehadiran_reason);
        edtBottomDialogKoreksiKehadiranBeforeStartTime = bottomSheetDialogParrentSb.findViewById(R.id.edt_bottom_dialog_koreksi_kehadiran_before_start_time);
        edtBottomDialogKoreksiKehadiranBeforeEndTime = bottomSheetDialogParrentSb.findViewById(R.id.edt_bottom_dialog_koreksi_kehadiran_before_end_time);
        edtBottomDialogKoreksiKehadiranAfterStartTime = bottomSheetDialogParrentSb.findViewById(R.id.edt_bottom_dialog_koreksi_kehadiran_after_start_time);
        edtBottomDialogKoreksiKehadiranAfterEndTime = bottomSheetDialogParrentSb.findViewById(R.id.edt_bottom_dialog_koreksi_kehadiran_after_end_time);
        btnUpdateDataArrayLocal = bottomSheetDialogParrentSb.findViewById(R.id.btn_update_data_array_local);

        Log.d(TAG, "Date data item : " + dataItems.get(position).getRecordDate());
        Log.d(TAG, "Date detailsItemArray : " + detailsItemArray.get(position).getRecordDateBefore());


        Log.d(TAG, "================= START SEBELUM ======================");
        Log.d(TAG, "Sebelum Edit getReason : " + detailsItemArray.get(position).getReason());
        Log.d(TAG, "Sebelum Edit getRecordDateBefore : " + detailsItemArray.get(position).getRecordDateBefore());
        Log.d(TAG, "Sebelum Edit getActualTimeInAfter : " + detailsItemArray.get(position).getActualTimeInAfter());
        Log.d(TAG, "Sebelum Edit getActualTimeOutAfter : " + detailsItemArray.get(position).getActualTimeOutAfter());
        Log.d(TAG, "================= END SEBELUM ================");

        btnUpdateDataArrayLocal.setOnClickListener(v -> {
            if (edtBottomDialogKoreksiKehadiranReason.getText().toString().isEmpty() || edtBottomDialogKoreksiKehadiranAfterStartTime.getText().toString().isEmpty()
                    || edtBottomDialogKoreksiKehadiranAfterEndTime.getText().toString().isEmpty()) {
                Toast.makeText(context, "Lengkapi form terlebih dahulu", Toast.LENGTH_SHORT).show();
            } else {
                // todo update data array

                // TODO isi detail
//            Log.d(TAG, "showBottomSheetDialogPosition: " + position);
//            Log.d(TAG, "showBottomSheetDialogData: " + dataItems.get(position));
//            Log.d(TAG, "=======================================");
//            Log.d(TAG, "showBottomSheetDialogDatagetgetRecordTimegetIn: " + dataItems.get(position).getIn().getRecordTime());
//            Log.d(TAG, "showBottomSheetDialogDatagetgetRecordTimegetOut: " + dataItems.get(position).getOut().getRecordTime());
//            Log.d(TAG, "=======================================");
//            Log.d(TAG, "showBottomSheetDialogDatagetgetRecordTimeIn: " + edtBottomDialogKoreksiKehadiranAfterStartTime.getText().toString().trim());
//            Log.d(TAG, "showBottomSheetDialogDatagetgetRecordTimeOut: " + edtBottomDialogKoreksiKehadiranAfterEndTime.getText().toString().trim());

                dataItems.get(position).getIn().setRecordTime(edtBottomDialogKoreksiKehadiranAfterStartTime.getText().toString().trim());
                dataItems.get(position).getOut().setRecordTime(edtBottomDialogKoreksiKehadiranAfterEndTime.getText().toString().trim());

                holder.tvListKoreksiKehadiranDetailsStartTime.setText(dataItems.get(position).getIn().getRecordTime() + " | ");
                holder.tvListKoreksiKehadiranDetailsEndTime.setText(dataItems.get(position).getOut().getRecordTime());
                holder.tvListKoreksiKehadiranDetailsStatusUpdate.setText("updated");
                holder.tvListKoreksiKehadiranDetailsStatusUpdate.setTextColor(context.getResources().getColor(R.color.primary));

                detailsKehadiranAdapter = new DetailsKehadiranAdapter(context, dataItems);
                detailsKehadiranAdapter.notifyDataSetChanged();

                // TODO mempersiapkan yang akan dikirim ke server
                detailsItemArray.get(position).setReason(edtBottomDialogKoreksiKehadiranReason.getText().toString().trim());
                detailsItemArray.get(position).setRecordDateBefore(dateServerKontol);
                detailsItemArray.get(position).setActualTimeInAfter(dataItems.get(position).getIn().getRecordTime());
                detailsItemArray.get(position).setActualTimeOutAfter(dataItems.get(position).getOut().getRecordTime());

                Log.d(TAG, "================= START SETELAH ======================");
                Log.d(TAG, "SUKSES Setelah Edit getReason : " + detailsItemArray.get(position).getReason());
                Log.d(TAG, "SUKSES Setelah Edit getRecordDateBefore : " + detailsItemArray.get(position).getRecordDateBefore());
                Log.d(TAG, "SUKSES Setelah Edit getActualTimeInAfter : " + detailsItemArray.get(position).getActualTimeInAfter());
                Log.d(TAG, "SUKSES Setelah Edit getActualTimeOutAfter : " + detailsItemArray.get(position).getActualTimeOutAfter());
                Log.d(TAG, "================= END SETELAH ======================");

                Log.d(TAG, "RecordDate Position: " + position + " > " + detailsItemArray.get(position).getRecordDateBefore());
                Log.d(TAG, "Reason Position: " + position + " > " + detailsItemArray.get(position).getReason());
            }


            bottomSheetDialogParrentSb.hide();

        });

        edtBottomDialogKoreksiKehadiranReason.setText(detailsItemArray.get(position).getReason());

        String dateServerBottomDialog = new SimpleDateFormat("EEE, dd MMM yyyy").format(dataItems.get(position).getRecordDate());
        edtBottomDialogKoreksiKehadiranDate.setText(dateServerBottomDialog);

        edtBottomDialogKoreksiKehadiranShift.setText(dataItems.get(position).getIn().getShiftRemark());

        edtBottomDialogKoreksiKehadiranBeforeStartTime.setText(dataItems.get(position).getIn().getRecordTime());
        edtBottomDialogKoreksiKehadiranBeforeEndTime.setText(dataItems.get(position).getOut().getRecordTime());

        edtBottomDialogKoreksiKehadiranAfterStartTime.setText(dataItems.get(position).getIn().getRecordTime());

        edtBottomDialogKoreksiKehadiranAfterStartTime.setOnClickListener(v -> {
            // TODO Auto-generated method stub
        });
        edtBottomDialogKoreksiKehadiranAfterEndTime.setOnClickListener(v -> {


        });

        bottomSheetDialogParrentSb.show();
    }
}
