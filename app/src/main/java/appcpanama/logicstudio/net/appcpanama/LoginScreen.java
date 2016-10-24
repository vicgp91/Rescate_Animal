package appcpanama.logicstudio.net.appcpanama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;

public class LoginScreen extends AppCompatActivity {

    //Clases
    SPControl control;

    //Controls
    Button btnLogin,
            btnRegister;

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


        control = new SPControl(getApplicationContext());
    }


    private void assign() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control.setStringValue("logValue", "loginAccedido");
                startActivity(new Intent(LoginScreen.this, HomeScreen.class));
                finish();
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
}
