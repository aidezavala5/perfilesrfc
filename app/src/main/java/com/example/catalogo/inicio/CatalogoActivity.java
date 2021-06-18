package com.example.catalogo.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catalogo.R;
import com.example.catalogo.adapter.PerfilesAdapter;
import com.example.catalogo.agregarperfil.ValidarRfcActivity;
import com.example.catalogo.objetos.Perfil;
import com.example.catalogo.personafisica.PersonaFisicaActivity;
import com.example.catalogo.personamoral.PersonaMoralActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;
import io.realm.RealmResults;

public class CatalogoActivity extends AppCompatActivity implements PerfilesAdapter.OnItemClickListener, CatalogoContract.View {

    private MenuItem searchMenuItem;
    Realm realm;
    private RecyclerView recyclerView;
    private CatalogoContract.Presenter mPresenter;
    private TextView noPerfiles;
    public static final String TAG_ELIMINAR = "eliminar";
    public static final String TAG_VER = "ver";
    public static final String TAG_EDITAR = "editar";
    public static final String TAG_FISICA = "fisica";
    public static final String TAG_MODO = "modo";
    public static final String TAG_PERFIL = "personafis";
    public static final String TAG_NOMBRE = "nombre";
    public static final String TAG_RFC = "rfc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new CatalogoPresenter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_principal);
        noPerfiles = (TextView) findViewById(R.id.noPerfiles);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_perfiles));
        setSupportActionBar(toolbar);
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogoActivity.this, ValidarRfcActivity.class);
                startActivity(intent);
            }
        });
        mPresenter.getPerfiles(realm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        android.widget.SearchView searchView = (android.widget.SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(searchListener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_todos:
                mPresenter.getPerfiles(realm);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onItemClickListener(Perfil perfil, String tipo) {
        if (tipo.equals(TAG_ELIMINAR)) {
            mPresenter.eliminarPerfil(perfil, realm, getApplicationContext());
            mPresenter.getPerfiles(realm);

        } else if (tipo.equals(TAG_VER) || (tipo.equals(TAG_EDITAR))) {
            Perfil perfil1 = new Perfil();
            perfil1.setRfc(perfil.getRfc());
            perfil1.setNombre(perfil.getNombre());
            perfil1.setCorreo(perfil.getCorreo());
            perfil1.setNumero(perfil.getNumero());
            perfil1.setTipo(perfil.getTipo());
            perfil1.setPais(perfil.getPais());
            perfil1.setEstado(perfil.getEstado());
            perfil1.setMunicipio(perfil.getMunicipio());
            perfil1.setCiudad(perfil.getCiudad());
            perfil1.setCodgPostal(perfil.getCodgPostal());
            perfil1.setColonia(perfil.getColonia());
            perfil1.setCalle(perfil.getCalle());
            perfil1.setNumExt(perfil.getNumExt());
            perfil1.setNumInte(perfil.getNumInte());
            if (perfil.getTipo().equals(TAG_FISICA)) {
                perfil1.setApellidoPat(perfil.getApellidoPat());
                perfil1.setApellidoMat(perfil.getApellidoMat());

                if (tipo.equals(TAG_EDITAR)) {
                    Intent intent = new Intent(getApplicationContext(), PersonaFisicaActivity.class);
                    intent.putExtra(TAG_MODO, TAG_EDITAR);
                    intent.putExtra(TAG_PERFIL, perfil1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), PersonaFisicaActivity.class);
                    intent.putExtra(TAG_MODO, TAG_VER);
                    intent.putExtra(TAG_PERFIL, perfil1);
                    startActivity(intent);
                }
            } else {
                if (tipo.equals(TAG_EDITAR)) {
                    Intent intent = new Intent(CatalogoActivity.this, PersonaMoralActivity.class);
                    intent.putExtra(TAG_MODO, TAG_EDITAR);
                    intent.putExtra(TAG_PERFIL, perfil1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CatalogoActivity.this, PersonaMoralActivity.class);
                    intent.putExtra(TAG_MODO, TAG_VER);
                    intent.putExtra(TAG_PERFIL, perfil1);
                    startActivity(intent);
                }

            }


        }


    }


    private android.widget.SearchView.OnQueryTextListener searchListener = new android.widget.SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String nombre) {
            searchMenuItem.collapseActionView();
            //  recyclerView.setVisibility(View.GONE);
            mPresenter.buscarPersona(nombre, realm, getApplicationContext());
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };


    @Override
    public void mostrarPerfiles(RealmResults<Perfil> perfil) {
        noPerfiles.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(CatalogoActivity.this, 1);
        recyclerView.setLayoutManager(mLayoutManager);

        PerfilesAdapter perfilesAdapter = new PerfilesAdapter(perfil, CatalogoActivity.this, getApplicationContext());
        recyclerView.setAdapter(perfilesAdapter);

    }

    @Override
    public void noPerfiles() {
        recyclerView.setVisibility(View.GONE);
        noPerfiles.setText(getResources().getString(R.string.no_perfiles));
        noPerfiles.setVisibility(View.VISIBLE);

    }

    @Override
    public void mostrarMsj(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }
}
