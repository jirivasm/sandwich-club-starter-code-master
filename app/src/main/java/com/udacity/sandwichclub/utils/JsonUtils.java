package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        if (json.isEmpty())
            return null;

        try {
            //Getting the Object of the Sandwich JSON
            JSONObject sandichObjects = new JSONObject(json);

            //getting the sandwich details
            JSONObject sandwichDetails = sandichObjects.getJSONObject("name");

            //getting the main name
            String mainName = sandwichDetails.getString("mainName");
            sandwich.setMainName(mainName);
            //getting the known as array
            JSONArray knownAs = sandwichDetails.getJSONArray("alsoKnownAs");

            List<String> alsoKnown = new ArrayList<>();
            //if the list is empty also Known is the same as the Main name
            if(knownAs.length() == 0) {
                alsoKnown.add(mainName);
            }
            else {
                for (int i = 0; i < knownAs.length(); i++) {
                    String names = knownAs.getString(i);
                    alsoKnown.add(names);
                }
            }
            sandwich.setAlsoKnownAs(alsoKnown);
            //getting the place of origin
            String origin = sandichObjects.optString("placeOfOrigin","Unknown");
            //if its empty set it to unknown
            if(origin.length() == 0)
                origin = "Unknown";

            sandwich.setPlaceOfOrigin(origin);
            //getting the description
            String description = sandichObjects.getString("description");
            sandwich.setDescription(description);

            //getting the image
            String image = sandichObjects.getString("image");
            sandwich.setImage(image);
            //getting the ingredients List<String>
            List<String> ingredientsList = new ArrayList<>();

            JSONArray ingredientsArray = sandichObjects.getJSONArray("ingredients");
            for (int i = 0;i<ingredientsArray.length();i++)
            {
                String ingredients = ingredientsArray.getString(i);
                ingredientsList.add(ingredients);
            }
            sandwich.setIngredients(ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
