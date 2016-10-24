package org.rescateanimal.com.rescateanimal.IntroFragments.InitialFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.rescateanimal.com.rescateanimal.Commons.fragmentResponse;
import org.rescateanimal.com.rescateanimal.Commons.pointerFragment;
import org.rescateanimal.com.rescateanimal.R;


public class InicioFragment extends fragmentResponse implements View.OnClickListener {


    //Controls
    Button loginBtn,
            registerBtn;


    public InicioFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = (ViewGroup) inflater.inflate(R.layout.inicio, container, false);

            initInstance();
            assign();
        }
        return view;
    }

    private void initInstance() {

        loginBtn = (Button) view.findViewById(R.id.btn_inicio_login);
        registerBtn = (Button) view.findViewById(R.id.btn_inicio_register);
    }

    private void assign() {

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int idView = view.getId();

        switch (idView) {
            case R.id.btn_inicio_login:
                btnInicioClick();
                break;

            case R.id.btn_inicio_register:
                btnRegisterClick();
                break;
        }
    }


    private void btnInicioClick() {
        frgCallback.changeScreen(pointerFragment.POINTER_LOGIN);
    }

    private void btnRegisterClick() {
        frgCallback.changeScreen(pointerFragment.POINTER_REGISTRO);
    }
}
