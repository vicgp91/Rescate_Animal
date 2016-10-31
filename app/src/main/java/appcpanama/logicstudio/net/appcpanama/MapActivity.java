package appcpanama.logicstudio.net.appcpanama;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import appcpanama.logicstudio.net.appcpanama.Commons.MyLocationListener;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Controls
    Toolbar toolbar;
    //Map
    private supportMapFragment supportMapFragment;
    private GoogleMap mapa;
    FloatingActionButton floatingActionButton;
    private LocationManager mlocManager;
    private MyLocationListener mlocListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_screen);

        initInstance();
        assign();
    }

    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.sendMaps);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Toast.makeText(MapActivity.this, "", Toast.LENGTH_LONG).show();


                //finish();
            }
        });
    }

    private void assign() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.mapTitleVerUbicacion);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





    }


    @Override
    public void onMapReady(GoogleMap map) {

        Intent intent = getIntent();

        String activityOrigen = intent.getStringExtra("esReporte");

        if (activityOrigen.equalsIgnoreCase("esReporte")) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //map.setMyLocationEnabled(true);
                mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                mlocListener = new MyLocationListener();
                mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, mlocListener);
            }

        } else {
            LatLng panaMarker = new LatLng(9.042224, -79.481777);
            map.addMarker(new MarkerOptions()
                    .position(panaMarker)
                    .title("Panama"));

            map.addMarker(new MarkerOptions()
                    .position(new LatLng(8.377311, -81.642853))
                    .title("Panama"));
        }
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(new LatLng(8.526787, -80.182050))
                .zoom(6.6f)
                .build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

}
