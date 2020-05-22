package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mIngredientsIv;

    private TextView mAlsoKnowAsLabel;
    private TextView mAlsoKnowAsTv;

    private TextView mPlaceOfOriginLabel;
    private TextView mOriginTv;

    private TextView mDescriptionTv;
    private TextView mIngredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mIngredientsIv = findViewById(R.id.image_iv);
        mAlsoKnowAsTv = findViewById(R.id.also_known_tv);
        mAlsoKnowAsLabel = findViewById(R.id.also_knows_label_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mOriginTv = findViewById(R.id.origin_tv);
        mPlaceOfOriginLabel = findViewById(R.id.place_of_origin_label_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);

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
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void hideOrigin(){
        mOriginTv.setVisibility(View.GONE);
        mPlaceOfOriginLabel.setVisibility(View.GONE);
    }
    private void showOrigin(){
        mOriginTv.setVisibility(View.VISIBLE);
        mPlaceOfOriginLabel.setVisibility(View.VISIBLE);
    }
    private void hideAlsoKnownAs(){
        mAlsoKnowAsTv.setVisibility(View.GONE);
        mAlsoKnowAsLabel.setVisibility(View.GONE);
    }
    private void showAlsoKnownAs(){
        mAlsoKnowAsTv.setVisibility(View.VISIBLE);
        mAlsoKnowAsLabel.setVisibility(View.VISIBLE);
    }

    private void populateUI(Sandwich sandwich) {

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mIngredientsIv);

        mDescriptionTv.setText(sandwich.getDescription());

        if (sandwich.getPlaceOfOrigin().isEmpty()){
            hideOrigin();
        } else {
            showOrigin();
            mOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        List<String> knownAsList = sandwich.getAlsoKnownAs();
        if (knownAsList.isEmpty()){
            hideAlsoKnownAs();
        } else {
            showAlsoKnownAs();
            for (int i=0; i < knownAsList.size(); i++){
                mAlsoKnowAsTv.append(knownAsList.get(i));
                if (i != (knownAsList.size()-1)){
                    mAlsoKnowAsTv.append(", ");
                }

            }
        }

        List<String> ingredientsList = sandwich.getIngredients();
        for (String ingredient : ingredientsList){
            mIngredientsTv.append(ingredient + "\n");
        }
    }
}
