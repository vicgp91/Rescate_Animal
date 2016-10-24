package org.rescateanimal.com.rescateanimal;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.rescateanimal.com.rescateanimal.Commons.SPControl;
import org.rescateanimal.com.rescateanimal.Commons.fragmentResponse;
import org.rescateanimal.com.rescateanimal.Commons.pointerFragment;
import org.rescateanimal.com.rescateanimal.IntroFragments.introFragmentAdministrator;

import java.util.List;

public class IntroActivity extends AppCompatActivity {

    //Variable
    SPControl control;

    //FragmentsManagements
    List<fragmentResponse> listfrg;
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
    private void initInstance() {

        control = new SPControl(getApplicationContext());

        fragmentManager = getFragmentManager();

        frgAdministrator = new introFragmentAdministrator();

        listfrg = frgAdministrator.addFragment(getApplicationContext());

        setFragmentCallbacks();
        changeFragment();
    }


    //PREPARE FOR CHANGE
    private void prepareListFrg(pointerFragment pointer) {
        try {
            listfrg = frgAdministrator.addFragment(getApplicationContext(), listfrg, pointer);
            setFragmentCallbacks();
            changeFragment();

        } catch (Exception e) {

        }
    }


    //METHOD CHANGE FRAGMENT
    private void changeFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmlay_intro_root, listfrg.get(listfrg.size() - 1));
        fragmentTransaction.commit();
    }


    //SET CALLBACK LISTENER
    private void setFragmentCallbacks() {
        listfrg.get(listfrg.size() - 1).setFragmentCallbackListener(new fragmentResponse.fragmentCallback() {
            @Override
            public void changeScreen(pointerFragment pointer) {
                Log.e("TAG", "CHANGE");
                if(listfrg.size() > 1 && !control.contains("logValue"))
                    listfrg.remove(listfrg.size()-1);

                prepareListFrg(pointer);
            }

            @Override
            public void setToolbar(Toolbar toolbar) {
                setSupportActionBar(toolbar);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (listfrg.size() > 1) {
            listfrg.remove(listfrg.size() - 1);
            changeFragment();
        } else {
            super.onBackPressed();
        }
    }
}
