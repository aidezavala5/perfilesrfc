package com.example.catalogo.personamoral;

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
import com.example.catalogo.personafisica.PersonaFisicaContract;
import com.example.catalogo.personafisica.PersonaFisicaPresenter;
import com.google.android.material.textfield.TextInputEditText;

public class PersonaMoralActivity extends AppCompatActivity implements PersonaMoralContract.View {

    private TextInputEditText razonSocial, tel, correo;
    private Perfil perfil;
    private  String modo;
    public static final String TAG_RFC = "rfc";
    private PersonaMoralContract.Presenter mPresenter;
    public static final String TAG_MORAL = "moral";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PersonaMoralPresenter(this);
        setContentView(R.layout.persona_moral);
        Bundle loadInfo = getIntent().getExtras();
        modo="";


        razonSocial= (TextInputEditText) findViewById(R.id.edittext_razSoc);
        tel= (TextInputEditText) findViewById(R.id.edittext_Tel);
        correo =(TextInputEditText) findViewById(R.id.edittext_correo);

        if(loadInfo!=null){
            perfil = (Perfil) loadInfo.getSerializable(CatalogoActivity.TAG_PERFIL);
            modo= getIntent().getStringExtra(CatalogoActivity.TAG_MODO);
        }


        if(modo.equals(CatalogoActivity.TAG_EDITAR)||modo.equals(CatalogoActivity.TAG_VER)){
            razonSocial.setText(perfil.getNombre());
            tel.setText(perfil.getNumero());
            correo.setText(perfil.getCorreo());
            if(modo.equals(CatalogoActivity.TAG_VER)){
                razonSocial.setFocusable(false);
                tel.setFocusable(false);
                correo.setFocusable(false);
            }
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_moral);
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
                mostrarVistaDomicilio();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void mostrarVistaDomicilio() {
        Perfil perfil1 = new Perfil();
        perfil1.setNombre(razonSocial.getText().toString());
        perfil1.setTipo(TAG_MORAL);
        perfil1.setCorreo(correo.getText().toString());
        perfil1.setNumero(tel.getText().toString());

        if((modo.equals(CatalogoActivity.TAG_EDITAR))||(modo.equals(CatalogoActivity.TAG_VER))){
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
        intent.putExtra(CatalogoActivity.TAG_MODO,modo);

        startActivity(intent);
    }

    @Override
    public void msjError(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }
}
