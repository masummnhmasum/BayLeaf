package com.lechef.bayleaf.jsonparser;

import android.util.Log;
import android.widget.Toast;

import com.lechef.bayleaf.datamodel.CategoryInfo;
import com.lechef.bayleaf.singleton.AppData;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by masum on 10/03/2015.
 */
public class JsonParser {
    public static int categoryParser(String response) throws JSONException {
        Log.i("JSON CAT  RESPONSE", "****" + response);
        String x = response;

        JSONArray jsonArray = new JSONArray(response);

        JSONArray jsonCategoryArray = jsonArray.getJSONObject(0).getJSONArray("dishCategories");
        int lengthCategoryArray = jsonCategoryArray.length();
        for (int i = 0; i < lengthCategoryArray; i++) {
            CategoryInfo categoryInfo = new CategoryInfo();
            categoryInfo.setCatId(jsonCategoryArray.getJSONObject(i).getInt("id"));
            categoryInfo.setCatName(jsonCategoryArray.getJSONObject(i).getString("name"));
            Log.i("Category Name", "****" + jsonCategoryArray.getJSONObject(i).getString("name"));
            categoryInfo.setDescription(jsonCategoryArray.getJSONObject(i).getString("description"));
            categoryInfo.setStatus(jsonCategoryArray.getJSONObject(i).getInt("status"));
            AppData.getInstance().getCategoryInfo().add(categoryInfo);
        }

        return 1;
    }
}
