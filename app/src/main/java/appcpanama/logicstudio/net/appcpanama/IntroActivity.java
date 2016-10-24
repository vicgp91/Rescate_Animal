package appcpanama.logicstudio.net.appcpanama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    //Variable
    SPControl control;


    //Controls
    Button loginBtn,
            registerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        initInstance();
        assign();
    }

    private void initInstance() {

        loginBtn = (Button) findViewById(R.id.btn_inicio_login);
        registerBtn = (Button) findViewById(R.id.btn_inicio_register);
    }

    private void assign() {

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int idView = view.getId();

        switch (idView) {
            case R.id.btn_inicio_login:
                btnInicioClick();
                break;

            case R.id.btn_inicio_register:
                btnRegisterClick();
                break;
        }
    }


    private void btnInicioClick() {
        startActivity(new Intent(IntroActivity.this, LoginScreen.class));
        finish();
    }

    private void btnRegisterClick() {
        startActivity(new Intent(IntroActivity.this, RegisterScreen.class));
        finish();
    }

}
