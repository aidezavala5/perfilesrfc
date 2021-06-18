package com.example.catalogo.personafisica;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.catalogo.R;
import com.example.catalogo.domicilio.DomicilioActivity;
import com.example.catalogo.inicio.CatalogoActivity;
import com.example.catalogo.objetos.Perfil;
import com.google.android.material.textfield.TextInputEditText;

public class PersonaFisicaActivity extends AppCompatActivity implements PersonaFisicaContract.View {

    private TextInputEditText nombre, apellidoPat, apellidoMat, tel, correo;
    private PersonaFisicaContract.Presenter mPresenter;
    private Perfil perfil;
    private String modo, rfc;
    public static final String TAG_RFC = "rfc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persona_fisica);
        Bundle loadInfo = getIntent().getExtras();
        modo = "";
        rfc = "";


        mPresenter = new PersonaFisicaPresenter(this);
        nombre = (TextInputEditText) findViewById(R.id.edittext_nombre);
        apellidoPat = (TextInputEditText) findViewById(R.id.edittext_apePat);
        apellidoMat = (TextInputEditText) findViewById(R.id.edittext_apeMat);
        tel = (TextInputEditText) findViewById(R.id.edittext_tel);
        correo = (TextInputEditText) findViewById(R.id.edittext_correo);

        if (loadInfo != null) {
            perfil = (Perfil) loadInfo.getSerializable(CatalogoActivity.TAG_PERFIL);
            modo = getIntent().getStringExtra(CatalogoActivity.TAG_MODO);
        }
        rfc = getIntent().getStringExtra(TAG_RFC);

        if (modo.equals(CatalogoActivity.TAG_EDITAR) || modo.equals(CatalogoActivity.TAG_VER)) {
            nombre.setText(perfil.getNombre());
            apellidoPat.setText(perfil.getApellidoPat());
            apellidoMat.setText(perfil.getApellidoMat());
            tel.setText(perfil.getNumero());
            correo.setText(perfil.getCorreo());
            if (modo.equals(CatalogoActivity.TAG_VER)) {
                nombre.setFocusable(false);
                apellidoMat.setFocusable(false);
                apellidoPat.setFocusable(false);
                tel.setFocusable(false);
                correo.setFocusable(false);
            }
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString( R.string.title_persona_fis));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                mPresenter.validarDatos(nombre.getText().toString(), apellidoPat.getText().toString(),
                        apellidoMat.getText().toString(), tel.getText().toString(), correo.getText().toString(), getApplicationContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void mostrarVistaDomicilio() {
        Perfil perfil1 = new Perfil();
        perfil1.setRfc(rfc);
        perfil1.setNombre(nombre.getText().toString());
        perfil1.setApellidoPat(apellidoPat.getText().toString());
        perfil1.setApellidoMat(apellidoMat.getText().toString());
        perfil1.setCorreo(correo.getText().toString());
        perfil1.setNumero(tel.getText().toString());
        perfil1.setTipo(CatalogoActivity.TAG_FISICA);

        if (modo.equals(CatalogoActivity.TAG_EDITAR) || (modo.equals(CatalogoActivity.TAG_VER))) {
            perfil1.setRfc(perfil.getRfc());
            perfil1.setPais(perfil.getPais());
            perfil1.setEstado(perfil.getEstado());
            perfil1.setMunicipio(perfil.getMunicipio());
            perfil1.setCiudad(perfil.getCiudad());
            perfil1.setCodgPostal(perfil.getCodgPostal());
            perfil1.setColonia(perfil.getColonia());
            perfil1.setCalle(perfil.getCalle());
            perfil1.setNumInte(perfil.getNumInte());
            perfil1.setNumExt(perfil.getNumExt());
        }


        Intent intent = new Intent(this, DomicilioActivity.class);
        intent.putExtra(CatalogoActivity.TAG_PERFIL, perfil1);
        intent.putExtra(CatalogoActivity.TAG_MODO, modo);

        startActivity(intent);
    }

    @Override
    public void msjError(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();

    }
}
