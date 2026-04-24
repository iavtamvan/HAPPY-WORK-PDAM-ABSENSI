package co.id.pdamkotasmg.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pdamkotasmg.goodday.utils.Config;

import co.id.pdamkotasmg.pembacameter.databinding.FragmentDataPelangganBinding;

public class DataPelangganFragment extends Fragment {

    private FragmentDataPelangganBinding binding;
    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private String user;
    private String pass;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDataPelangganBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        user = sharedPreferences.getString(Config.SHARED_NPP_PROFILE, "");
        pass = sharedPreferences.getString(Config.SHARED_GETPASSWORD, "");

        Log.d("debug", user + pass);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.show();

        // Menangani tombol back
        handleOnBackPressed();

        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Set a WebViewClient to handle navigation events
        binding.webView.setWebViewClient(new WebViewClient());

        // Set a WebViewClient to handle navigation events
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });

        // Load the URL with POST data
        loadUrlWithPostData("https://app.pdamkotasmg.co.id/be-proxy/siramhpm/login/login-02-proses-api.php",
                "user=" + user + "&pass=" + pass);

        return root;
    }

    private void loadUrlWithPostData(String url, String postData) {
        byte[] postDataBytes = postData.getBytes();
        binding.webView.postUrl(url, postDataBytes);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void handleOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (binding.webView.canGoBack()) {
                            binding.webView.goBack();
                        } else {
                            if (isEnabled()) {
                                setEnabled(false);
                                requireActivity().onBackPressed();
                            }
                        }
                    }
                });
    }
}