package com.pdamsmg.pekerjaanteknik.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamsmg.pekerjaanteknik.BuildConfig;
import com.pdamsmg.pekerjaanteknik.R;
import com.pdamsmg.pekerjaanteknik.fitur.activity.spk.InputSpkActivity;
import com.pdamsmg.pekerjaanteknik.model.warning.DataItem;
import com.pdamsmg.pekerjaanteknik.utils.Config;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class WarningAdapter extends RecyclerView.Adapter<WarningAdapter.ViewHolder> {
    private final String TAG = "debug";
    Context context;
    private List<DataItem> dataItem;

    public WarningAdapter(Context context, List<DataItem> dataItem) {
        this.context = context;
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_warning, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Log.d(TAG, "update messages: " + dataItem.get(position).getMessaggeUpdate().toString().replace("[", "").replace(",", "\n"));
//        if (dataItem.get(position).getUpdateApk().isEmpty()) {
//            Log.d(TAG, "update APK: empty");
//        } else {
//            ArrayList<String> updateApkList = new ArrayList<>();
//            for (int i = 0; i < dataItem.get(position).getUpdateApk().size(); i++) {
//                Log.d(TAG, "update APK: " + dataItem.get(position).getUpdateApk().get(i));
//                updateApkList.add(dataItem.get(position).getUpdateApk().get(i));
//            }
//            String resultUpdateApk = TextUtils.join(", \n", updateApkList);
//            Log.d(TAG, "update APK: " + resultUpdateApk);
//            if (!resultUpdateApk.equals(BuildConfig.VERSION_NAME)){
//                MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
//                        .setTitle("Aplikasi harus di update ke versi " + resultUpdateApk + ", \nsekarang memakai aplikasi versi " + BuildConfig.VERSION_NAME)
//                        .setMessage(dataItem.get(position).getMessaggeUpdate().toString().replace("[", "").replace("]", "").replace(",", "\n"))
//                        .setCancelable(false)
//                        .setPositiveButton("Update Sekarang", (dialogInterface, which) -> {
////                            dialogInterface.dismiss();
//                            final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
//                            try {
//                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                            } catch (android.content.ActivityNotFoundException anfe) {
//                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                            }
//                        })
//                        .build();
//
//                mDialog.show();
//            } else {
//                Log.d(TAG, "Apk Action: OK" );
//            }
//
//        }

        if (dataItem.get(position).getVerifikator().isEmpty()) {
            holder.divVerifikator.setVisibility(View.GONE);
        } else {
            ArrayList<String> verifikatorList = new ArrayList<>();
            for (int i = 0; i < dataItem.get(position).getVerifikator().size(); i++) {
                Log.d(TAG, "onBindViewHolder: " + dataItem.get(position).getVerifikator().get(i));
                verifikatorList.add(dataItem.get(position).getVerifikator().get(i));
            }
            String resultVerifikator = TextUtils.join(", \n", verifikatorList);
            holder.tvVerifikator.setText(resultVerifikator);
        }

        if (dataItem.get(position).getPeringatan().isEmpty()) {
            holder.divPeringatan.setVisibility(View.GONE);
        } else {
            ArrayList<String> peringatanList = new ArrayList<>();
            for (int i = 0; i < dataItem.get(position).getPeringatan().size(); i++) {
                Log.d(TAG, "onBindViewHolder: " + dataItem.get(position).getPeringatan().get(i));
                peringatanList.add(dataItem.get(position).getPeringatan().get(i));
            }
            String resultPeringatan = TextUtils.join(", \n", peringatanList);
            holder.tvPeringatan.setText(resultPeringatan);
        }

        if (dataItem.get(position).getInfo().isEmpty()) {
            holder.divInfo.setVisibility(View.GONE);
        } else {
            ArrayList<String> infoList = new ArrayList<>();
            for (int i = 0; i < dataItem.get(position).getInfo().size(); i++) {
                Log.d(TAG, "onBindViewHolder: " + dataItem.get(position).getInfo().get(i));
                infoList.add(dataItem.get(position).getInfo().get(i));
            }
            String resultInfo = TextUtils.join(", \n", infoList);
            holder.tvInfo.setText(resultInfo);
        }

    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cvKlik;
        private LinearLayout divVerifikator;
        private LinearLayout divPeringatan;
        private LinearLayout divInfo;
        private TextView tvVerifikator;
        private TextView tvPeringatan;
        private TextView tvInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvKlik = itemView.findViewById(R.id.cvKlik);
            divVerifikator = itemView.findViewById(R.id.div_verifikator);
            divPeringatan = itemView.findViewById(R.id.div_peringatan);
            divInfo = itemView.findViewById(R.id.div_info);
            tvVerifikator = itemView.findViewById(R.id.tv_verifikator);
            tvPeringatan = itemView.findViewById(R.id.tv_peringatan);
            tvInfo = itemView.findViewById(R.id.tv_info);
        }
    }
}
