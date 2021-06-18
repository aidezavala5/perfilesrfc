package com.example.catalogo.personafisica;

import android.content.Context;

public class PersonaFisicaContract {

    interface View {
       void mostrarVistaDomicilio();
       void msjError(String msj);
    }

    interface Presenter {
      void validarDatos(String nombre, String apellPat, String apellMat, String tel, String correo, Context context);

    }
}
