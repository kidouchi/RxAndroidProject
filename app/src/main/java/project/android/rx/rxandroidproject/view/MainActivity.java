package project.android.rx.rxandroidproject.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import project.android.rx.rxandroidproject.R;
import project.android.rx.rxandroidproject.adapter.PokemonListViewAdapter;
import project.android.rx.rxandroidproject.api.PokemonApi;
import project.android.rx.rxandroidproject.model.Pokemon;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView mPokemonSearchBar;
    private ListView mPokemonSearchResults;
    private PokemonListViewAdapter mPokemonListViewAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildSearchAutocompleteBar();
        buildPokemonListView();
        mProgressBar = (ProgressBar) findViewById(R.id.pokemon_search_progress_bar);
    }

    public void retrievePokemons(String query) {
        PokemonApi.getApi()
                .getPokemon(query.toLowerCase())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnRequest(aLong -> mProgressBar.setVisibility(View.VISIBLE))
                .subscribe(new Subscriber<Pokemon>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Pokemon", "DONE");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Pokemon", "Error :" + e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Pokemon pokemon) {
                        mProgressBar.setVisibility(View.GONE);
                        Log.d("Pokemon", pokemon.getSprites().getFrontDefault());
                        mPokemonListViewAdapter.add(pokemon);
                        mPokemonListViewAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void buildPokemonListView() {
        mPokemonSearchResults = (ListView) findViewById(R.id.pokemon_search_results);
        mPokemonListViewAdapter = new PokemonListViewAdapter(this, new ArrayList<Pokemon>());
        mPokemonSearchResults.setAdapter(mPokemonListViewAdapter);
    }

    public void buildSearchAutocompleteBar() {
        mPokemonSearchBar = (AutoCompleteTextView) findViewById(R.id.pokemon_search_bar);

        String[] pokemonNames = getResources().getStringArray(R.array.pokemon_name_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, pokemonNames);

        // Set character threshold before starting autocomplete

        mPokemonSearchBar.setThreshold(2);
        mPokemonSearchBar.setAdapter(adapter);
        mPokemonSearchBar.setOnItemClickListener((parent, view, position, id) -> {
            Log.i("Chose", adapter.getItem(position));
            retrievePokemons(adapter.getItem(position));

            adapter.notifyDataSetChanged();
        });

//        RxAutoCompleteTextView.completionHint(mPokemonSearchBar).

        mPokemonSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPokemonListViewAdapter.clear();
                mPokemonListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("Typed", s.toString());
            }
        });
    }
}
