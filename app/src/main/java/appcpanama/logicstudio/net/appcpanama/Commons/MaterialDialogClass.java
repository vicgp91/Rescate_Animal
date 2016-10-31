package appcpanama.logicstudio.net.appcpanama.Commons;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import appcpanama.logicstudio.net.appcpanama.HomeScreen;
import appcpanama.logicstudio.net.appcpanama.R;

/**
 * Created by LogicStudio on 31/10/16.
 */

public class MaterialDialogClass {

    public static void createOKDialog(Context context, String title, String description){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_custom);

        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView titleTxt = (TextView)dialog.findViewById(R.id.txt_dialog_title);
        TextView descriptionTxt = (TextView)dialog.findViewById(R.id.txt_dialog_description);

        titleTxt.setText(title);
        descriptionTxt.setText(description);

        Button okButton = (Button)dialog.findViewById(R.id.positiveButton);

        dialog.findViewById(R.id.negativeButton).setVisibility(View.GONE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
