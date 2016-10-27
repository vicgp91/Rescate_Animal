package appcpanama.logicstudio.net.appcpanama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class PerfilScreen extends AppCompatActivity implements View.OnClickListener {

    //Controls
    Toolbar toolbar;
    LinearLayout btnActDatos,
            btnCambiarContr,
            btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_screen);

        initInstance();
        assign();
    }

    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        btnActDatos = (LinearLayout) findViewById(R.id.lay_perfil_datos);
        btnCambiarContr = (LinearLayout) findViewById(R.id.lay_perfil_contrasena);
        btnLogOut = (LinearLayout) findViewById(R.id.lay_perfil_sesion);
    }


    private void assign() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.perfilTitle);

        btnActDatos.setOnClickListener(this);
        btnCambiarContr.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);

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
}
