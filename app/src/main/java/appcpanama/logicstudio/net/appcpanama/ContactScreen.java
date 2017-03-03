package appcpanama.logicstudio.net.appcpanama;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class ContactScreen extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    LinearLayout btnLlamar,
            btnCorreo,
            btnSitio;
    LinearLayout btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_screen);
        initInstance();
        assign();
    }

    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnLlamar = (LinearLayout) findViewById(R.id.lay_phone);
        btnCorreo = (LinearLayout) findViewById(R.id.lay_correo);
        btnSitio = (LinearLayout) findViewById(R.id.lay_internet);


    }


    private void assign() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.contactTitle);

        btnLlamar.setOnClickListener(this);
        btnCorreo.setOnClickListener(this);
        btnSitio.setOnClickListener(this);


    }

    public void call() {

        String phone = "+5073171258";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(callIntent);
    }

    public void mail() {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        String mail = getResources().getString(R.string.correoappc);
        emailIntent.setData(Uri.parse("mailto:" + mail));
        startActivity(emailIntent);
    }

    public void openSite() {
        String url = getResources().getString(R.string.sitioappc);
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();

        switch (idView) {

            case R.id.lay_phone:
                call();
                break;

            case R.id.lay_correo:
                mail();
                break;

            case R.id.lay_internet:
                openSite();
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, HomeScreen.class);
                this.finish();
                this.startActivity(intent);

        }
        return (super.onOptionsItemSelected(menuItem));
    }

}
