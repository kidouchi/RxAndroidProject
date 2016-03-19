package project.android.rx.rxandroidproject.api;

import retrofit2.Retrofit;

/**
 * Created by iuy407 on 3/18/16.
 */
public class PokemonApi {

    public static PokemonService getApi() {
        return new Retrofit.Builder().
                baseUrl("http://pokeapi.co/api/v2/")
                .build()
                .create(PokemonService.class);
    }

}
