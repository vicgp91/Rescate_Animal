package appcpanama.logicstudio.net.appcpanama;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;
import appcpanama.logicstudio.net.appcpanama.Commons.fragmentResponse;
import appcpanama.logicstudio.net.appcpanama.Commons.pointerFragment;
import appcpanama.logicstudio.net.appcpanama.IntroFragments.HomeFragment;
import appcpanama.logicstudio.net.appcpanama.IntroFragments.introFragmentAdministrator;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    //Variable
    SPControl control;

    //FragmentsManagements
    List<fragmentResponse> listfrg = new ArrayList<>();
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

        if (!control.contains("logValue"))
            listfrg = frgAdministrator.addFragment(getApplicationContext());
        else
            listfrg = frgAdministrator.addFragment(getApplicationContext(), listfrg, pointerFragment.POINTER_HOME);

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
                if (listfrg.size() > 1 && !control.contains("logValue"))
                    listfrg.remove(listfrg.size() - 1);

                prepareListFrg(pointer);
            }

            @Override
            public void setToolbar(Toolbar toolbar) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            @Override
            public void callOnBackPressed() {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (listfrg.get(listfrg.size() - 1).getClass() == HomeFragment.class &&
                ((HomeFragment) listfrg.get(listfrg.size() - 1)).isDrawerLayoutOpen()) {

            ((HomeFragment) listfrg.get(listfrg.size() - 1)).closeDrawerLayout();

        } else {
            if (listfrg.size() > 1) {
                listfrg.remove(listfrg.size() - 1);
                changeFragment();
            } else {
                super.onBackPressed();
            }
        }
    }

}
