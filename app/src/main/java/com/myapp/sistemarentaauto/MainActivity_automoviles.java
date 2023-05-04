package com.myapp.sistemarentaauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
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

public class MainActivity_automoviles extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText placa_auto, marca_auto;
    Switch estado_auto;
    ImageButton guardar_auto, regresar_auto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_automoviles);
        getSupportActionBar().hide();
        placa_auto = findViewById(R.id.etplaca_auto);
        marca_auto = findViewById(R.id.etmarca_auto);
        estado_auto = findViewById(R.id.swestado_auto);
        guardar_auto = findViewById(R.id.btnguardar_auto);
        regresar_auto = findViewById(R.id.btnregresar_auto);
        regresar_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_opciones.class);
                startActivity(intent);
            }
        });
        guardar_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!placa_auto.getText().toString().isEmpty() && !marca_auto.getText().toString().isEmpty()){
                    db.collection("Auto_tabla").whereEqualTo("placa", placa_auto.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                if(task.getResult().isEmpty()){
                                    Map<String, Object> autos = new HashMap<>();
                                    autos.put("marca", marca_auto.getText().toString());
                                    autos.put("placa", placa_auto.getText().toString());
                                    boolean isChecked = estado_auto.isChecked();
                                    autos.put("estado", isChecked);
                                    db.collection("Auto_tabla").add(autos).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(MainActivity_automoviles.this, "Auto ingresado correctamente con la identificacion: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainActivity_automoviles.this, "No se pudo realizar el registro: " + e, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(MainActivity_automoviles.this, "Auto existente, ingrese uno nuevo!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity_automoviles.this, "Debe ingresar todos los datos para guardar, intente de nuevo!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}