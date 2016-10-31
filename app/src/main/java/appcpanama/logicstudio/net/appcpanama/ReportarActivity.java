package appcpanama.logicstudio.net.appcpanama;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import appcpanama.logicstudio.net.appcpanama.Adapters.ListAnimalAdapter;
import appcpanama.logicstudio.net.appcpanama.Adapters.infoAnimalAdapter;
import appcpanama.logicstudio.net.appcpanama.Commons.MyLocationListener;
import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;
import appcpanama.logicstudio.net.appcpanama.Commons.SimpleDividerItemDecoration;

public class ReportarActivity extends AppCompatActivity {

    //Controls
    Toolbar toolbar;
    RecyclerView rclrList;
    ListAnimalAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ImageView imgSelected;
    TextView txtSelect;
    Button selecAnimal;
    Button btnReportar,btnAddUbicacion;
    EditText txvComoLLegar;
    private LocationManager mlocManager;
    private MyLocationListener mlocListener;
    ProgressDialog dialogLocation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Dialog dialog = new Dialog(this);

        txvComoLLegar=(EditText) findViewById(R.id.txtComoLLegar);
        txvComoLLegar.addTextChangedListener(new MyTextWatcher(txvComoLLegar));
        btnAddUbicacion=(Button)findViewById(R.id.btnAddUbicacion);


        imgSelected =(ImageView) findViewById(R.id.imgSelected);
        txtSelect = (TextView)findViewById(R.id.textAnimalSelected);
        selecAnimal = (Button) findViewById(R.id.seleccionarAnimal);
        btnReportar = (Button) findViewById(R.id.btnreportar);

        btnAddUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(ReportarActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    mlocListener = new MyLocationListener();
                    mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, mlocListener);

                    dialogLocation = ProgressDialog.show(ReportarActivity.this, "",
                            "Procesando Solicitud....", true);

                    mlocListener.setLocationCallback(new MyLocationListener.LocationCallback() {
                        @Override
                        public void locationChange(Location location) {
                            dialogLocation.cancel();
                        }
                    });

                }
                /*Intent intent = new Intent(ReportarActivity.this, MapActivity.class);
                intent.putExtra("esReporte", "esReporte");
                startActivity(intent);*/

            }
        });




        selecAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPControl sp = new SPControl(ReportarActivity.this);

                dialog.setTitle("Selecciona un animal");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.animal_list);
                rclrList = (RecyclerView) dialog.findViewById(R.id.rclr_infoanimal_list);

                adapter = new ListAnimalAdapter(sp.fakeData());
                layoutManager = new LinearLayoutManager(getApplicationContext());
                rclrList.setLayoutManager(layoutManager);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                rclrList.addItemDecoration(new SimpleDividerItemDecoration(ReportarActivity.this, (int) metrics.density * 90));
                rclrList.setAdapter(adapter);
                dialog.show();

                adapter.setOnItemClickListener(new ListAnimalAdapter.ClickListener(){
                    @Override
                    public void onItemClick(int position, View v) {

                        SPControl sp = new SPControl(ReportarActivity.this);
                        String texto;
                        Integer img;
                        texto= sp.fakeData().get(position).getNombre();
                        img=sp.fakeData().get(position).getImg();
                        imgSelected.setImageResource(img);
                        txtSelect.setText(texto);
                        selecAnimal.setText("Presiona para cambiar");
                        dialog.cancel();
                    }
                });

            }
        });


        btnReportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportarActivity.this, FinalReport.class));
                finish();
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.txtComoLLegar:
                    // validateName();
                    break;

            }
        }
    }

}
