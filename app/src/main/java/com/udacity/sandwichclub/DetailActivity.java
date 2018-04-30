package com.udacity.sandwichclub;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            // Learned from: https://developer.android.com/guide/topics/resources/string-resource#FormattingAndStyling
            String message = getString(R.string.parse_error_message, e.getMessage());
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
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
        final TextView sandwichPlaceOfOrigin = findViewByIdTextView(R.id.origin_tv);
        final TextView sandwichDescription = findViewByIdTextView(R.id.description_tv);
        final TextView sandwichIngredients = findViewByIdTextView(R.id.ingredients_tv);
        final TextView sandwichAlsoKnownAs = findViewByIdTextView(R.id.also_known_tv);

        sandwichPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        sandwichDescription.setText(sandwich.getDescription());
        sandwichIngredients.setText(createCommaDelimitedListFromListOfStrings(sandwich.getIngredients()));
        sandwichAlsoKnownAs.setText(createCommaDelimitedListFromListOfStrings(sandwich.getAlsoKnownAs()));
    }

    private TextView findViewByIdTextView(int id) {
        return (TextView) findViewById(id);
    }

    private String createCommaDelimitedListFromListOfStrings(List<String> listOfStrings) {
        String commaDelimitedList = "";
        for (String s : listOfStrings) {
            commaDelimitedList += s;
            if (!s.equals(listOfStrings.get(listOfStrings.size()-1))) {
                commaDelimitedList += ", ";
            }
        }
        return commaDelimitedList;
    }
}
