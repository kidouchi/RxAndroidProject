package project.android.rx.rxandroidproject.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iuy407 on 3/18/16.
 */
public class PokemonApi {

    private static String endpoint = "http://pokeapi.co/";

    public static PokemonService getApi() {
        return new Retrofit.Builder().baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(PokemonService.class);
    }

}

