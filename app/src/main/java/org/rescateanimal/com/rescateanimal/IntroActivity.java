package org.rescateanimal.com.rescateanimal;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.rescateanimal.com.rescateanimal.Commons.fragmentResponse;
import org.rescateanimal.com.rescateanimal.IntroFragments.introFragmentAdministrator;

import java.util.List;

public class IntroActivity extends AppCompatActivity {

    //FragmentsManagements
    List<fragmentResponse>listfrg;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    introFragmentAdministrator frgAdministrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        initInstance();
    }

    //INIT INSTANCE
    private void initInstance(){
        fragmentManager = getFragmentManager();

        frgAdministrator = new introFragmentAdministrator();

        listfrg = frgAdministrator.addFragment(getApplicationContext());


        setFragmentCallbacks();
        changeFragment();
    }


    //METHOD CHANGE FRAGMENT
    private void changeFragment()
    {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmlay_intro_root, listfrg.get(listfrg.size() -1));
        fragmentTransaction.commit();
    }

    //SET CALLBACK LISTENER
    private void setFragmentCallbacks()
    {
        listfrg.get(listfrg.size() -1).setFragmentCallbackListener(new fragmentResponse.fragmentCallback() {
            @Override
            public void changeScreen() {
                Log.e("TAG", "CHANGE");
            }
        });
    }
}
