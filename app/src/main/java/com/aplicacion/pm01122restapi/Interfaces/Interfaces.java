package com.aplicacion.pm01122restapi.Interfaces;
import com.aplicacion.pm01122restapi.Models.Usuarios;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface Interfaces
{
    String Ruta0 = "/posts";
    String Ruta1 = "/posts/{valor}";

    @GET(Ruta0)
    Call<List<Usuarios>> getUsusuarios();

    @GET(Ruta1)
    Call<List<Usuarios>> getUsusuario(@Path("valor") String valor);
}
