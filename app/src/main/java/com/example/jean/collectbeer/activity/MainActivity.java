package com.example.jean.collectbeer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jean.collectbeer.R;

import static android.R.attr.data;
import static com.example.jean.collectbeer.activity.NuevoActivity.ADDED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
RelativeLayout nuevo, consultar, mostrarTodo, configuracion;
    public final static int REQUEST=10;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nuevo =(RelativeLayout) findViewById(R.id.layoutNuevo);
        consultar=(RelativeLayout) findViewById(R.id.layoutConsultar);
        mostrarTodo=(RelativeLayout) findViewById(R.id.layoutMostrar);
        configuracion=(RelativeLayout) findViewById(R.id.layoutConfig);

        nuevo.setOnClickListener(this);
        consultar.setOnClickListener(this);
        mostrarTodo.setOnClickListener(this);
        configuracion.setOnClickListener(this);
        Log.i("PRUEBA", "ANTES DE TOdO CREATE MAIN ACTICYTY");

}


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutNuevo:
                intent=new Intent(this, NuevoActivity.class);
                startActivityForResult(intent,REQUEST);
                break;
            case R.id.layoutConsultar:
                intent=new Intent(this, ConsultaActivity.class);
                startActivity(intent);
                break;
            case R.id.layoutMostrar:
                intent=new Intent(this, MostrarActivity.class);
                startActivity(intent);
                break;
            case R.id.layoutConfig:
                intent=new Intent(this, ConfigActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST){
            if (resultCode==RESULT_OK) {
                Toast.makeText(this, "Guardado Correctamente!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
