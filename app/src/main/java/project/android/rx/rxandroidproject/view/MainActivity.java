package project.android.rx.rxandroidproject.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import project.android.rx.rxandroidproject.R;
import project.android.rx.rxandroidproject.api.PokemonApi;
import project.android.rx.rxandroidproject.model.Pokemon;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView mPokemonSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildSearchAutocompleteBar();

        PokemonApi.getApi()
                .getPokemon("charmander")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Pokemon>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Pokemon", "Error :" + e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Pokemon pokemon) {
                         Log.d("Pokemon", pokemon.getSprites().getFrontDefault());
                    }
                });
    }

    public void buildSearchAutocompleteBar() {
        mPokemonSearchBar = (AutoCompleteTextView) findViewById(R.id.pokemon_search_bar);

        String[] pokemonNames = getResources().getStringArray(R.array.pokemon_name_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, pokemonNames);

        mPokemonSearchBar.setAdapter(adapter);
        mPokemonSearchBar.setOnItemClickListener((parent, view, position, id) -> {
            Log.i("Chose", adapter.getItem(position));
        });

        mPokemonSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("Typed", s.toString());
            }
        });
    }
}
