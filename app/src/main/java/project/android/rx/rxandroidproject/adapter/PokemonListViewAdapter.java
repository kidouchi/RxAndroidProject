package project.android.rx.rxandroidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import project.android.rx.rxandroidproject.R;
import project.android.rx.rxandroidproject.model.Pokemon;

/**
 * Created by iuy407 on 3/19/16.
 */
public class PokemonListViewAdapter extends ArrayAdapter<Pokemon> {

    private static class ViewHolder {
        TextView name;
        ImageView sprite;
    }

    public PokemonListViewAdapter(Context context, ArrayList<Pokemon> pokemons) {
        super(context, R.layout.pokemon_list_item, pokemons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pokemon pokemon = getItem(position);

        ViewHolder viewHolder;
        // Check if existing view is being reused
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.pokemon_list_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.pokemon_name_text_view);
            viewHolder.sprite = (ImageView) convertView.findViewById(R.id.pokemon_sprite_image_view);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return super.getView(position, convertView, parent);

    }
}
