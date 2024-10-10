package com.ledesma.tp2loginconobject.request;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.ledesma.tp2loginconobject.model.Usuario;
import com.ledesma.tp2loginconobject.ui.registro.RegistroActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ApiClient {
    private static File archivo;
    private static File conectar(Context context) {
        if(archivo==null){
            archivo=new File(context.getFilesDir(), "usuario.dat");

        }
        return archivo;
    }
    public static Usuario obtenerUsuario(Context context){
        File archivo = conectar(context);
        Usuario usu = new Usuario();

        try {
            FileInputStream fis = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            try {
                usu = (Usuario) ois.readObject();
                    String nombre = usu.getNombre();
                    String apellido = usu.getApellido();
                    String email = usu.getEmail();
                    String pass = usu.getPassword();
                    long dni = usu.getDni();
                    fis.close();
                    return new Usuario(dni, nombre, apellido, email, pass);

            } catch (EOFException eof) {
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "Error al acceder al archivo FileNotFound obtenerUsuario", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context, "Error al acceder al archivo IOExcep", Toast.LENGTH_LONG).show();
        }
        return usu;
    }


    public static void guardarUsuario(Usuario usuario, Context context) {
       // Usuario usu = new Usuario(Long.parseLong(dni),nombre, apellido, email, password);
        File archivo = conectar(context);
        try {

            FileOutputStream fos = new FileOutputStream(archivo, false);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(usuario);
            bos.flush();
            fos.close();
            usuario = null;
            Toast.makeText(context, "Usuario Guardado", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "Error al acceder al archivo FileNotFound", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context, "Error al acceder al archivo IOExcep", Toast.LENGTH_LONG).show();
        }
    }

    public static Usuario login(Context context, String email, String pass){
        File archivo = conectar(context);
        Usuario usuario = null;

        try {
            FileInputStream fi = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fi);
            ObjectInputStream ois = new ObjectInputStream(bis);

            while (true) {
                try {
                    usuario = (Usuario) ois.readObject();
                    if (usuario.getEmail().equals(email) && usuario.getPassword().equals(pass)) {
                        fi.close();
                        break;
                    } else {
                        Toast.makeText(context, "Usuario y/o Password Incorrectos", Toast.LENGTH_LONG).show();
                        usuario.setNombre("-1");
                        fi.close();
                    }
                } catch (EOFException e) {
                    fi.close();
                    break;
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }


}
