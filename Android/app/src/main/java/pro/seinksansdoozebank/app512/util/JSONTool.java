package pro.seinksansdoozebank.app512.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class JSONTool {

    public static boolean savePurchaseToJSON(String fileName, Context context, int carId, String name, String lastName, String date, double latitude, double longitude) {
        JSONObject root = JSONTool.readJSON(context, fileName);
        JSONArray jsonArray = root.optJSONArray("purchases");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("carId", carId);
            jsonObject.put("name", name);
            jsonObject.put("lastName", lastName);
            jsonObject.put("date", date);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
            assert jsonArray != null;
            jsonArray.put(jsonObject);
            root.put("purchases", jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String jsonString = root.toString();
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (IOException fileNotFound) {
            return false;
        }
    }

    public static JSONObject readJSON(Context context, String fileName) {

        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                System.out.println(line);
            }
        } catch (IOException fileNotFound) {
            throw new RuntimeException(fileNotFound);
        }

        try {
            JSONObject obj = new JSONObject(sb.toString());
            JSONArray arr = obj.getJSONArray("purchases");
            for (int i = 0; i < arr.length(); i++)
            {
                String name = arr.getJSONObject(i).getString("name");
                System.out.println(name);
            }
            return obj;
        } catch (JSONException fileNotFound) {
            throw new RuntimeException(fileNotFound);
        }
    }
}
