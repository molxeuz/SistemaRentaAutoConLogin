package com.myapp.sistemarentaauto;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText usuario_inicio, contraseña_inicio;
    ImageButton ingresar_inicio, registrar_inicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        usuario_inicio = findViewById(R.id.etusuario_inicio);
        contraseña_inicio = findViewById(R.id.etcontraseña_inicio);
        ingresar_inicio = findViewById(R.id.btningresar_inicio);
        registrar_inicio = findViewById(R.id.btnregistrar_inicio);
        registrar_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_usuarios.class);
                startActivity(intent);
            }
        });
        ingresar_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usuario_inicio.getText().toString().isEmpty() && !contraseña_inicio.getText().toString().isEmpty()) {
                    db.collection("Usuario_tabla").whereEqualTo("usuario", usuario_inicio.getText().toString()).whereEqualTo("contraseña", contraseña_inicio.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().size() > 0) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity_opciones.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "Acceso de sesión no exitoso", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Acceso de sesión no exitoso", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Ingresa todos los campos para iniciar sesion!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}