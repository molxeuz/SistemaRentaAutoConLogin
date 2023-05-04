package com.myapp.sistemarentaauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity_usuarios extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText usuario_usuario, nombre_usuario, contraseña_usuario;
    ImageButton guardar_usuario, regresar_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_usuarios);
        getSupportActionBar().hide();
        usuario_usuario = findViewById(R.id.etusuario_usuario);
        nombre_usuario = findViewById(R.id.etnombre_usuario);
        contraseña_usuario = findViewById(R.id.etcontraseña_usuario);
        guardar_usuario = findViewById(R.id.btnguardar_usuario);
        regresar_usuario = findViewById(R.id.btnregresar_usuario);
        regresar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        guardar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usuario_usuario.getText().toString().isEmpty() && !nombre_usuario.getText().toString().isEmpty() && !contraseña_usuario.getText().toString().isEmpty()){
                    db.collection("Usuario_tabla").whereEqualTo("usuario", usuario_usuario.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                if(task.getResult().isEmpty()){
                                    Map<String, Object> usuarios = new HashMap<>();
                                    usuarios.put("nombre", nombre_usuario.getText().toString());
                                    usuarios.put("usuario", usuario_usuario.getText().toString());
                                    usuarios.put("contraseña", contraseña_usuario.getText().toString());
                                    db.collection("Usuario_tabla").add(usuarios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(MainActivity_usuarios.this, "Usuario ingresado correctamente con la identificacion: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainActivity_usuarios.this, "No se pudo realizar el registro: " + e, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(MainActivity_usuarios.this, "Usuario existente, ingrese uno nuevo!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity_usuarios.this, "Debe ingresar todos los datos para guardar, intente de nuevo!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}