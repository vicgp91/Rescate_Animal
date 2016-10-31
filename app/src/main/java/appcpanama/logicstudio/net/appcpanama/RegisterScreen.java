package appcpanama.logicstudio.net.appcpanama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import appcpanama.logicstudio.net.appcpanama.Commons.MaterialDialogClass;


public class RegisterScreen extends AppCompatActivity {

    //Controls
    Button btnLogin;
    Button btnRegister;

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
    }

    private void assign() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialDialogClass.createOKDialog(RegisterScreen.this,
                        getString(R.string.dialogRegisterTitle),
                        getString(R.string.dialogRegisterDescription));
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
}
