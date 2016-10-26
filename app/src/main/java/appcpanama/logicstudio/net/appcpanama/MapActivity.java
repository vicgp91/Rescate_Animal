package appcpanama.logicstudio.net.appcpanama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Controls
    Toolbar toolbar;

    //Map
    private supportMapFragment supportMapFragment;
    private GoogleMap mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_screen);

        initInstance();
        assign();
    }

    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        LatLng panaMarker = new LatLng(9.042224, -79.481777);
        map.addMarker(new MarkerOptions()
                .position(panaMarker)
                .title("Panama"));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(8.377311, -81.642853))
                .title("Panama"));

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
