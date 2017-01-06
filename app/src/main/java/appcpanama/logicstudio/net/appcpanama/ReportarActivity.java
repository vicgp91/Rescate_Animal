package appcpanama.logicstudio.net.appcpanama;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
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
import android.util.Log;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import appcpanama.logicstudio.net.appcpanama.Adapters.CondicionAnimalAdapter;
import appcpanama.logicstudio.net.appcpanama.Adapters.ListAnimalAdapter;
import appcpanama.logicstudio.net.appcpanama.Commons.MaterialDialogClass;
import appcpanama.logicstudio.net.appcpanama.Commons.PhotoClass;
import appcpanama.logicstudio.net.appcpanama.Adapters.infoAnimalAdapter;
import appcpanama.logicstudio.net.appcpanama.Commons.MyLocationListener;
import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;
import appcpanama.logicstudio.net.appcpanama.Commons.SimpleDividerItemDecoration;

public class ReportarActivity extends AppCompatActivity implements LocationListener {


    private final int TAKE_PHOTO_CODE = 1;
    private final int CAMERA_PERMISSION_CODE = 102;
    private final int WRITE_PERMISSION_CODE = 105;

    //Controls
    Toolbar toolbar;
    ProgressDialog progressDialog;
    RecyclerView rclrList;
    ListAnimalAdapter adapter;
    CondicionAnimalAdapter adapaterCondicion;
    RecyclerView.LayoutManager layoutManager;
    ImageView imgSelected;
    TextView txtSelect, textCondicionSelected;
    ImageView imgAnimal;
    Button selecAnimal, btnCondicion;
    Button takePicture;
    EditText txtCondicion;
    Button btnReportar,btnAddUbicacion;
    EditText txvComoLLegar;
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    ProgressDialog dialogLocation;
    Dialog dialog;
    private TextInputLayout inputLayoutCondicion,inputLayoutComoLLegar, input_layout_tipoAnimal ;


    Uri PhotoAnimal;


    String nombre="android", correo="correo@android.com", telefono="999-9999", resumen="reporte desde android", problema="reporte android", extencion="+507";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Dialog dialog = new Dialog(this);

        txvComoLLegar = (EditText) findViewById(R.id.txtComoLLegar);
        btnAddUbicacion=(Button)findViewById(R.id.btnAddUbicacion);

        imgSelected = (ImageView) findViewById(R.id.imgSelected);
        txtSelect = (TextView) findViewById(R.id.textAnimalSelected);

        textCondicionSelected=(TextView) findViewById(R.id.textCondicionSelected);
        imgAnimal = (ImageView) findViewById(R.id.img_reportar_photo);
        selecAnimal = (Button) findViewById(R.id.seleccionarAnimal);
        btnCondicion=(Button)findViewById(R.id.btnTipoCondicion);
        takePicture = (Button) findViewById(R.id.btnAddFoto);
        btnReportar = (Button) findViewById(R.id.btnreportar);
        txtCondicion = (EditText)findViewById(R.id.txtCondicion);

        inputLayoutCondicion= (TextInputLayout) findViewById(R.id.input_layout_condicion);

        inputLayoutComoLLegar=(TextInputLayout) findViewById(R.id.input_layout_referencia);




        btnAddUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ReportarActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, ReportarActivity.this);
                    dialogLocation = ProgressDialog.show(ReportarActivity.this, "",
                            "Obteniendo ubicación....", true);
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

                adapter.setOnItemClickListener(new ListAnimalAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {

                        SPControl sp = new SPControl(ReportarActivity.this);
                        String texto;
                        Integer img;
                        texto = sp.fakeData().get(position).getNombre();
                        img = sp.fakeData().get(position).getImg();
                        imgSelected.setImageResource(img);
                        txtSelect.setText(texto);
                        resumen="Reporte de un "+texto;
                        txtSelect.setTextColor(Color.parseColor("#348839"));
                        selecAnimal.setText("Presiona para cambiar");
                        dialog.cancel();
                    }
                });

            }
        });






        btnCondicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPControl sp = new SPControl(ReportarActivity.this);

                dialog.setTitle("Condición del Animal");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.animal_list);
                rclrList = (RecyclerView) dialog.findViewById(R.id.rclr_infoanimal_list);

                adapaterCondicion = new CondicionAnimalAdapter(sp.fakeDataCondicion());
                layoutManager = new LinearLayoutManager(getApplicationContext());
                rclrList.setLayoutManager(layoutManager);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                rclrList.addItemDecoration(new SimpleDividerItemDecoration(ReportarActivity.this, (int) metrics.density * 90));
                rclrList.setAdapter(adapaterCondicion);
                dialog.show();

                adapaterCondicion.setOnItemClickListener(new CondicionAnimalAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {

                        SPControl sp = new SPControl(ReportarActivity.this);
                        String texto;
                        Integer img;
                        texto = sp.fakeDataCondicion().get(position).getNombre();

                        problema = "\nEl animal reportado se encuentra en la siguiente condicion: "+texto;


                       // img = sp.fakeData().get(position).getImg();
                       // imgSelected.setImageResource(img);
                        textCondicionSelected.setText(texto);
                        textCondicionSelected.setTextColor(Color.parseColor("#348839"));
                        btnCondicion.setText("Presiona para cambiar");
                        dialog.cancel();
                    }
                });

            }
        });


        btnReportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if(!validaTipoAnimal() || !validaCondicionAnimal() ||  !validaCondicion()  || !validaReferencia()){

                //}else{

                    progressDialog = ProgressDialog.show(ReportarActivity.this, "",
                            "Procesando Solicitud....", true);
                    new EnviarDatos(ReportarActivity.this).execute();
                   // startActivity(new Intent(ReportarActivity.this, FinalReport.class));
                   // finish();
               // }


            }
        });


        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    photoPermission();
                } else {
                    callCamera();
                }
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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validaTipoAnimal(){
        if(txtSelect.getText().toString().equalsIgnoreCase("")){

            selecAnimal.setError("Selecciona tipo de animal");

            txtSelect.setText("Debes seleccionar un animal");
            txtSelect.setTextColor(Color.parseColor("#B71C1C"));

            return false;
        }else{
          ///  input_layout_tipoAnimal.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validaCondicionAnimal(){
        if(textCondicionSelected.getText().toString().equalsIgnoreCase("")){

            btnCondicion.setError("Selecciona tipo de animal");

            textCondicionSelected.setText("Selecciona una condicion");
            textCondicionSelected.setTextColor(Color.parseColor("#B71C1C"));

            return false;
        }else{
            ///  input_layout_tipoAnimal.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validaCondicion() {
        if (txtCondicion.getText().toString().trim().isEmpty()) {
            txtCondicion.setError("Completa este campo");
            requestFocus(txtCondicion);
            return false;
        } else {
            inputLayoutCondicion.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validaReferencia() {
        if (txvComoLLegar.getText().toString().trim().isEmpty()) {
            txvComoLLegar.setError("Agrega una referencia");
            requestFocus(txvComoLLegar);
            return false;
        } else {
            inputLayoutComoLLegar.setErrorEnabled(false);
        }

        return true;
    }



    //CALL CAMERA
    private void callCamera() {
        PhotoClass photoClass = new PhotoClass();
        Uri uriPhoto = photoClass.takePicture(ReportarActivity.this);
        if (uriPhoto != null) {

            PhotoAnimal = uriPhoto;

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto);
            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);

        } else {
            Toast.makeText(ReportarActivity.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            imgAnimal.setVisibility(View.VISIBLE);
            imgAnimal.setImageURI(PhotoAnimal);
        }
    }


    //PERMISO DE FOTO
    private void photoPermission() {

        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            writePermission();
        } else {
            ActivityCompat.requestPermissions(ReportarActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
        }
    }

    //PERMISO DE ESCRITURA
    private void writePermission() {

        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            callCamera();
        } else {
            ActivityCompat.requestPermissions(ReportarActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_PERMISSION_CODE);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case CAMERA_PERMISSION_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    writePermission();
                } else {
                    MaterialDialogClass.createOKDialog(ReportarActivity.this,
                            getString(R.string.errorTituloPermiso),
                            getString(R.string.errorDescCamera));
                }
                break;

            case WRITE_PERMISSION_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callCamera();
                } else {
                    MaterialDialogClass.createOKDialog(ReportarActivity.this,
                            getString(R.string.errorTituloPermiso),
                            getString(R.string.errorDescWrite));
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        dialogLocation.cancel();
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(), Toast.LENGTH_SHORT);
        toast1.show();


    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }








    //Metodo de envio de formulario


    class EnviarDatos extends AsyncTask<String,String,String> {
        private Activity context;
        EnviarDatos(Activity contex){
            this.context=contex;
        }

        @Override
        protected  String doInBackground(String... params){
            if(registroReporte()){
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Datos enviados correctamente", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();

                    }
                });
            }
            else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Datos no enviados", Toast.LENGTH_SHORT).show();

                    }
                });
            }



            return null;
        }

    }



    public Boolean registroReporte(){
        try {


            problema="\nMas informacion acerca de la condicion: "+txtCondicion.getText().toString();

            problema = problema +  "\nLa informacion detallada para llegar es: "+ txvComoLLegar.getText().toString();



               URL url = new URL("http://192.168.3.24:81/apiOs/generarTicket.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                 //conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
               conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                conn.setDoInput(true);
                String urlParameters = "nombre=" + nombre + "&correo=" + correo + "&telefono=" + telefono + "&resumen=" + resumen + "&problema=" + problema + "&extencion=" + extencion + "&sitio=Android App"+ "";
                conn.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                 wr.close();
                int responseCode = conn.getResponseCode();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));

                in.close();




            return true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }














}
