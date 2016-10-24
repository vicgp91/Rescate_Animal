package appcpanama.logicstudio.net.appcpanama.IntroFragments.InitialFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;
import appcpanama.logicstudio.net.appcpanama.Commons.fragmentResponse;
import appcpanama.logicstudio.net.appcpanama.Commons.pointerFragment;
import appcpanama.logicstudio.net.appcpanama.R;

public class LoginFragment extends fragmentResponse {

    //Clases
    SPControl control;

    //Controls
    Button btnLogin,
            btnRegister;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = (ViewGroup) inflater.inflate(R.layout.login, container, false);
            setRetainInstance(true);
            initInstance();
            assign();
        }
        return view;
    }

    private void initInstance() {
        btnLogin = (Button) view.findViewById(R.id.btn_login_sesion);
        btnRegister = (Button) view.findViewById(R.id.btn_login_registrar);


        control = new SPControl(context);
    }


    private void assign() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control.setStringValue("logValue", "loginAccedido");
                frgCallback.changeScreen(pointerFragment.POINTER_HOME);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frgCallback.changeScreen(pointerFragment.POINTER_REGISTRO);
            }
        });
    }

}
