package co.id.pdamkotasmg.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.DecelerateInterpolator;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pdamkotasmg.goodday.fitur.menuLainnya.ProfilePelangganDanTagihanActivity;
import com.pdamkotasmg.goodday.fitur.menuLainnya.WebViewActivity;
import com.pdamkotasmg.goodday.fitur.profil.ProfileActivity;
import com.pdamkotasmg.goodday.fitur.profil.controller.ProfileController;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import co.id.pdamkotasmg.adapter.CheckKoneksiServerAdapter;
import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.model.checkKoneksi.CheckItem;
import co.id.pdamkotasmg.model.checkKoneksi.CheckKoneksiServerRootModel;
import co.id.pdamkotasmg.model.home.HomeRootModel;
import co.id.pdamkotasmg.model.motivation.RootMotivationItem;
import co.id.pdamkotasmg.pembacameter.R;
import co.id.pdamkotasmg.pembacameter.databinding.FragmentHomeBinding;
import co.id.pdamkotasmg.service.PingService;
import co.id.pdamkotasmg.ui.activity.CariDataActivity;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private String TAG = "debug";

    private FragmentHomeBinding binding;
    private ProfileController profileController;
    private ProgressDialog progressDialog;
    private BroadcastReceiver receiver;
    private StringBuilder terminalOutputBuilder = new StringBuilder();
    private String token;
    private String npp;
    private String nama;
    private String nolangg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        SharedPreferences sp = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
        nama = sp.getString(Config.SHARED_NAME, "");
        npp = sp.getString(Config.SHARED_NPP_PROFILE, "");
        profileController = new ProfileController();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.show();

        binding.scrollingtext.setText("HALOOooo PEMBACA METER..... SELAMAT BEKERJA....");
        binding.scrollingtext.setSelected(true);

        setupScrollAnimations();
        playEntranceAnimations();

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 11) {
            binding.tvGood.setText("Good Morning \uD83C\uDF04");
            Config.showNotification(getActivity(), "Pekerjaan Sudah Siap", "Semangat kerja !!!", getActivity().getClass());
        } else if (timeOfDay >= 11 && timeOfDay < 15) {
            binding.tvGood.setText("Good Afternoon \uD83C\uDF1E");
            Config.showNotification(getActivity(), "Sudah Waktunya Istirahat", "Semangat!!!", getActivity().getClass());
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            Config.showNotification(getActivity(), "Saatnya Istirahat Sejenak", "Kalau lembur, jangan lupa klik Tidur YA!", getActivity().getClass());
            binding.tvGood.setText("Good Evening \uD83C\uDF25");
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            Config.showNotification(getActivity(), "Saatnya Tidur", "Kalau lembur, jangan lupa tidur YA!", getActivity().getClass());
            binding.tvGood.setText("Good Night \uD83D\uDECC \uD83D\uDCA4");
        }

        binding.tvNameDashboard.setText(nama + " (" + npp + ")");
        binding.tvSatker.setText("Pembaca Meter");

        binding.ivLogout.setOnClickListener(view -> {
            MaterialDialog mDialog = new MaterialDialog.Builder(getActivity())
                    .setMessage("Yakin mau keluar?")
                    .setAnimation("lt_logout.json")
                    .setCancelable(true)
                    .setNegativeButton("Gak", (dialogInterface, which) -> dialogInterface.dismiss())
                    .setPositiveButton("Iya", (dialogInterface, which) -> {
                        profileController.logout(getActivity());
                        dialogInterface.dismiss();
                    })
                    .build();

            // Show Dialog
            mDialog.show();
        });

        binding.divProfil.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), ProfileActivity.class));
        });

        binding.ivRefresh.setOnClickListener(view -> {
            binding.tvTotalHariIni.setText("--");
            binding.tvJamAwal.setText("--");
            binding.tvPeriode.setText("--");
            binding.tvTotalPeriode.setText("--");
            progressDialog.show();
            getCountPeriode();
        });

        binding.divInProfilPelanggan.setOnClickListener(view -> {
            final BottomSheetDialog bottomSheetDialogResultCariData = new BottomSheetDialog(getActivity());
            bottomSheetDialogResultCariData.setContentView(R.layout.bottom_sheet_dialog_cari_data);

            TextView tvTutupDialog = bottomSheetDialogResultCariData.findViewById(R.id.tv_tutup_dialog);
            LinearLayout divProfilPelanggan = bottomSheetDialogResultCariData.findViewById(R.id.div_profil_pelanggan);
            LinearLayout divCariNamaAlamatNometer = bottomSheetDialogResultCariData.findViewById(R.id.div_cari_nama_alamat_nometer);

            tvTutupDialog.setOnClickListener(view1 -> {
                bottomSheetDialogResultCariData.dismiss();
            });

            divProfilPelanggan.setOnClickListener(view1 -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Nolangg");

                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", (dialog, which) -> {
                    nolangg = input.getText().toString();
                    if (nolangg.isEmpty()) {
                        Toast.makeText(getActivity(), "Isi nolangg", Toast.LENGTH_SHORT).show();
                    } else {
                        bottomSheetDialogResultCariData.dismiss();
                        Intent intent = new Intent(getActivity(), ProfilePelangganDanTagihanActivity.class);
                        intent.putExtra(Config.BUNDLE_PEMBACA_METER_NOLANGG, nolangg);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                builder.show();
            });

            divCariNamaAlamatNometer.setOnClickListener(view1 -> {
                startActivity(new Intent(getActivity(), CariDataActivity.class));
                bottomSheetDialogResultCariData.dismiss();
            });

            bottomSheetDialogResultCariData.show();


        });

        binding.divInCekKoneksi.setOnClickListener(view -> {
            final BottomSheetDialog bottomSheetDialogCheckKoneksi = new BottomSheetDialog(getActivity());
            bottomSheetDialogCheckKoneksi.setContentView(R.layout.bottom_sheet_dialog_check_koneksi);

            TextView tvTutupDialog = bottomSheetDialogCheckKoneksi.findViewById(R.id.tv_tutup_dialog);
            LinearLayout divCheckKoneksiHp = bottomSheetDialogCheckKoneksi.findViewById(R.id.div_check_koneksi_hp);
            LinearLayout divCheckPingInternet = bottomSheetDialogCheckKoneksi.findViewById(R.id.div_stabilitas_ping);
            LinearLayout divCheckKoneksiServer = bottomSheetDialogCheckKoneksi.findViewById(R.id.div_check_koneksi_server);

            tvTutupDialog.setOnClickListener(view1 -> {
                bottomSheetDialogCheckKoneksi.dismiss();
            });

            divCheckKoneksiHp.setOnClickListener(view1 -> {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.SHARED_TYPE_WEB_vIEW, "cekkoneksi");
                editor.commit();

                getActivity().startActivity(new Intent(getActivity(), WebViewActivity.class));
            });

            divCheckPingInternet.setOnClickListener(view1 -> {
            // Mendaftarkan BroadcastReceiver untuk menerima pesan dari layanan
                progressDialog.show();
                receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if ("ping_result".equals(intent.getAction())) {
                            String output = intent.getStringExtra(Config.BUNDLE_PEMBACA_METER_PING_INTERNET);
                            // Menampilkan output ping di TextView
//                            binding.terminalOutput.append(output + "\n");
                            Log.d(TAG, output + "\n");
//                            Toast.makeText(context, "Ping running ...", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.cancel();
                    }
                };
                LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter("ping_result"));

                // Memulai layanan
                getActivity().startService(new Intent(getActivity(), PingService.class));
            });

            divCheckKoneksiServer.setOnClickListener(view1 -> {
                showPopup();
            });

            bottomSheetDialogCheckKoneksi.show();
        });

        Log.d(TAG, "Access Token Pembaca Meter " + token);
        getCountPeriode();
        getMotivation();

        return root;
    }

    private void getMotivation() {
        ApiService apiService = ApiConfig.getApiServiceGitHub(getActivity());
        apiService.getMotivation().enqueue(new Callback<List<RootMotivationItem>>() {
            @Override
            public void onResponse(Call<List<RootMotivationItem>> call, Response<List<RootMotivationItem>> response) {

                Log.d(TAG, "onResponse: " + response.body());

                if (response.isSuccessful()) {

                    List<RootMotivationItem> rootMotivationItems = response.body();

                    int randomIndex = new Random().nextInt(rootMotivationItems.size());
                    RootMotivationItem randomItem = rootMotivationItems.get(randomIndex);

                    Log.d(TAG, "randomItem: " + randomItem.getQuote());

                    binding.tvMotivation.setText(randomItem.getQuote() + " -" + randomItem.getBy() + "-");

                }
            }

            @Override
            public void onFailure(Call<List<RootMotivationItem>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getCountPeriode() {
        ApiService apiService = ApiConfig.getApiServiceGWAPI(getActivity());
        apiService.getCountPeriode(token)
                .enqueue(new Callback<HomeRootModel>() {
                    @Override
                    public void onResponse(Call<HomeRootModel> call, Response<HomeRootModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.cancel();
                            binding.tvTotalHariIni.setText(response.body().getData().getCountTotalpembacaanPetugasHariIni());
                            binding.tvJamAwal.setText(response.body().getData().getJamAwal().getJamBaca());
                            binding.tvPeriode.setText("Total " + response.body().getData().getPeriode());
                            binding.tvTotalPeriode.setText(response.body().getData().getCountTotalPetugas());
                        } else {
                            progressDialog.cancel();
                            Config.logout(getActivity());
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeRootModel> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(getActivity(), "1: " + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private CheckKoneksiServerAdapter checkKoneksiServerAdapter;
    private List<CheckItem> checkItems;
    // Metode untuk menampilkan popup
    private void showPopup() {
        progressDialog.show();
        final BottomSheetDialog bottomSheetDialogCheckKoneksiServer = new BottomSheetDialog(getActivity());
        bottomSheetDialogCheckKoneksiServer.setContentView(R.layout.popup_check_koneksi_server);

        RecyclerView rvCheckKoneksiServer = bottomSheetDialogCheckKoneksiServer.findViewById(R.id.rv_pop_up_chec_koneksi_server);
        TextView tvTutup = bottomSheetDialogCheckKoneksiServer.findViewById(R.id.tv_tutup_dialog);
        TextView tvStServer = bottomSheetDialogCheckKoneksiServer.findViewById(R.id.tv_st_server);

        ApiService apiService = ApiConfig.getApiServiceGWAPI(getActivity());
        apiService.getTestPing().enqueue(new Callback<CheckKoneksiServerRootModel>() {
            @Override
            public void onResponse(Call<CheckKoneksiServerRootModel> call, Response<CheckKoneksiServerRootModel> response) {
                if (response.isSuccessful()){
                    progressDialog.cancel();
                    // Menampilkan dialog
                    bottomSheetDialogCheckKoneksiServer.show();
                    tvStServer.setText("Status Semua Server : " + response.body().getData().getEnd());
                    checkItems = response.body().getData().getCheck();
                    checkKoneksiServerAdapter = new CheckKoneksiServerAdapter(getActivity(), checkItems);
                    rvCheckKoneksiServer.setAdapter(checkKoneksiServerAdapter);
                    rvCheckKoneksiServer.setLayoutManager(new LinearLayoutManager(getActivity()));
                    checkKoneksiServerAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<CheckKoneksiServerRootModel> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(getActivity(), "Failed check", Toast.LENGTH_SHORT).show();
            }
        });

        tvTutup.setOnClickListener(view -> {
            bottomSheetDialogCheckKoneksiServer.cancel();
        });


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    // ============== SCROLL ANIMATIONS (Fase #4) ==============

    private static final int TOP_BAR_HIDE_THRESHOLD_PX = 240;

    /**
     * Listener scroll utk:
     *   - Hide-on-scroll-down top bar (avatar + marquee + refresh + logout) dgn fade+translateY.
     *   - Greeting card parallax subtle (translateY 0.3× scroll, alpha bleed 0.6).
     *
     * Pakai NestedScrollView.OnScrollChangeListener — tersedia sejak NestedScrollView 1.0.
     */
    private void setupScrollAnimations() {
        if (binding == null) return;
        binding.scrollHome.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int x, int y, int oldX, int oldY) {
                if (binding == null) return;

                // Top bar: hide gradually saat scroll lewat threshold
                float topProgress = Math.min(1f, (float) y / (float) TOP_BAR_HIDE_THRESHOLD_PX);
                binding.topBar.setAlpha(1f - topProgress);
                binding.topBar.setTranslationY(-topProgress * binding.topBar.getHeight() * 0.6f);

                // Greeting card parallax + fade
                if (binding.cardGreeting != null) {
                    binding.cardGreeting.setTranslationY(-y * 0.18f);
                    float greetAlpha = Math.max(0.35f, 1f - (y / 600f));
                    binding.cardGreeting.setAlpha(greetAlpha);
                }
            }
        });
    }

    /**
     * Entrance staggered animation untuk kartu-kartu utama:
     *   1. Greeting card slide-up + fade-in (delay 0ms, 400ms)
     *   2. Row stats slide-up + fade-in   (delay 120ms, 400ms)
     *   3. Row actions slide-up + fade-in (delay 240ms, 400ms)
     *   4. Motivation slide-up + fade-in  (delay 360ms, 400ms)
     */
    private void playEntranceAnimations() {
        if (binding == null) return;
        View[] targets = {
                binding.cardGreeting,
                binding.rowStats,
                binding.rowActions,
                binding.cardMotivation
        };
        int delayStep = 110;
        for (int i = 0; i < targets.length; i++) {
            View t = targets[i];
            if (t == null) continue;
            t.setAlpha(0f);
            t.setTranslationY(60f);
            ObjectAnimator fade = ObjectAnimator.ofFloat(t, "alpha", 0f, 1f);
            ObjectAnimator slide = ObjectAnimator.ofFloat(t, "translationY", 60f, 0f);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(fade, slide);
            set.setDuration(420);
            set.setStartDelay((long) i * delayStep);
            set.setInterpolator(new DecelerateInterpolator(1.6f));
            set.start();
        }
    }
}