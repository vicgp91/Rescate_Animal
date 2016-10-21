package org.rescateanimal.com.rescateanimal.IntroFragments;

import android.content.Context;

import org.rescateanimal.com.rescateanimal.Commons.fragmentResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LogicStudio on 21/10/16.
 */

public class introFragmentAdministrator {

    public List<fragmentResponse> addFragment(Context context)
    {
        List<fragmentResponse> listfrg = new ArrayList<>();

        fragmentResponse frg = (fragmentResponse)InicioFragment.instantiate(context, InicioFragment.class.getName());

        listfrg.add(frg);

        return listfrg;
    }
}
