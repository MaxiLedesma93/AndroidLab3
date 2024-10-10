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

public class MainActivityViewModel extends AndroidViewModel {

    Context context;
    MutableLiveData<Usuario> mUsuario;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
    }
    LiveData<Usuario> getMUsuario(){
        if(mUsuario==null){
            mUsuario = new MutableLiveData<>();
        }
        return mUsuario;
    }

    public void loguear(String email, String pass) {
       Usuario usuario = ApiClient.login(context,email, pass);
        if(!usuario.getNombre().equals("-1")){
            mUsuario.setValue(usuario);
        }

    }
}
