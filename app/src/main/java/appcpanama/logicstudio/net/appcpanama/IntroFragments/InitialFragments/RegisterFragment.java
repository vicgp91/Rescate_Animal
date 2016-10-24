package appcpanama.logicstudio.net.appcpanama.IntroFragments.InitialFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import appcpanama.logicstudio.net.appcpanama.Commons.fragmentResponse;
import appcpanama.logicstudio.net.appcpanama.Commons.pointerFragment;
import appcpanama.logicstudio.net.appcpanama.R;

public class RegisterFragment extends fragmentResponse {


    //Controls
    Button btnLogin;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = (ViewGroup) inflater.inflate(R.layout.register, container, false);

            initInstance();
            assign();
        }
        return view;
    }


    private void initInstance() {

        btnLogin = (Button)view.findViewById(R.id.btn_registro_login);
    }

    private void assign() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frgCallback.changeScreen(pointerFragment.POINTER_LOGIN);
            }
        });
    }

}
