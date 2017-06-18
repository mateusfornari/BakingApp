package com.example.android.bakingapp.utilities;

import com.example.android.bakingapp.domain.Ingredient;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.domain.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mateus on 15/06/17.
 */

public class JsonUtils {

    private static final String FIELD_RECIPE_ID = "id";
    private static final String FIELD_RECIPE_NAME = "name";
    private static final String FIELD_RECIPE_INGREDIENTS = "ingredients";
    private static final String FIELD_RECIPE_STEPS = "steps";
    private static final String FIELD_RECIPE_IMAGE = "image";
    private static final String FIELD_RECIPE_SERVINGS = "servings";

    private static final String FIELD_INGREDIENT_DESCRIPTION = "ingredient";
    private static final String FIELD_INGREDIENT_QUANTITY = "quantity";
    private static final String FIELD_INGREDIENT_MEASURE = "measure";

    private static final String FIELD_STEP_ID = "id";
    private static final String FIELD_STEP_SHORT_DESCRIPTION = "shortDescription";
    private static final String FIELD_STEP_DESCRIPTION = "description";
    private static final String FIELD_STEP_VIDEO = "videoURL";
    private static final String FIELD_STEP_THUMBNAIL = "thumbnailURL";

    public static ArrayList<Recipe> fetchRecipeList(JSONArray array){

        ArrayList<Recipe> list = new ArrayList<>();

        try {
            for (int i = 0; i < array.length(); i++) {

                JSONObject o = array.getJSONObject(i);

                Recipe r = new Recipe();

                r.setId(o.getInt(FIELD_RECIPE_ID));
                r.setName(o.getString(FIELD_RECIPE_NAME));
                r.setImageUrl(o.getString(FIELD_RECIPE_IMAGE));
                r.setServings(o.getInt(FIELD_RECIPE_SERVINGS));

                r.setIngredients(new ArrayList<Ingredient>());
                r.setSteps(new ArrayList<RecipeStep>());

                JSONArray jsonIngredients = o.getJSONArray(FIELD_RECIPE_INGREDIENTS);

                for (int j = 0; j < jsonIngredients.length(); j++){
                    JSONObject json = jsonIngredients.getJSONObject(j);
                    Ingredient ingredient = new Ingredient();
                    filloutIngredient(ingredient, json);
                    r.getIngredients().add(ingredient);
                }

                JSONArray jsonSteps = o.getJSONArray(FIELD_RECIPE_STEPS);

                for (int j = 0; j < jsonSteps.length(); j++){
                    JSONObject json = jsonSteps.getJSONObject(j);
                    RecipeStep step = new RecipeStep();
                    filloutStep(step, json);
                    r.getSteps().add(step);
                }

                list.add(r);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void filloutIngredient(Ingredient ingredient, JSONObject json) throws JSONException {
        ingredient.setDescription(json.getString(FIELD_INGREDIENT_DESCRIPTION));
        ingredient.setMeasure(json.getString(FIELD_INGREDIENT_MEASURE));
        ingredient.setQuantity(json.getDouble(FIELD_INGREDIENT_QUANTITY));
    }

    private static void filloutStep(RecipeStep step, JSONObject json) throws JSONException {
        step.setDescription(json.getString(FIELD_STEP_DESCRIPTION));
        step.setId(json.getInt(FIELD_STEP_ID));
        step.setShortDescription(json.getString(FIELD_STEP_SHORT_DESCRIPTION));
        step.setThumbnailUrl(json.getString(FIELD_STEP_THUMBNAIL));
        step.setVideoUrl(json.getString(FIELD_STEP_VIDEO));
    }

}
