package co.id.pdamkotasmg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.id.pdamkotasmg.local.db.entity.PendingBacaanEntity;
import co.id.pdamkotasmg.pembacameter.R;
import co.id.pdamkotasmg.pembacameter.databinding.ItemPendingDataBinding;

public class PendingDataAdapter extends RecyclerView.Adapter<PendingDataAdapter.VH> {

    public interface Listener {
        void onItemClick(PendingBacaanEntity entity);
        void onRetryClick(PendingBacaanEntity entity);
        void onDeleteClick(PendingBacaanEntity entity);
    }

    private final Context ctx;
    private final List<PendingBacaanEntity> items;
    private final Listener listener;
    private final SimpleDateFormat dateFmt = new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault());

    public PendingDataAdapter(Context ctx, List<PendingBacaanEntity> items, Listener listener) {
        this.ctx = ctx;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPendingDataBinding b = ItemPendingDataBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        PendingBacaanEntity item = items.get(position);
        bind(h.b, item);

        h.b.getRoot().setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });
        h.b.btnRetry.setOnClickListener(v -> {
            if (listener != null) listener.onRetryClick(item);
        });
        h.b.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(item);
        });
    }

    private void bind(ItemPendingDataBinding b, PendingBacaanEntity item) {
        b.tvNolangg.setText(item.nolangg != null ? item.nolangg : "-");

        // Sub info: jenis + bacaan kini
        StringBuilder sub = new StringBuilder();
        sub.append(formatJenis(item.jenis));
        if (item.kini != null && !item.kini.isEmpty()) {
            sub.append(" • Kini: ").append(item.kini);
        }
        b.tvSubInfo.setText(sub.toString());

        // Time
        if (item.createdAt > 0) {
            b.tvCreatedAt.setText(dateFmt.format(new Date(item.createdAt)));
        } else {
            b.tvCreatedAt.setText("");
        }

        // Status badge
        applyStatusBadge(b, item);

        // Retry button visible only kalau FAILED
        boolean failed = item.syncStatus == PendingBacaanEntity.STATUS_FAILED;
        b.btnRetry.setVisibility(failed ? View.VISIBLE : View.GONE);

        // Last error visible kalau ada
        if (failed && item.lastError != null && !item.lastError.isEmpty()) {
            b.tvErrorMsg.setVisibility(View.VISIBLE);
            String err = item.lastError;
            if (err.length() > 80) err = err.substring(0, 80) + "...";
            b.tvErrorMsg.setText("⚠ " + err);
        } else {
            b.tvErrorMsg.setVisibility(View.GONE);
        }
    }

    private void applyStatusBadge(ItemPendingDataBinding b, PendingBacaanEntity item) {
        switch (item.syncStatus) {
            case PendingBacaanEntity.STATUS_IN_FLIGHT:
                b.tvStatusBadge.setText("MENGIRIM");
                b.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_in_flight);
                break;
            case PendingBacaanEntity.STATUS_FAILED:
                b.tvStatusBadge.setText("GAGAL");
                b.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_failed);
                break;
            case PendingBacaanEntity.STATUS_SUCCESS:
                b.tvStatusBadge.setText("SUKSES");
                b.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_success);
                break;
            case PendingBacaanEntity.STATUS_PENDING:
            default:
                b.tvStatusBadge.setText("PENDING");
                b.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_pending);
                break;
        }

        if (item.retryCount > 0) {
            b.tvRetryCount.setVisibility(View.VISIBLE);
            b.tvRetryCount.setText("retry " + item.retryCount + "×");
        } else {
            b.tvRetryCount.setVisibility(View.GONE);
        }
    }

    private static String formatJenis(String jenis) {
        if (jenis == null) return "Tidak diketahui";
        switch (jenis) {
            case PendingBacaanEntity.JENIS_KHUSUS_BENDEL:
                return "Khusus Bendel";
            case PendingBacaanEntity.JENIS_PER_PELANGGAN:
                return "Per Pelanggan";
            case PendingBacaanEntity.JENIS_PER_FOTO_METER:
                return "Foto Meter";
            case PendingBacaanEntity.JENIS_BACA_ULANG:
                return "Baca Ulang";
            default:
                return jenis;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final ItemPendingDataBinding b;
        VH(ItemPendingDataBinding b) {
            super(b.getRoot());
            this.b = b;
        }
    }
}