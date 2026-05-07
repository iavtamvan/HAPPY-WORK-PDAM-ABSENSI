package co.id.pdamkotasmg.local;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Utility untuk cek koneksi internet device.
 *
 * Pakai dual-API: NetworkCapabilities (API 23+) + NetworkInfo (legacy)
 * supaya tetap bekerja di Android 5.0-5.1 yang masih banyak dipakai
 * petugas lapangan PDAM.
 *
 * Contoh penggunaan:
 *   if (NetworkUtil.isOnline(context)) {
 *       // boleh hit API
 *   } else {
 *       // save ke local saja
 *   }
 */
public class NetworkUtil {

    private NetworkUtil() {
        // util class — no instance
    }

    /**
     * @return true jika device punya koneksi aktif (cellular / wifi / ethernet).
     *         false jika airplane mode, no signal, atau wifi tanpa internet.
     */
    public static boolean isOnline(Context context) {
        if (context == null) return false;

        ConnectivityManager cm = (ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return isOnlineModern(cm);
        } else {
            return isOnlineLegacy(cm);
        }
    }

    private static boolean isOnlineModern(ConnectivityManager cm) {
        Network network = cm.getActiveNetwork();
        if (network == null) return false;

        NetworkCapabilities caps = cm.getNetworkCapabilities(network);
        if (caps == null) return false;

        // Punya internet capability + memang divalidasi (artinya bukan captive portal)
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }

    @SuppressWarnings("deprecation")
    private static boolean isOnlineLegacy(ConnectivityManager cm) {
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * @return label tipe koneksi: "WiFi", "Cellular", "Ethernet", atau "Offline".
     *         Berguna untuk debugging atau ditampilkan di UI.
     */
    public static String getConnectionLabel(Context context) {
        if (context == null) return "Offline";

        ConnectivityManager cm = (ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return "Offline";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = cm.getActiveNetwork();
            if (network == null) return "Offline";
            NetworkCapabilities caps = cm.getNetworkCapabilities(network);
            if (caps == null) return "Offline";
            if (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return "WiFi";
            if (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return "Cellular";
            if (caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) return "Ethernet";
            return "Online";
        } else {
            @SuppressWarnings("deprecation")
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null || !info.isConnected()) return "Offline";
            int type = info.getType();
            if (type == ConnectivityManager.TYPE_WIFI) return "WiFi";
            if (type == ConnectivityManager.TYPE_MOBILE) return "Cellular";
            if (type == ConnectivityManager.TYPE_ETHERNET) return "Ethernet";
            return "Online";
        }
    }
}