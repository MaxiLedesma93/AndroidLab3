package com.ledesma.tp3loginconfoto.ui.registro;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ledesma.tp3loginconfoto.model.Usuario;
import com.ledesma.tp3loginconfoto.request.ApiClient;
import com.ledesma.tp3loginconfoto.ui.login.MainActivity;

public class RegistroViewModel extends AndroidViewModel {
    Context context;
    MutableLiveData<Usuario> mUsuario;
    MutableLiveData<Uri> uriMutableLiveData;
    Uri uri;
    public RegistroViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
    }
    public MutableLiveData<Usuario> getMUsuario() {
        if (mUsuario == null) {
            mUsuario = new MutableLiveData<>();
        }
        return mUsuario;
    }
    public LiveData<Uri> getMUri(){
        if(uriMutableLiveData==null){
            uriMutableLiveData = new MutableLiveData<>();
        }
        return uriMutableLiveData;
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
        Usuario usu = new Usuario(nombre, apellido, email, dni, password, uri.toString());
        ApiClient.guardarUsuario(usu,context);
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void recibirFoto(ActivityResult result){
        if(result.getResultCode() == RESULT_OK){
            Intent data =   result.getData();
            uri = data.getData();
            uriMutableLiveData.setValue(uri);








        }
    }
}
