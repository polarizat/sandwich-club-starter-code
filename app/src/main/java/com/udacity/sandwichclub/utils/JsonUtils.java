package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String KEY_NAME = "name";
    private final static String KEY_MAIN_NAME = "mainName";
    private final static String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String KEY_INGREDIENTS = "ingredients";
    private final static String KEY_DESCRIPTION = "description";
    private final static String KEY_ORIGIN = "placeOfOrigin";
    private final static String KEY_IMAGE = "image";

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject jsonSandwich = new JSONObject(json);

            JSONObject sandwichNameObj = jsonSandwich.getJSONObject(KEY_NAME);

            String mainName = sandwichNameObj.getString(KEY_MAIN_NAME);
            String ingredientsImage = jsonSandwich.getString(KEY_IMAGE);
            String placeOfOrigin = jsonSandwich.getString(KEY_ORIGIN);
            String description = jsonSandwich.getString(KEY_DESCRIPTION);

            List<String> alsoKnowAsList = new ArrayList<>();
            JSONArray alsoKnowAs = sandwichNameObj.getJSONArray(KEY_ALSO_KNOWN_AS);
            for (int i = 0; i < alsoKnowAs.length(); i++) {
                try {
                    String knowAsElement = alsoKnowAs.getString(i);
                    alsoKnowAsList.add(knowAsElement);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            List<String> ingredientsList = new ArrayList<>();
            JSONArray ingredientsJson = jsonSandwich.getJSONArray(KEY_INGREDIENTS);
            for (int i = 0; i < ingredientsJson.length(); i++) {
                try {
                    String ingredient = ingredientsJson.getString(i);
                    ingredientsList.add(ingredient);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return new Sandwich(
                    mainName,
                    alsoKnowAsList,
                    placeOfOrigin,
                    description,
                    ingredientsImage,
                    ingredientsList
            );

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
