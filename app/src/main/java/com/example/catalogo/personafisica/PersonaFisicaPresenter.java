package com.example.catalogo.personafisica;

import android.content.Context;

import com.example.catalogo.R;


public class PersonaFisicaPresenter implements PersonaFisicaContract.Presenter {

    private PersonaFisicaContract.View mView;

    public PersonaFisicaPresenter(PersonaFisicaContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void validarDatos(String nombre, String apellPat, String apellMat, String tel, String correo, Context context) {
        if(!nombre.equals("") && !apellPat.equals("") && !apellMat.equals("") && !tel.equals("") && !correo.equals("")){
            mView.mostrarVistaDomicilio();
        }else{
            mView.msjError(context.getResources().getString(R.string.llenar_campos));
        }
    }
}
