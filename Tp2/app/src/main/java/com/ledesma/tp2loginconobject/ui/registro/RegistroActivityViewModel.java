package com.ledesma.tp2loginconobject.ui.registro;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ledesma.tp2loginconobject.model.Usuario;
import com.ledesma.tp2loginconobject.request.ApiClient;
import com.ledesma.tp2loginconobject.ui.login.MainActivity;

import java.io.File;


public class RegistroActivityViewModel extends AndroidViewModel {
    MutableLiveData<Usuario> mUsuario;
    Context context;
    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
    }
    public MutableLiveData<Usuario> getMUsuario() {
        if (mUsuario == null) {
            mUsuario = new MutableLiveData<>();
        }
        return mUsuario;
    }

    public void cargar() {
        Usuario usuario = ApiClient.obtenerUsuario(context);
        if(!usuario.getNombre().equals("-1")){
            getMUsuario().setValue(usuario);
        }else {
            Toast.makeText(getApplication().getApplicationContext(), "Credenciales no validas", Toast.LENGTH_SHORT).show();
        }

    }

    public void guardar(String dni, String nombre, String apellido, String email, String password) {
        Usuario usu = new Usuario(Long.parseLong(dni),nombre, apellido, email, password);
        ApiClient.guardarUsuario(usu,context);
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
