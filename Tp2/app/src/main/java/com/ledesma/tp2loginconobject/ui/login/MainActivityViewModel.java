package com.ledesma.tp2loginconobject.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ledesma.tp2loginconobject.model.Usuario;
import com.ledesma.tp2loginconobject.request.ApiClient;
import com.ledesma.tp2loginconobject.ui.registro.RegistroActivity;

public class MainActivityViewModel extends AndroidViewModel {

    Context context;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
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
