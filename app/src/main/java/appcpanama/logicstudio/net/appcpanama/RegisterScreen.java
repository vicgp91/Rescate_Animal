package appcpanama.logicstudio.net.appcpanama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class RegisterScreen extends AppCompatActivity {

    //Controls
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        initInstance();
        assign();
    }

    private void initInstance() {

        btnLogin = (Button)findViewById(R.id.btn_registro_login);
    }

    private void assign() {
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
        startActivity(new Intent(RegisterScreen.this,IntroActivity.class));
        super.onBackPressed();
    }
}
