package com.roman.batsu.utils;

import com.roman.batsu.ui.model.Rings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class JSONReader {

    /*
     * Read jsonFile from assets folder
     * call this:
     * inputStream = getResources().openRawResource(R.raw.file_json);
     * String jsonTaxiString = jsonReader.readJsonDataFromFile(getMyInputStream());
     */
    private List<Rings> jsonList = new ArrayList<>();

    private String readJsonDataFromFile(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        try {
            String jsonDataString;
            BufferedReader bufferedReader = new BufferedReader
                    (new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }

    public List<Rings> createListJson(InputStream inputStream) {
        try {
            String jsonTaxiString = this.readJsonDataFromFile(inputStream);
            JSONArray menuTaxiJsonArray = new JSONArray(jsonTaxiString);

            for (int i = 0; i < menuTaxiJsonArray.length(); ++i) {
                JSONObject taxiItemObject = menuTaxiJsonArray.getJSONObject(i);
                String title = taxiItemObject.getString("title");
                String description = taxiItemObject.getString("description");
                Rings itemCatalog = new Rings(title, description);
                jsonList.add(itemCatalog);
            }
        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
        return jsonList;
    }

}