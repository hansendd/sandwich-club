package com.udacity.sandwichclub.utils;

import android.util.JsonReader;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        // Documentation found at: https://developer.android.com/reference/org/json/JSONObject
        //                         https://developer.android.com/reference/org/json/JSONArray
        //                         https://developer.android.com/reference/org/json/JSONException
        JSONObject primaryJsonObject = new JSONObject(json);
        JSONObject nameJsonObject = getJSONObject(primaryJsonObject, "name");
        String mainName = getJSONObjectValueString(nameJsonObject, "mainName");
        JSONArray alsoKnownAsArray = getJSONObjectValueJSONArray(nameJsonObject, "alsoKnownAs");
        List<String> alsoKnownAsList = createListOfStringsFromJSONArray(alsoKnownAsArray);
        String placeOfOrigin = getJSONObjectValueString(primaryJsonObject, "placeOfOrigin");
        String description = getJSONObjectValueString(primaryJsonObject, "description");
        String image = getJSONObjectValueString(primaryJsonObject, "image");
        JSONArray ingredientsArray = getJSONObjectValueJSONArray(primaryJsonObject, "ingredients");
        List<String> ingredientList = createListOfStringsFromJSONArray(ingredientsArray);
        return new Sandwich(mainName,
                            alsoKnownAsList,
                            placeOfOrigin,
                            description,
                            image,
                            ingredientList);
    }

    private static JSONObject getJSONObject(JSONObject jsonObject,
                                            String objectName) throws JSONException {
        return jsonObject.getJSONObject(objectName);
    }

    private static String getJSONObjectValueString(JSONObject jsonObject,
                                                   String name) throws JSONException {
        return jsonObject.getString(name);
    }

    private static JSONArray getJSONObjectValueJSONArray(JSONObject jsonObject,
                                                         String name) throws JSONException {
        return jsonObject.getJSONArray(name);
    }

    private static List<String> createListOfStringsFromJSONArray(JSONArray jsonArray) throws JSONException {
        List<String> listOfStrings = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            listOfStrings.add((String)jsonArray.get(i));
        }
        return listOfStrings;
    }
}
