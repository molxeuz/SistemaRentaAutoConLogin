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

public class MainActivity_renta extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText renta_renta, usuario_renta, placa_renta, fecha_renta;
    ImageButton guardar_renta, buscar_renta, regresar_renta;
    String vieja_renta, buscar_id_renta;
    Boolean isChecked = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_renta);
        getSupportActionBar().hide();
        renta_renta = findViewById(R.id.etrenta_renta);
        usuario_renta = findViewById(R.id.etusuario_renta);
        placa_renta = findViewById(R.id.etplaca_renta);
        fecha_renta = findViewById(R.id.etfecha_renta);
        guardar_renta = findViewById(R.id.btnguardar_renta);
        buscar_renta = findViewById(R.id.btnbuscar_renta);
        regresar_renta = findViewById(R.id.btnregresar_renta);
        regresar_renta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_opciones.class);
                startActivity(intent);
            }
        });
        guardar_renta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!renta_renta.getText().toString().isEmpty() && !usuario_renta.getText().toString().isEmpty() && !placa_renta.getText().toString().isEmpty() && !fecha_renta.getText().toString().isEmpty()){
                    db.collection("Usuario_tabla").whereEqualTo("usuario", usuario_renta.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().size() > 0) {
                                    db.collection("Auto_tabla").whereEqualTo("placa", placa_renta.getText().toString()).whereEqualTo("estado", isChecked).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                if (task.getResult().size() > 0) {
                                                    db.collection("Renta_tabla").whereEqualTo("renta", renta_renta.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
                                                                if(task.getResult().isEmpty()){
                                                                    Map<String, Object> renta = new HashMap<>();
                                                                    renta.put("renta", renta_renta.getText().toString());
                                                                    renta.put("usuario_renta", usuario_renta.getText().toString());
                                                                    renta.put("placa_renta", placa_renta.getText().toString());
                                                                    renta.put("fecha", fecha_renta.getText().toString());
                                                                    db.collection("Renta_tabla").add(renta).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentReference documentReference) {
                                                                            Toast.makeText(MainActivity_renta.this, "Renta ingresada correctamente con la identificacion: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(MainActivity_renta.this, "No se pudo realizar el registro: " + e, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                } else {
                                                                    Toast.makeText(MainActivity_renta.this, "Usuario existente, ingrese uno nuevo!!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(MainActivity_renta.this, "Placa no disponible o el automovil no esta disponible", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(MainActivity_renta.this, "Placa no disponible o el automovil no esta disponible", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(MainActivity_renta.this, "Usuario no disponible para realizar renta", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity_renta.this, "Usuario no disponible para realizar renta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity_renta.this, "Ingresa todos los campos para realizar una renta", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buscar_renta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if  (!renta_renta.getText().toString().isEmpty()){
                    db.collection("Renta_tabla").whereEqualTo("renta", renta_renta.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                if (!task.getResult().isEmpty()){
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        vieja_renta = document.getString("renta");
                                        buscar_id_renta = document.getId();
                                        usuario_renta.setText(document.getString("usuario_renta"));
                                        placa_renta.setText(document.getString("placa_renta"));
                                        fecha_renta.setText(document.getString("fecha"));
                                        Toast.makeText(getApplicationContext(), "Codigo de renta: " + buscar_id_renta, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity_renta.this, "Renta no existe, intente de nuevo!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity_renta.this, "Debe ingresar la identificacion de renta a buscar, intente de nuevo!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}