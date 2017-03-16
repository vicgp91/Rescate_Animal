package appcpanama.logicstudio.net.appcpanama;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class PerfilScreen extends AppCompatActivity implements View.OnClickListener {

    //Controls
    ProgressDialog progressDialog;
    Integer response;
    Toolbar toolbar;
    LinearLayout btnActDatos,
            btnCambiarContr,
            btnLogOut;
    LinearLayout btnShare;
    TextView txtname, txtcorreo, txthuellas;
    String name, correo;
    Integer id;
    Integer lastcount;
    boolean getHuellas = false;
    public static final String PREFS_NAME = "RA_PREFS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_screen);
        SharedPreferences settings;
        settings = getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        SharedPreferences.Editor editor = settings.edit();
        name = settings.getString("name", null);
        correo = settings.getString("Correo", null);
        lastcount = settings.getInt("huella", 0);
        id = settings.getInt("id", 0);
        // Log.i("name", name);
        initInstance();
        assign();
    }

    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        btnActDatos = (LinearLayout) findViewById(R.id.lay_perfil_datos);
        btnCambiarContr = (LinearLayout) findViewById(R.id.lay_perfil_contrasena);
        btnLogOut = (LinearLayout) findViewById(R.id.lay_perfil_sesion);
        btnShare = (LinearLayout) findViewById(R.id.lay_compartir);
        txtname = (TextView) findViewById(R.id.idname);
        txtcorreo = (TextView) findViewById(R.id.correo);
        txthuellas = (TextView) findViewById(R.id.counthuellas);
        txtname.setText(name);
        txtcorreo.setText(correo);
        txthuellas.setText(String.valueOf(lastcount) + " Huellas");


    }


    private void assign() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.perfilTitle);

        btnActDatos.setOnClickListener(this);
        btnCambiarContr.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
        btnShare.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        int idView = v.getId();

        switch (idView) {

            case R.id.lay_perfil_datos:

                break;

            case R.id.lay_perfil_contrasena:

                break;

            case R.id.lay_perfil_sesion:
                cerrarSesion();
                break;
            case R.id.lay_compartir:
                shareContent();
                break;


            default:
                break;
        }
    }


    private void cerrarSesion(){
        new SPControl(getApplicationContext()).delete("logValue");

        Intent i = new Intent(this, IntroActivity.class );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }


    public void shareContent() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>Salva la vida de uan especie, descarga Rescate Animal </p>"));
        startActivity(Intent.createChooser(sharingIntent, "Share using"));

    }


    class EnviarDatos extends AsyncTask<String, String, String> {
        private Activity context;

        EnviarDatos(Activity contex) {
            this.context = contex;
        }

        @Override
        protected String doInBackground(String... params) {

            if (consultarHuellas()) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        txthuellas.setText(String.valueOf(response) + " Huellas");

                        getHuellas = true;
                    }
                });

            } else {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No se pudo consultar huellas", Toast.LENGTH_LONG).show();

                        txthuellas.setText(lastcount);

                    }
                });
            }


            return null;
        }

    }


    public Boolean consultarHuellas() {
        try {
            URL url = new URL("http://192.168.4.21:8088/rescateAnimal/api_ticket/validate_user.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            conn.setDoInput(true);
            String urlParameters = "codigo=" + 1 + "&id=" + id + "";
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlParameters);

            Log.i(" Sent, Response: ", String.valueOf(conn.getResponseCode()));
            InputStream is = conn.getInputStream();

            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            if (conn.getResponseCode() == 200) {
                String server_response = b.toString();
                Log.i("Message", server_response);
                try {
                    JSONObject reader = new JSONObject(server_response);
                    String error = reader.getString("error");

                    if (error.equals("")) {
                        response = reader.getInt("huella_user");
                    } else {
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                        return false;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Log.e("Server response", "Failed to get server response");
                return false;

            }

            wr.close();
            is.close();

            return true;


        } catch (Exception e) {

        }
        return false;
    }

}
