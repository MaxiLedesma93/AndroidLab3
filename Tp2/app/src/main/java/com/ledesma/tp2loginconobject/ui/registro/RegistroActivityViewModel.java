package com.ledesma.tp2loginconobject.ui.registro;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ledesma.tp2loginconobject.model.Usuario;
import com.ledesma.tp2loginconobject.request.ApiClient;


public class RegistroActivityViewModel extends AndroidViewModel {
    MutableLiveData<Usuario> mUsuario;
    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<Usuario> getMUsuario() {
        if (mUsuario == null) {
            mUsuario = new MutableLiveData<>();
        }
        return mUsuario;
    }

    public void recuperarUsuario(Context context, String mail, String password) {
        Usuario usuario = ApiClient.obtenerUsuario(context, mail, password);
        if(usuario!=null){
            getMUsuario().setValue(usuario);
        }else {
            Toast.makeText(context, "Credenciales no validas", Toast.LENGTH_SHORT).show();
        }

    }

    public void guardar(String dni, String nombre, String apellido, String email, String password) {
        Usuario usu = new Usuario(Long.parseLong(dni),nombre, apellido, email, password);

        ApiClient.guardarUsuario(usu,getApplication().getApplicationContext());
    }
}
