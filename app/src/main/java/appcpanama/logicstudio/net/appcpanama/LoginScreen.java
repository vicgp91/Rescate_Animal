package appcpanama.logicstudio.net.appcpanama;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;
import appcpanama.logicstudio.net.appcpanama.JavaBeans.UsuarioInfoBeans;

public class LoginScreen extends AppCompatActivity {

    //Clases
    SPControl control;
    public static final String PREFS_NAME = "RA_PREFS";

    String server_response = "{}";
    //Controls
    Button btnLogin,
            btnRegister;

    private EditText user, pass;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        initInstance();
        assign();
    }

    public void savePrererencias(Integer id, String pass, String name, String phone, String Correo, Integer huella) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit();
        editor.putInt("id", id); // Storing boolean - true/false
        editor.putString("name", name); // Storing boolean - true/false
        editor.putString("phone", phone); // Storing string
        editor.putString("pass", pass); // Storing integer
        editor.putString("Correo", Correo); // Storing float
        editor.putInt("huella", huella); // Storing long
        editor.commit(); // commit changes
    }



    private void initInstance() {
        btnLogin = (Button) findViewById(R.id.btn_login_sesion);
        btnRegister = (Button) findViewById(R.id.btn_login_registrar);
        user = (EditText) findViewById(R.id.edt_login_nombre);
        pass=(EditText) findViewById(R.id.edt_login_contra);



        control = new SPControl(getApplicationContext());
    }


    private void assign() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if(!isOnline(LoginScreen.this)){
                    dialog();
                }
                else{*/

                if (!user.getText().toString().equals("") && !pass.getText().toString().equals("")) {
                    dialog = ProgressDialog.show(LoginScreen.this, "",
                            "Procesando Solicitud....", true);
                    new EnviarDatos(LoginScreen.this).execute();

                } else {

                    Toast.makeText(getApplicationContext(), "Complete la información requerida.", Toast.LENGTH_SHORT).show();

                }
               // }

                //control.setStringValue("logValue", "loginAccedido");
                //startActivity(new Intent(LoginScreen.this, HomeScreen.class));
                //finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginScreen.this, IntroActivity.class));
        super.onBackPressed();
    }




    class EnviarDatos extends AsyncTask<String,String,String> {
        private Activity context;
        EnviarDatos(Activity contex){
            this.context=contex;
        }

        @Override
        protected  String doInBackground(String... params){

            if(registroUsuario()){
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        control.setStringValue("logValue", "loginAccedido");
                        dialog.cancel();

                        try {
                            JSONObject reader = new JSONObject(server_response);
                            String error = reader.getString("error");

                            if (error.equals("")) {
                                savePrererencias(reader.getInt("id"), pass.getText().toString(), reader.getString("name"),
                                        reader.getString("phone_user"), user.getText().toString(),
                                        reader.getInt("huella_user"));
                                startActivity(new Intent(LoginScreen.this, HomeScreen.class));
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Se ha Producido un error al enviar, intente nuevamente", Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                    }
                });
            }



            return null;
        }

    }

    public  void dialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                LoginScreen.this);
        alertDialogBuilder.setTitle("Sin Conexión");
        alertDialogBuilder.setMessage("No está conectado a internet").setCancelable(false).
                setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    };

    private boolean isOnline(Context context) {
        boolean isInternetAvailable = false;
        try
        {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if(networkInfo != null && (networkInfo.isConnected()))
            {
                isInternetAvailable  = true;
            }
        }
        catch(Exception exception)
        {

        }

        return isInternetAvailable;
    }

    public String sha256(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(s.getBytes("UTF-8"));
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            return String.format("%064x", new java.math.BigInteger(1, messageDigest));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Boolean registroUsuario() {
        try {
            String userName, passW;
            userName = user.getText().toString();
            passW = pass.getText().toString();

            String passencryp = sha256(passW);
            Log.i("encryp", passencryp);
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int maxBufferSize = 1 * 1024 * 1024;
            URL url = new URL("http://192.168.4.21:8088/rescateAnimal/api_ticket/validate_user.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            conn.setDoInput(true);
            String urlParameters = "codigo=" + 0 + "&correo=" + userName + "&pass=" + passencryp + "";
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
                server_response = b.toString();
                Log.i("Message", server_response);
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
