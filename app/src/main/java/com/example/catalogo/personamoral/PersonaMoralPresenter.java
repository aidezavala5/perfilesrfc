package com.example.catalogo.personamoral;

import android.content.Context;

import com.example.catalogo.R;
import com.example.catalogo.personafisica.PersonaFisicaContract;

public class PersonaMoralPresenter implements PersonaMoralContract.Presenter {

    private PersonaMoralContract.View mView;

    public PersonaMoralPresenter(PersonaMoralContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void validarDatos(String nombre, String tel, String correo, Context context) {
        if(!nombre.equals("")  && !tel.equals("") && !correo.equals("")){
            mView.mostrarVistaDomicilio();
        }else{
            mView.msjError(context.getResources().getString(R.string.llenar_campos));
        }
    }
}
