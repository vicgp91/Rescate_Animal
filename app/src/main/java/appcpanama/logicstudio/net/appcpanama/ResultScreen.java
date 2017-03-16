package appcpanama.logicstudio.net.appcpanama;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultScreen extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    LinearLayout btnShare;
    public static final String PREFS_NAME = "RA_PREFS";
    String nombre, correo, huellas;
    TextView txnombre, txthuella;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_screen);
        SharedPreferences settings;
        settings = getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        SharedPreferences.Editor editor = settings.edit();
        nombre = settings.getString("name", null);
        correo = settings.getString("Correo", null);
        huellas = String.valueOf(settings.getInt("huella", 0));
        initInstance();
        assign();
    }


    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        btnShare = (LinearLayout) findViewById(R.id.lay_compartir);
        txnombre = (TextView) findViewById(R.id.idnombre);
        txthuella = (TextView) findViewById(R.id.idhuellas);

    }


    private void assign() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.resultTitle);
        btnShare.setOnClickListener(this);
        txnombre.setText(nombre);
        txthuella.setText(huellas + " huellas");


    }

    @Override
    public void onClick(View v) {

        int idView = v.getId();

        switch (idView) {

            case R.id.lay_compartir:
                shareContent();
                break;


            default:
                break;
        }

    }

    public void shareContent() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>Felicidades, has ganado 4 huellas! </p>"));
        startActivity(Intent.createChooser(sharingIntent, "Share using"));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, HomeScreen.class);
                this.startActivity(intent);

        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
