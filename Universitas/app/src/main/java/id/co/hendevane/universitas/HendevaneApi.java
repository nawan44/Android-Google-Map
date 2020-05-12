package id.co.hendevane.universitas;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface HendevaneApi {

    Retrofit service = new Retrofit.Builder()
            .baseUrl("http://suksesit.id/hendevane/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("universities")
    Call<JsonArray> universities();

}
