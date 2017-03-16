package appcpanama.logicstudio.net.appcpanama;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appcpanama.logicstudio.net.appcpanama.Commons.MaterialDialogClass;


public class RegisterScreen extends AppCompatActivity {

    //Controls
    Button btnLogin;
    Button btnRegister;
    EditText edtnombre, edtcorre, edttel, edtpass, edtpassr;
    ProgressDialog progressDialog;
    String server_response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        initInstance();
        assign();
    }

    private void initInstance() {
        btnRegister = (Button) findViewById(R.id.btn_register_registrar);
        btnLogin = (Button) findViewById(R.id.btn_registro_login);
        edtnombre = (EditText) findViewById(R.id.edt_register_nombre);
        edtcorre = (EditText) findViewById(R.id.edt_register_email);
        edttel = (EditText) findViewById(R.id.edt_register_phone);
        edtpass = (EditText) findViewById(R.id.edt_register_contra);
        edtpassr = (EditText) findViewById(R.id.edt_register_contrar);
    }

    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    public boolean isValidForm() {
        String passr = edtpassr.getText().toString().trim();
        String pass = edtpass.getText().toString().trim();
        Log.i("correo", pass + "/" + passr);
        if (edtnombre.getText().toString().trim().isEmpty() || edtnombre.getText().toString().trim().length() == 0 || edtnombre.getText().toString().trim().equals("") || edtnombre.getText().toString().trim() == null) {
            Toast.makeText(RegisterScreen.this, "Por favor introduzca su nombre", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtcorre.getText().toString().trim().isEmpty() || edtcorre.getText().toString().trim().length() == 0 || edtcorre.getText().toString().trim().equals("") || edtcorre.getText().toString().trim() == null) {
            Toast.makeText(RegisterScreen.this, "Por favor introduzca su correo", Toast.LENGTH_SHORT).show();
            return false;

        } else if (isValidEmailAddress(edtcorre.getText().toString()) == false) {
            Toast.makeText(RegisterScreen.this, "Correo Invalido", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edttel.getText().toString().trim().isEmpty() || edttel.getText().toString().trim().length() == 0 || edttel.getText().toString().trim().equals("") || edttel.getText().toString().trim() == null) {
            Toast.makeText(RegisterScreen.this, "Por favor introduzca su  teléfono", Toast.LENGTH_SHORT).show();
            return false;

        } else if (edtpass.getText().toString().trim().isEmpty() || edtpass.getText().toString().trim().length() == 0 || edtpass.getText().toString().trim().equals("") || edtpass.getText().toString().trim() == null) {
            Toast.makeText(RegisterScreen.this, "Por favor introduzca su  contraseña", Toast.LENGTH_SHORT).show();
            return false;

        } else if (edtpassr.getText().toString().trim().isEmpty() || edtpassr.getText().toString().trim().length() == 0 || edtpassr.getText().toString().trim().equals("") || edtpassr.getText().toString().trim() == null) {
            Toast.makeText(RegisterScreen.this, "Por favor repita su  contraseña", Toast.LENGTH_SHORT).show();
            return false;

        } else if (pass.equals(passr) == false) {
            Toast.makeText(RegisterScreen.this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;

        }


        return true;
    }

    private void assign() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isValidForm()) {
                    progressDialog = ProgressDialog.show(RegisterScreen.this, "",
                            "Se está  creando su cuenta...", true);
                    new RegisterScreen.EnviarDatos(RegisterScreen.this).execute();


                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterScreen.this, IntroActivity.class));
        super.onBackPressed();
    }


    class EnviarDatos extends AsyncTask<String, String, String> {
        private Activity context;

        EnviarDatos(Activity contex) {
            this.context = contex;
        }

        @Override
        protected String doInBackground(String... params) {

            if (registrarCuenta()) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Log.i("Message", server_response);
                        try {
                            JSONObject reader = new JSONObject(server_response);
                            String error = reader.getString("error");

                            if (error.equals("")) {

                                Intent loginIntent = new Intent(RegisterScreen.this, LoginScreen.class);
                                startActivity(loginIntent);
                                Toast.makeText(getApplicationContext(), "Se ha creado su cuenta con éxito", Toast.LENGTH_LONG).show();

                                finish();


                            } else {
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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


                    }
                });
            }


            return null;
        }

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

    public Boolean registrarCuenta() {
        try {
            String n = edtnombre.getText().toString();
            String c = edtcorre.getText().toString();
            String p = edtpass.getText().toString();
            String hash = sha256(p);
            String t = edttel.getText().toString().trim();
            URL url = new URL("http://192.168.4.21:8088/rescateAnimal/api_ticket/validate_user.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            conn.setDoInput(true);
            String urlParameters = "codigo=" + 3 + "&nombre=" + n + "&correo=" + c + "&pass=" + hash + "&tel=" + t + "";
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
