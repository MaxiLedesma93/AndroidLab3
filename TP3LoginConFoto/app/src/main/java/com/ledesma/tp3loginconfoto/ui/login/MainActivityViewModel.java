package com.ledesma.tp3loginconfoto.ui.login;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ledesma.tp3loginconfoto.model.Usuario;
import com.ledesma.tp3loginconfoto.request.ApiClient;
import com.ledesma.tp3loginconfoto.ui.registro.RegistroActivity;

public class MainActivityViewModel extends AndroidViewModel {


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }
    public void loguear(String email, String pass) {
        if(email.isEmpty() || pass.isEmpty()){
            Toast.makeText(getApplication().getApplicationContext(), "Ingrese un email y una contraseña", Toast.LENGTH_LONG).show();
        }else {
            Usuario usuario = ApiClient.login(getApplication().getApplicationContext(), email, pass);
            if (usuario != null) {
                Intent intent = new Intent(getApplication(), RegistroActivity.class);
                intent.putExtra("usuario", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intent);
            } else {
                Toast.makeText(getApplication(), "Email o contraseña incorrecta", Toast.LENGTH_LONG).show();
            }
        }

    }
}
