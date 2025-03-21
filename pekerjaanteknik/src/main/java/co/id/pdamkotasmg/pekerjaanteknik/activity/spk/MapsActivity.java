package co.id.pdamkotasmg.pekerjaanteknik.activity.spk;

import android.Manifest;
import android.app.Activity;
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
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import co.id.pdamkotasmg.pekerjaanteknik.utils.Config;
import im.delight.android.location.SimpleLocation;

public class MapsActivity extends AppCompatActivity {

    private final String TAG = "debug";

    private GoogleMap mMap;
    private Double lati, longi;
    double latNew;
    double longiNew;
    private SimpleLocation location;
    private FusedLocationProviderClient mFusedLocation;

    private String address_gps;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;

    private LinearLayout divContainer;
    private TextView tvBottomAlamat;
    private Button btnSimpanMaps;
    private Button btnRefreshMaps;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().hide();
        initView();

        Config.header(ivHeaderBackArrow, ivHeaderInfo, tvHeaderJudul, MapsActivity.this, "Google Maps");

        getMaps();
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
        mFusedLocation = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocation.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                // Do it all with location
                Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                // Display in Toast
                lati = location.getLatitude();
                longi = location.getLongitude();
                Log.d(TAG, "lat: " + lati);
                Log.d(TAG, "long: " + longi);

                Log.d(TAG, "onCreate: " + lati + longi);

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.maps);

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
                                Toast.makeText(MapsActivity.this, "Alamat tidak ditemukan, input manual!", Toast.LENGTH_SHORT).show();
                            } else {
                                Geocoder geocoder;
                                List<Address> addressList = new ArrayList<>();
                                if (addressList == null) {
                                    Log.d("debug", "adress list : Null");
                                } else {
                                    geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
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

    private void initView() {
        divContainer = findViewById(R.id.div_container);
        tvBottomAlamat = findViewById(R.id.tv_bottom_alamat);
        btnSimpanMaps = findViewById(R.id.btn_simpan_maps);
        btnRefreshMaps = findViewById(R.id.btn_refresh_maps);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
    }
}