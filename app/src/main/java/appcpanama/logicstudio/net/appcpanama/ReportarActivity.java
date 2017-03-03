package appcpanama.logicstudio.net.appcpanama;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
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
import android.util.Base64;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.services.urlshortener.Urlshortener;

public class ReportarActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {


    private final int PERMISSION_ACCESS_COARSE_LOCATION = 8;
    private final int TAKE_PHOTO_CODE = 1;
    private final int CAMERA_PERMISSION_CODE = 102;
    private final int WRITE_PERMISSION_CODE = 105;
    Handler handler;
    double latitude;
    double longitude;
    Location location;
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
    Button btnReportar, btnAddUbicacion;
    EditText txvComoLLegar;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    SupportMapFragment mapFragment;
    ProgressDialog dialogLocation;
    Dialog dialog;
    private TextInputLayout inputLayoutCondicion, inputLayoutComoLLegar, input_layout_tipoAnimal;
    TextView labelUbicacion;
    GoogleMap map;
    Bitmap myBitmap;
    ImageView iv;
    LinearLayout lmap;
    LinearLayout lfoto;
    Uri PhotoAnimal;
    String encoded = null;

    String nombre = "android", correo = "correo@android.com", telefono = "999-9999", resumen = "reporte desde android", problema = "reporte android", extencion = "+507";


    public Runnable runLocation = new Runnable() {
        @Override
        public void run() {

            // Toast.makeText(ReportarActivity.this, "location check" +latitude +" " + longitude, Toast.LENGTH_SHORT).show();

            if (location != null) {
                labelUbicacion.append(+latitude + " " + longitude);


            }
        }
    };

    @Override
    protected void onStart() {
        //android.os.Process.killProcess(android.os.Process.myPid());

        super.onStart();


    }


    private void init() {

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public void getLocation() {


        boolean isGPSEnabled = false;

        handler = new Handler();

        if (ContextCompat.checkSelfPermission(ReportarActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            lmap.setVisibility(View.VISIBLE);

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            boolean network_enabled = false;
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ReportarActivity.this);

            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            dialogLocation = ProgressDialog.show(ReportarActivity.this, "",
                    "Obteniendo ubicación....", true);


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (dialogLocation != null)
                        dialogLocation.dismiss();
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        setmap(location);
                    } else {
                        Toast.makeText(ReportarActivity.this, "No se pudo obtener Ubicación", Toast.LENGTH_LONG).show();

                        if (ActivityCompat.checkSelfPermission(ReportarActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ReportarActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        locationManager.removeUpdates(ReportarActivity.this);

                    }

                }
            }, 15000);


        } else {
            ActivityCompat.requestPermissions(ReportarActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    8);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.ayudalosACasa);
        final Dialog dialog = new Dialog(this);
        btnAddUbicacion = (Button) findViewById(R.id.btnAddUbicacion);
        imgSelected = (ImageView) findViewById(R.id.imgSelected);
        txtSelect = (TextView) findViewById(R.id.textAnimalSelected);
        textCondicionSelected = (TextView) findViewById(R.id.textCondicionSelected);
        imgAnimal = (ImageView) findViewById(R.id.img_reportar_photo);
        selecAnimal = (Button) findViewById(R.id.seleccionarAnimal);
        btnCondicion = (Button) findViewById(R.id.btnTipoCondicion);
        takePicture = (Button) findViewById(R.id.btnAddFoto);
        btnReportar = (Button) findViewById(R.id.btnreportar);
        lmap = (LinearLayout) findViewById(R.id.maplayaout);
        lfoto = (LinearLayout) findViewById(R.id.lfoto);
        init();

        btnAddUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    getLocation();

                } catch (Exception e) {
                    e.printStackTrace();
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
                        txtSelect.setVisibility(View.VISIBLE);
                        imgSelected.setVisibility(View.VISIBLE);
                        imgSelected.setImageResource(img);
                        txtSelect.setText(texto);
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


                        // img = sp.fakeData().get(position).getImg();
                        // imgSelected.setImageResource(img);
                        textCondicionSelected.setVisibility(View.VISIBLE);
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
                if (isValidForm()) {

                    progressDialog = ProgressDialog.show(ReportarActivity.this, "",
                            "Se está enviando el reporte....", true);
                    new EnviarDatos(ReportarActivity.this).execute();
                    // }
                } else {

                    Toast.makeText(ReportarActivity.this, "Complete la información", Toast.LENGTH_SHORT).show();


                }


            }
        });


        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Entrando", "-------");

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

    private boolean validaTipoAnimal() {
        if (txtSelect.getText().toString().equalsIgnoreCase("")) {

            selecAnimal.setError("Selecciona tipo de animal");

            txtSelect.setText("Debes seleccionar un animal");
            txtSelect.setTextColor(Color.parseColor("#B71C1C"));

            return false;
        } else {
            ///  input_layout_tipoAnimal.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validaCondicionAnimal() {
        if (textCondicionSelected.getText().toString().equalsIgnoreCase("")) {

            btnCondicion.setError("Selecciona tipo de animal");

            textCondicionSelected.setText("Selecciona una condicion");
            textCondicionSelected.setTextColor(Color.parseColor("#B71C1C"));

            return false;
        } else {
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

    public boolean isValidForm() {

        if (txtSelect.getText() == "") {
            Toast.makeText(ReportarActivity.this, "Seleccione el Tipo de Animal", Toast.LENGTH_SHORT).show();
            return false;

        } else if (textCondicionSelected.getText() == "") {

            Toast.makeText(ReportarActivity.this, "Seleccione la condición en que está el animal", Toast.LENGTH_SHORT).show();
            return false;

        } else if (latitude == 0 || longitude == 0) {

            Toast.makeText(ReportarActivity.this, "Complete la Ubicación del Animal", Toast.LENGTH_SHORT).show();
            return false;

        } else if (encoded == null) {
            Toast.makeText(ReportarActivity.this, "Por favor tome la foto del animal", Toast.LENGTH_SHORT).show();
            return false;

        }

        return true;
    }

    /**
     * Compress image
     */
    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }


    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    /*****
     *
     *
     *
     *
     */




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
            lfoto.setVisibility(View.VISIBLE);

            try {

                String mImageNewPath = compressImage(PhotoAnimal.toString());

                // Sending side
                byte[] datab = mImageNewPath.getBytes("UTF-8");
                encoded = Base64.encodeToString(datab, Base64.DEFAULT);
                imgAnimal.setImageURI(Uri.parse(mImageNewPath));
                //encodeImage();
            } catch (Exception e) {

                e.printStackTrace();
            }




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
            case PERMISSION_ACCESS_COARSE_LOCATION:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                    getLocation();
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void setmap(Location location) {
        LatLng latlng = new LatLng(location.getLatitude(),
                location.getLongitude());

        CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(16f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

        if (map != null) {
            map.clear();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latlng);
            Marker marker = map.addMarker(markerOptions);
            map.moveCamera(cameraUpdate);

        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (dialogLocation != null)
            dialogLocation.dismiss();

        if (latitude != 0 && longitude != 0) {

            setmap(location);
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);


    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

    }


    //Metodo de envio de formulario


    class EnviarDatos extends AsyncTask<String, String, String> {
        private Activity context;

        EnviarDatos(Activity contex) {
            this.context = contex;
        }

        @Override
        protected String doInBackground(String... params) {

            if (registroReporte()) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "Datos enviados correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ResultScreen.class));

                        ReportarActivity.this.finish();
                    }
                });

            } else {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
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


    public Boolean registroReporte() {

        try {

            String attachmentName = "bitmap";
            String attachmentFileName = "bitmap.bmp";

            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            String path = PhotoAnimal.getPath();
            File sourceFile = new File(path);

            problema = "El siguiente animal reportado tipo: " + txtSelect.getText() + " con las siguientes condiciones " + textCondicionSelected.getText()
                    + " , localizado en " + "<a href=\"http://maps.google.com/?q=" + latitude + "," + longitude + "\">aqui </a>";
            FileInputStream fileInputStream = new FileInputStream(sourceFile);

            URL url = new URL("http://192.168.4.21:8088/rescateAnimal/api_ticket/generarTicket.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setDoInput(true); // Allow Inputs

            conn.setDoOutput(true); // Allow Outputs

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(twoHyphens + boundary + lineEnd);
            wr.writeBytes("Content-Disposition: form-data; name=\"nombre\"" + lineEnd);
            wr.writeBytes(lineEnd);
            wr.writeBytes(nombre);
            wr.writeBytes(lineEnd);

            wr.writeBytes(twoHyphens + boundary + lineEnd);
            wr.writeBytes("Content-Disposition: form-data; name=\"correo\"" + lineEnd);
            wr.writeBytes(lineEnd);
            wr.writeBytes(correo);
            wr.writeBytes(lineEnd);

            wr.writeBytes(twoHyphens + boundary + lineEnd);
            wr.writeBytes("Content-Disposition: form-data; name=\"telefono\"" + lineEnd);
            wr.writeBytes(lineEnd);
            wr.writeBytes(telefono);
            wr.writeBytes(lineEnd);

            wr.writeBytes(twoHyphens + boundary + lineEnd);
            wr.writeBytes("Content-Disposition: form-data; name=\"resumen\"" + lineEnd);
            wr.writeBytes(lineEnd);
            wr.writeBytes(resumen);
            wr.writeBytes(lineEnd);

            wr.writeBytes(twoHyphens + boundary + lineEnd);
            wr.writeBytes("Content-Disposition: form-data; name=\"problema\"" + lineEnd);
            wr.writeBytes(lineEnd);
            wr.writeBytes(problema);
            wr.writeBytes(lineEnd);


            wr.writeBytes(twoHyphens + boundary + lineEnd);
            wr.writeBytes("Content-Disposition: form-data; name=\"extencion\"" + lineEnd);
            wr.writeBytes(lineEnd);
            wr.writeBytes(extencion);
            wr.writeBytes(lineEnd);


            wr.writeBytes(twoHyphens + boundary + lineEnd);
            wr.writeBytes("Content-Disposition: form-data; name=\"sitio\"" + lineEnd);
            wr.writeBytes(lineEnd);
            wr.writeBytes("Android App");
            wr.writeBytes(lineEnd);

            String filename = path.substring(path.lastIndexOf("/") + 1);
            wr.writeBytes(twoHyphens + boundary + lineEnd);
            wr.writeBytes("Content-Disposition: form-data; name=\"attachments\";filename=\"" + filename + "\"" + lineEnd);
            wr.writeBytes(lineEnd);


            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                wr.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            wr.writeBytes(lineEnd);
            wr.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            fileInputStream.close();
            wr.flush();

            Log.e("File Sent, Response: ", String.valueOf(conn.getResponseCode()));
            InputStream is = conn.getInputStream();

            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            Log.i("Response", s);
            wr.close();
            is.close();


            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }


}
