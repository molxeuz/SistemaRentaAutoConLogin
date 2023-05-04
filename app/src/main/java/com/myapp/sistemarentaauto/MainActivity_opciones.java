package com.myapp.sistemarentaauto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity_opciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_opciones);
        getSupportActionBar().hide();
        ImageButton registro_automoviles, registro_renta, cerrar_sesion_opciones;
        registro_automoviles = findViewById(R.id.btnregistro_automoviles);
        registro_renta = findViewById(R.id.btnregistro_renta);
        cerrar_sesion_opciones = findViewById(R.id.btncerrar_sesion_opciones);
        cerrar_sesion_opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        registro_automoviles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_automoviles.class);
                startActivity(intent);
            }
        });
        registro_renta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_renta.class);
                startActivity(intent);
            }
        });
    }
}