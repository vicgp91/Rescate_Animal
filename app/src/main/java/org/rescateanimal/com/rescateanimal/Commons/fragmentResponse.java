package org.rescateanimal.com.rescateanimal.Commons;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by LogicStudio on 21/10/16.
 */

public class fragmentResponse extends Fragment {

    //View - Context
    public View view;
    public Context context;

    //Callback
    public fragmentCallback frgCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.context = activity.getApplicationContext();
    }

    public interface fragmentCallback{
        void changeScreen(pointerFragment pointer);
        void setToolbar(Toolbar toolbar);
    }


    public void setFragmentCallbackListener(fragmentCallback fragmentCallback)
    {
        this.frgCallback = fragmentCallback;
    }
}
