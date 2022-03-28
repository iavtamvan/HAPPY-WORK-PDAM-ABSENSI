package com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.R;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.model.DataItem;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.postKoreksiKehadiran.DetailsItem;

import java.util.ArrayList;
import java.util.List;

public class EditDetailsPerjalananDinasAdapter extends RecyclerView.Adapter<EditDetailsPerjalananDinasAdapter.ViewHolder> {
    Context context;
    private List<DataItem> dataItems;

    public List<DetailsItem> detailsItemArray = new ArrayList<>();
    //    private DetailsItem detailsItems;
    private String dateServer;
    private String dateServerPostman;

    private final String TAG = "debug";

    public EditDetailsPerjalananDinasAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_perjalan_dinas_req, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        dateServer = new SimpleDateFormat("EEE, dd MMM yyyy").format(dataItems.get(position).getRecordDate());
//        dateServerPostman = new SimpleDateFormat("yyyy-MM-dd").format(dataItems.get(position).getRecordDate());

//        detailsItems = new DetailsItem();
////        for (int i = 0; i < dataItems.size(); i++) {
//        detailsItems.setReason("-");
//        detailsItems.setRecordDateBefore(dateServerPostman);
//        detailsItems.setActualTimeInAfter(dataItems.get(position).getIn().getRecordTime());
////        detailsItems.setActualTimeOutAfter(dataItems.get(position).getOut().getRecordTime());
//        detailsItemArray.add(detailsItems);
//            break;
//        }


    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvKlik;
        private TextView tvListDestination;
        private EditText edtBottomDialogPerjalananDinasStart;
        private EditText edtBottomDialogPerjalananDinasEnd;
        private Button btnKehadiranLihatDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cv_klik);
            tvListDestination = itemView.findViewById(R.id.tv_list_destination);
            edtBottomDialogPerjalananDinasStart = itemView.findViewById(R.id.edt_bottom_dialog_perjalanan_dinas_start);
            edtBottomDialogPerjalananDinasEnd = itemView.findViewById(R.id.edt_bottom_dialog_perjalanan_dinas_end);
            btnKehadiranLihatDetail = itemView.findViewById(R.id.btn_kehadiran_lihat_detail);
        }
    }
}
