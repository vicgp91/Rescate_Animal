package appcpanama.logicstudio.net.appcpanama.IntroFragments;

import android.content.Context;

import appcpanama.logicstudio.net.appcpanama.Commons.fragmentResponse;
import appcpanama.logicstudio.net.appcpanama.Commons.pointerFragment;
import appcpanama.logicstudio.net.appcpanama.IntroFragments.InitialFragments.InicioFragment;
import appcpanama.logicstudio.net.appcpanama.IntroFragments.InitialFragments.LoginFragment;
import appcpanama.logicstudio.net.appcpanama.IntroFragments.InitialFragments.RegisterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LogicStudio on 21/10/16.
 */

public class introFragmentAdministrator {

    public List<fragmentResponse> addFragment(Context context)
    {
        List<fragmentResponse> listfrg = new ArrayList<>();

        fragmentResponse frg = (fragmentResponse) InicioFragment.instantiate(context, InicioFragment.class.getName());

        listfrg.add(frg);

        return listfrg;
    }

    public List<fragmentResponse> addFragment(Context context, List<fragmentResponse> listfrg, pointerFragment pointer)
    {

        fragmentResponse frg = null;

        switch (pointer)
        {
            case POINTER_INICIO:
                frg = (fragmentResponse)InicioFragment.instantiate(context, InicioFragment.class.getName());
                break;

            case POINTER_LOGIN:
                frg = (fragmentResponse) LoginFragment.instantiate(context, LoginFragment.class.getName());
                break;

            case POINTER_REGISTRO:
                frg = (fragmentResponse) RegisterFragment.instantiate(context, RegisterFragment.class.getName());
                break;

            case POINTER_HOME:
                frg = (fragmentResponse) HomeFragment.instantiate(context, HomeFragment.class.getName());
                break;
        }

        listfrg.add(frg);

        return listfrg;
    }
}
