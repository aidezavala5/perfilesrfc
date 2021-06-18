package com.example.catalogo.utils;

import com.example.catalogo.inicio.CatalogoActivity;
import com.example.catalogo.objetos.Perfil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ValidadorRfc {


    public static int validarRfc(String nombre, Realm realm) {
        int numero = 0;
        //Persona moral
        if (nombre.length() == 12) {
            //Buscarlo primero en la BD
            RealmResults<Perfil> mPerfil = realm.where(Perfil.class).like(CatalogoActivity.TAG_RFC, nombre, Case.INSENSITIVE)
                    .findAll().sort(CatalogoActivity.TAG_NOMBRE, Sort.DESCENDING);
            if (mPerfil.size() == 0) {
            //validar el rfc
                Pattern pat = Pattern.compile("^(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))");
                Matcher mat = pat.matcher(nombre);
                if (mat.matches()) {
                    numero = 1;
                }
            } else {
                numero = 3;
            }

        }//persona fisica
        else if (nombre.length() == 13) {
            RealmResults<Perfil> mPerfil = realm.where(Perfil.class).like(CatalogoActivity.TAG_RFC, nombre, Case.INSENSITIVE)
                    .findAll().sort(CatalogoActivity.TAG_NOMBRE, Sort.DESCENDING);
            if (mPerfil.size() == 0) {
                Pattern pat = Pattern.compile("^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))((-)?([A-Z\\d]{3}))?$");
                Matcher mat = pat.matcher(nombre);
                if (mat.matches()) {
                    numero = 2;
                }
            } else {
                numero = 3;
            }

        }
        return numero;
    }

}
