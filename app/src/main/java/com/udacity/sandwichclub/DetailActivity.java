package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView knownAsTv = findViewById(R.id.known_as_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView originTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);

        List<String> knownAs = sandwich.getAlsoKnownAs();
        List<String> Ingredients = sandwich.getIngredients();
        String known = "Known As: ";
        for (int i = 0; i < knownAs.size(); i++) {
            known += knownAs.get(i);
            if (i < knownAs.size() - 1)
                known += ",\t\t";
        }
        knownAsTv.setText(known);

        String ing = "Ingredients:\n-";
        for (int i = 0; i < Ingredients.size(); i++) {
            ing += Ingredients.get(i);
            if (i < Ingredients.size() - 1)
                ing += "\n" + "-";
        }
        ingredientsTv.setText(ing);

       String ori = "Place of Origin: ";
        originTv.setText(ori +sandwich.getPlaceOfOrigin());

        String description = "Description: ";
        descriptionTv.setText(description + sandwich.getDescription());


    }
}
