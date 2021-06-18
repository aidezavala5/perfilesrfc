package com.example.catalogo.agregarperfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.catalogo.R;
import com.example.catalogo.inicio.CatalogoActivity;
import com.example.catalogo.personafisica.PersonaFisicaActivity;
import com.example.catalogo.personamoral.PersonaMoralActivity;
import com.example.catalogo.utils.ValidadorRfc;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import io.realm.Realm;

public class ValidarRfcActivity extends AppCompatActivity implements ValidarRfcContract.View {

    private TextInputEditText rfc;
    private Button aceptar;
    private ValidarRfcContract.Presenter mPresenter;
    Realm realm;
    public static final String TAG_RFC = "rfc";
    public static final String TAG_AGREGAR = "add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rfc);
        mPresenter = new ValidarRfcPresenter(this);
        rfc = (TextInputEditText) findViewById(R.id.edittext_rfc);
        aceptar = (Button) findViewById(R.id.bottom_aceptar);
        realm = Realm.getDefaultInstance();

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.validarRfc(rfc.getText().toString(), realm, getApplicationContext());

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.title_agregar);
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void vistaPersonaMoral() {

        Intent intent = new Intent(ValidarRfcActivity.this, PersonaMoralActivity.class);
        intent.putExtra(TAG_RFC, rfc.getText().toString().toUpperCase());
        intent.putExtra(CatalogoActivity.TAG_MODO, TAG_AGREGAR);
        startActivity(intent);
    }

    @Override
    public void vistaPersonaFisica() {
        Intent intent = new Intent(ValidarRfcActivity.this, PersonaFisicaActivity.class);
        intent.putExtra(TAG_RFC, rfc.getText().toString().toUpperCase());
        intent.putExtra(CatalogoActivity.TAG_MODO, TAG_AGREGAR);
        startActivity(intent);
    }

    @Override
    public void msjError(String msj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.title_error));
        builder.setMessage(msj);
        builder.setPositiveButton(R.string.title_aceptar, null);
        builder.create();
        builder.show();
    }
}
