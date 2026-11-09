package co.id.pdamkotasmg.pekerjaanteknik.activity.spk;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import com.pdamkotasmg.goodday.utils.Config;

public class MapsAdressActivity extends AppCompatActivity implements OnMapReadyCallback {
    private final String TAG = "debug";

    private SupportMapFragment mapFragment;

    private GoogleMap mMap;
    SearchView searchView;
    private Double lati, longi;
    double latNew;
    double longiNew;
    private String address_gps;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;

    private FusedLocationProviderClient mFusedLocation;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;
    private LinearLayout divContainer;
    private ConstraintLayout containerLayout;
    private TextView tvBottomAlamat;
    private Button btnSimpanMaps;
    private Button btnRefreshMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_adress);
        
        initView();

        Config.header(ivHeaderBackArrow, ivHeaderInfo, tvHeaderJudul, MapsAdressActivity.this, "Google Maps");

        getMaps();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ProgressDialog progressDialog = new ProgressDialog(MapsAdressActivity.this);
                progressDialog.setMessage("Mohon tunggu...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                String location = searchView.getQuery().toString();

                List<Address> addressList = null;

                // Dulu memakai "location != null || location.equals("")" yang justru
                // meloloskan teks kosong, lalu addressList dipakai tanpa penjagaan
                // padahal tetap null bila pencarian gagal atau tidak ada koneksi.
                if (location != null && !location.trim().isEmpty()) {
                    Geocoder geocoder = new Geocoder(MapsAdressActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    progressDialog.cancel();

                    if (addressList == null || addressList.isEmpty()) {
                        Toast.makeText(MapsAdressActivity.this, "Alamat tidak ditemukan", Toast.LENGTH_SHORT).show();
                    } else if (mMap == null) {
                        Toast.makeText(MapsAdressActivity.this, "Peta belum siap, coba lagi", Toast.LENGTH_SHORT).show();
                    } else {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                    }
                } else {
                    progressDialog.cancel();
                    Toast.makeText(MapsAdressActivity.this, "Isi dulu alamat yang dicari", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);

        btnSimpanMaps.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("alamat", address_gps);
            returnIntent.putExtra("kode_pos", postalCode);
            returnIntent.putExtra("lat", latNew);
            returnIntent.putExtra("long", longiNew);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();

        });

        btnRefreshMaps.setOnClickListener(v -> {
            getMaps();
        });
    }

    private void getMaps() {
        mFusedLocation = LocationServices.getFusedLocationProviderClient(MapsAdressActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocation.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                lati = location.getLatitude();
                longi = location.getLongitude();
                Log.d(TAG, "lat: " + lati);
                Log.d(TAG, "long: " + longi);

                Log.d(TAG, "onCreate: " + lati + longi);

                mapFragment.getMapAsync(googleMap -> {
                    mMap = googleMap;
                    LatLng sydney = new LatLng(lati, longi);
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    LatLng oldPosition = mMap.getCameraPosition().target;

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(sydney)      // Sets the center of the map to Mountain View
                            .zoom(17)                   // Sets the zoom
                            .bearing(0)                // Sets the orientation of the camera to east
                            .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    mMap.setOnCameraMoveStartedListener(i -> {

                    });

                    mMap.setOnCameraIdleListener(() -> {
                        LatLng newPosition = mMap.getCameraPosition().target;
                        if (newPosition != oldPosition) {
                            Log.d(TAG, "if: " + newPosition);
                            latNew = newPosition.latitude;
                            longiNew = newPosition.longitude;

                            Log.d(TAG, "lati new : " + latNew + " Longi : " + longiNew);
                            if (latNew == 0.0 || longiNew == 0.0) {
                                Toast.makeText(MapsAdressActivity.this, "Alamat tidak ditemukan, input manual!", Toast.LENGTH_SHORT).show();
                            } else {
                                // Pengecekan lama "addressList == null" atas ArrayList yang baru
                                // dibuat tidak pernah bernilai benar, sementara hasil Geocoder
                                // bisa kosong sehingga get(0) menutup aplikasi. Titik di laut
                                // atau tanpa koneksi adalah kejadian biasa di lapangan.
                                Geocoder geocoder = new Geocoder(MapsAdressActivity.this, Locale.getDefault());
                                try {
                                    List<Address> addressList = geocoder.getFromLocation(latNew, longiNew, 1);
                                    if (addressList == null || addressList.isEmpty()) {
                                        Log.d("debug", "adress list : kosong");
                                    } else {
                                        Address alamat = addressList.get(0);
                                        address_gps = alamat.getAddressLine(0);
                                        if (address_gps == null || address_gps.isEmpty()) {
                                            address_gps = "alamat";
                                        }
                                        city = alamat.getLocality() == null ? "kota" : alamat.getLocality();
                                        state = alamat.getAdminArea() == null ? "." : alamat.getAdminArea();
                                        country = alamat.getCountryName() == null ? "negara" : alamat.getCountryName();
                                        postalCode = alamat.getPostalCode() == null ? "postal" : alamat.getPostalCode();
                                        knownName = alamat.getFeatureName() == null ? "name" : alamat.getFeatureName();

                                        tvBottomAlamat.setText(address_gps);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                });

            }

        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
        divContainer = findViewById(R.id.div_container);
        containerLayout = findViewById(R.id.container_layout);
        tvBottomAlamat = findViewById(R.id.tv_bottom_alamat);
        btnSimpanMaps = findViewById(R.id.btn_simpan_maps);
        btnRefreshMaps = findViewById(R.id.btn_refresh_maps);
        searchView = findViewById(R.id.idSearchView);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }
}