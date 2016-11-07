package appcpanama.logicstudio.net.appcpanama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class ParticipaActivity extends AppCompatActivity {
    Toolbar toolbar;
    WebView webView;
    String htmlText = "<html>" + "<HEAD>\n" +
            " <meta name=\"tipo_contenido\"  content=\"text/html;\" http-equiv=\"content-type\" charset=\"utf-8\">\n" +
            "</HEAD>"+
            "<body style=\"text-align:justify\" text=\"#737373\" style=\"font-size:19px; background-color:#f4f8fa;\"> %s </body></Html>";

    String text1="\n" +
            "<article>\n" +
            "  <header>\n" +
            "    <h2 style=\"margin-left:45px;\"><br><br><b>Proyecto Rescate Animal</b></h2>\n" +
            "  </header>\n" +
            "  <img alt=\"Smiley face\" width=\"400\" height=\"600\"  style=\"float:right;\n" +
            "    clear:right;\" src=\"C:\\Users\\LogicStudio\\Documents\\GitHub\\Rescate_Animal\\app\\src\\main\\res\\drawable-xhdpi\\logo_color.png\">\n" +
            "  <p style=\"margin-left:20px; text-align: justify; margin-right:20px\"><br><br>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse efficitur sodales elit, vel blandit ex elementum a. Fusce sed fermentum leo, at maximus tortor. Aliquam porta mauris convallis dapibus pharetra. Sed tristique eros eu tellus tempus lobortis. Donec lobortis mattis orci, quis dignissim libero fringilla eget. Maecenas ut elementum magna. Fusce maximus, ipsum et dictum tristique, \n" +
            "  tortor enim sodales lectus, ut accumsan tortor sem eu dolor.</p>\n" +
            "  \n" +
            "  \n" +
            "  <p style=\"margin-left:20px; text-align: justify; margin-right:20px\"><br> <br> Duis egestas viverra porta. Praesent sodales pharetra lorem vel facilisis. Pellentesque non urna id arcu cursus feugiat. Morbi non tortor nisi.</p><br> \n" +
            "  \n" +
            "  \n" +
            "  <a style=\"color: #fbce46; margin-left:20px;\" href=\"#\">Más Información</a>\n" +
            "  <br><br><br>\n" +
            "</article>\n" +
            "\n" +
            "<hr>\n" +
            "<article>\n" +
            "<br>\n" +
            "<br>\n" +
            "  <img src=\"C:\\Users\\LogicStudio\\Downloads\\LogicStudio_header-logo.png\" style=\"margin-left:110px; margin-right:45px\"> \n" +
            "  <header>\n" +
            "    <br> <h2 style=\"margin-left:20px;\"><br><b>Logic Studio</b></h2>\n" +
            "  </header>\n" +
            "  \n" +
            "  <p style=\"margin-left:20px; text-align: justify; margin-right:20px\"><br>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse efficitur sodales elit, vel blandit ex elementum a. Fusce sed fermentum leo, at maximus tortor. Aliquam porta mauris convallis dapibus pharetra. Sed tristique eros eu tellus tempus lobortis. Donec lobortis mattis orci, quis dignissim libero fringilla eget. Maecenas ut elementum magna. Fusce maximus, ipsum et dictum tristique, \n" +
            "  tortor enim sodales lectus, ut accumsan tortor sem eu dolor.</p>\n" +
            "\n" +
            "</article>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participa);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.webViewParticipa);
        //webView.loadData(String.format(htmlText,text1), "text/html", "utf-8");
        webView.loadUrl("file:///android_asset/participa.html");

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_participa, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
