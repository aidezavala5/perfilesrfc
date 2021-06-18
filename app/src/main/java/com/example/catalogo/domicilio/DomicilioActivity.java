package com.example.catalogo.domicilio;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.catalogo.R;
import com.example.catalogo.adapter.PerfilesAdapter;
import com.example.catalogo.inicio.CatalogoActivity;
import com.example.catalogo.inicio.CatalogoContract;
import com.example.catalogo.inicio.CatalogoPresenter;
import com.example.catalogo.objetos.Perfil;
import com.google.android.material.textfield.TextInputEditText;

import io.realm.Realm;

public class DomicilioActivity extends AppCompatActivity implements DomicilioContract.View {


    private Perfil perfil;
    private TextInputEditText pais, estado, ciudad, municipio, codgPostal, colonia, calle, numInt, numExt;
    private Realm realm;
    private String modo = "";
    private DomicilioContract.Presenter mPresenter;
    private ImageView siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.domicilio);
        mPresenter = new DomicilioPresenter(this);
        Bundle loadInfo = getIntent().getExtras();
        perfil = (Perfil) loadInfo.getSerializable(CatalogoActivity.TAG_PERFIL);


        realm = Realm.getDefaultInstance();
        pais = (TextInputEditText) findViewById(R.id.edittext_paiz);
        estado = (TextInputEditText) findViewById(R.id.edittext_estado);
        municipio = (TextInputEditText) findViewById(R.id.edittext_delegacion);
        ciudad = (TextInputEditText) findViewById(R.id.edittext_ciudad);
        codgPostal = (TextInputEditText) findViewById(R.id.edittext_codgPos);
        colonia = (TextInputEditText) findViewById(R.id.edittext_col);
        calle = (TextInputEditText) findViewById(R.id.edittext_calle);
        numExt = (TextInputEditText) findViewById(R.id.edittext_numEx);
        numInt = (TextInputEditText) findViewById(R.id.edittext_numInt);
        modo = getIntent().getStringExtra(CatalogoActivity.TAG_MODO);
        if (modo.equals(CatalogoActivity.TAG_EDITAR) || modo.equals(CatalogoActivity.TAG_VER)) {
            pais.setText(perfil.getPais());
            estado.setText(perfil.getEstado());
            municipio.setText(perfil.getMunicipio());
            ciudad.setText(perfil.getCiudad());
            codgPostal.setText(perfil.getCodgPostal());
            colonia.setText(perfil.getColonia());
            calle.setText(perfil.getCalle());
            numExt.setText(perfil.getNumExt());
            numInt.setText(perfil.getNumInte());

            if (modo.equals(CatalogoActivity.TAG_VER)) {
                pais.setFocusable(false);
                estado.setFocusable(false);
                municipio.setFocusable(false);
                ciudad.setFocusable(false);
                codgPostal.setFocusable(false);
                colonia.setFocusable(false);
                calle.setFocusable(false);
                calle.setFocusable(false);
                numExt.setFocusable(false);
                numInt.setFocusable(false);
            }
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_dom);
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
                //Guardar datos
                if(mPresenter.validarDatos(pais.getText().toString(), estado.getText().toString(),municipio.getText().toString(),ciudad.getText().toString(),
                        colonia.getText().toString(),codgPostal.getText().toString(),calle.getText().toString(),getApplicationContext())) {
                    perfil.setPais(pais.getText().toString());
                    perfil.setEstado(estado.getText().toString());
                    perfil.setMunicipio(municipio.getText().toString());
                    perfil.setCiudad(ciudad.getText().toString());
                    perfil.setCodgPostal(codgPostal.getText().toString());
                    perfil.setColonia(colonia.getText().toString());
                    perfil.setCalle(calle.getText().toString());
                    perfil.setNumExt(numExt.getText().toString());
                    perfil.setNumInte(numInt.getText().toString());
                    mPresenter.guardarDatos(perfil, realm, modo, getApplicationContext());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public void msj(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }

    @Override
    public void vistaPrincipal() {
        Intent intent = new Intent(this, CatalogoActivity.class);
        startActivity(intent);
    }
}
