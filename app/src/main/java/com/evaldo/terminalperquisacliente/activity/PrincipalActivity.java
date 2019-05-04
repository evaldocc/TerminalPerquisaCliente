package com.evaldo.terminalperquisacliente.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.evaldo.terminalperquisacliente.R;

public class PrincipalActivity extends AppCompatActivity {

    public static int pularTela = 1;
    public static boolean chamarPrimeiraTelaUmVez = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        System.out.println("PrincipalActivity");
        chamarGerenciadorTelas();

    }

    private void chamarGerenciadorTelas() {
        Intent intent = new Intent(this, TelaGerenciadorActivity.class);
        onPause();
        startActivity(intent);
    }

}
