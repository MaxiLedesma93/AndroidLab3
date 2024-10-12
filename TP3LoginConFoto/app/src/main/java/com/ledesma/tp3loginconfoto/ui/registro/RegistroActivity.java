package com.ledesma.tp3loginconfoto.ui.registro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ledesma.tp3loginconfoto.R;
import com.ledesma.tp3loginconfoto.databinding.ActivityRegistroBinding;
import com.ledesma.tp3loginconfoto.model.Usuario;

public class RegistroActivity extends AppCompatActivity {
    ActivityRegistroBinding binding;
    RegistroViewModel viewModel;
    private Intent intent;

    private ActivityResultLauncher<Intent> arl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(RegistroViewModel.class);
        setContentView(binding.getRoot());
        abrirGaleria();
        viewModel.getMUri().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivFoto.setImageURI(uri);
            }
        });
        viewModel.getMUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.etNombre.setText(usuario.getNombre());
                binding.etApellido.setText(usuario.getApellido());
                binding.etDni.setText(usuario.getDni());
                binding.etEmail.setText(usuario.getEmail());
                binding.etPassword.setText(usuario.getPassword());
                binding.ivFoto.setImageURI(Uri.parse(usuario.getFoto()));
            }
        });
        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.guardar(binding.etDni.getText().toString(),
                        binding.etNombre.getText().toString(),
                        binding.etApellido.getText().toString(),
                        binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString()
                );
            }
        });
        binding.btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });
        if(getIntent().getFlags()== Intent.FLAG_ACTIVITY_NEW_TASK){
            viewModel.cargar();

        }
    }
    private void abrirGaleria(){


        intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        arl=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                viewModel.recibirFoto(result);


            }
        });



    }
}