package project.android.rx.rxandroidproject.api;

import project.android.rx.rxandroidproject.model.Pokemon;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by iuy407 on 3/18/16.
 */
public interface PokemonService {

    @GET("api/v2/pokemon/{id}")
    Observable<Pokemon> getPokemon(@Path("id") int id);

    @GET("api/v2/pokemon/{name}")
    Observable<Pokemon> getPokemon(@Path("name") String name);
}
