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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;
import appcpanama.logicstudio.net.appcpanama.JavaBeans.UsuarioInfoBeans;

public class LoginScreen extends AppCompatActivity {

    //Clases
    SPControl control;

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
                    dialog = ProgressDialog.show(LoginScreen.this, "",
                            "Procesando Solicitud....", true);
                    new EnviarDatos(LoginScreen.this).execute();
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
                       startActivity(new Intent(LoginScreen.this, HomeScreen.class));
                       finish();

                      //  Toast.makeText(getApplicationContext(), "Datos enviados correctamente", Toast.LENGTH_SHORT).show();
                        user.setText("");
                        pass.setText("");
                        dialog.cancel();
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



    public Boolean registroUsuario() {
        //try {
            String userName, passW;
            userName = user.getText().toString();
            passW = pass.getText().toString();

            UsuarioInfoBeans usuario = new UsuarioInfoBeans();
            usuario.setUserName("logic01");
            usuario.setPassword("123456");
            usuario.setLastLogin(new Date());
            usuario.setNombreCompleto("Logic Studio");
            usuario.setCorreoElectronico("panama@logicstudio.net");


            if (userName.trim().equalsIgnoreCase(usuario.getCorreoElectronico().trim())
                    && passW.trim().equalsIgnoreCase(usuario.getPassword().trim())) {
       return true;

            } else {
        return false;
            }
/*
            //if(tieneRed) {

                URL url = new URL("http://10.10.10.16:8081/rescateanimal/serviceRescate/login");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                conn.setDoInput(true);
                String urlParameters = "userName=" + userName + "&password=" + passW + "";
                conn.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
                int responseCode = conn.getResponseCode();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));

                in.close();
            //}
            return true;
       }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return false;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
*/
    }



}
