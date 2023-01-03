package id.pdamkotasmg.pekerjaan_teknik_feature.activity.spk;

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

import id.pdamkotasmg.pekerjaan_teknik_feature.R;
import id.pdamkotasmg.pekerjaan_teknik_feature.utils.Config;

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
        getSupportActionBar().hide();
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

                if (location != null || location.equals("")) {
                    progressDialog.cancel();
                    Geocoder geocoder = new Geocoder(MapsAdressActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList.size() == 0) {
                        progressDialog.cancel();
                        Toast.makeText(MapsAdressActivity.this, "Tidak ditemukan", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.cancel();
                        Address address = addressList.get(0);

                        if (addressList.isEmpty()) {
                            Toast.makeText(MapsAdressActivity.this, "Tidak ditemukan", Toast.LENGTH_SHORT).show();
                        } else {
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                            // on below line we are adding marker to that position.
//                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
//                    LatLng target = mMap.getCameraPosition().target;

                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                        }
                    }


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
                                Geocoder geocoder;
                                List<Address> addressList = new ArrayList<>();
                                if (addressList == null) {
                                    Log.d("debug", "adress list : Null");
                                } else {
                                    geocoder = new Geocoder(MapsAdressActivity.this, Locale.getDefault());
                                    try {
                                        addressList = geocoder.getFromLocation(latNew, longiNew, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                        address_gps = addressList.get(0).getAddressLine(0); // If any additional address_gps line present than only, check with max available address_gps lines by getMaxAddressLineIndex()
                                        if (address_gps == null || address_gps.isEmpty()) {
                                            address_gps = "alamat";
                                        }
                                        city = addressList.get(0).getLocality();
                                        if (city == null) {
                                            city = "kota";
                                        }
                                        state = addressList.get(0).getAdminArea();
                                        if (state == null) {
                                            state = ".";
                                        }
                                        country = addressList.get(0).getCountryName();
                                        if (country == null) {
                                            country = "negara";
                                        }
                                        postalCode = addressList.get(0).getPostalCode();
                                        if (postalCode == null) {
                                            postalCode = "postal";
                                        }
                                        knownName = addressList.get(0).getFeatureName(); // Only if available else return NULL
                                        if (knownName == null) {
                                            knownName = "name";
                                        }
                                        Log.d("debug", "loc: " + address_gps + " ");

                                        tvBottomAlamat.setText(address_gps);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
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