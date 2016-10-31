package appcpanama.logicstudio.net.appcpanama;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import appcpanama.logicstudio.net.appcpanama.Adapters.ListAnimalAdapter;
import appcpanama.logicstudio.net.appcpanama.Commons.MaterialDialogClass;
import appcpanama.logicstudio.net.appcpanama.Commons.PhotoClass;
import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;
import appcpanama.logicstudio.net.appcpanama.Commons.SimpleDividerItemDecoration;

public class ReportarActivity extends AppCompatActivity {


    private final int TAKE_PHOTO_CODE = 1;
    private final int CAMERA_PERMISSION_CODE = 102;
    private final int WRITE_PERMISSION_CODE = 105;
    //Controls
    Toolbar toolbar;
    RecyclerView rclrList;
    ListAnimalAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ImageView imgSelected;
    TextView txtSelect;
    ImageView imgAnimal;
    Button selecAnimal;
    Button takePicture;
    Button btnReportar;
    EditText txvComoLLegar;

    Uri PhotoAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Dialog dialog = new Dialog(this);

        txvComoLLegar = (EditText) findViewById(R.id.txtComoLLegar);
        txvComoLLegar.addTextChangedListener(new MyTextWatcher(txvComoLLegar));


        imgSelected = (ImageView) findViewById(R.id.imgSelected);
        txtSelect = (TextView) findViewById(R.id.textAnimalSelected);
        imgAnimal = (ImageView) findViewById(R.id.img_reportar_photo);
        selecAnimal = (Button) findViewById(R.id.seleccionarAnimal);
        takePicture = (Button) findViewById(R.id.btnAddFoto);
        btnReportar = (Button) findViewById(R.id.btnreportar);

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

}
