package me.oma.omaBackdoor.payloads;

import java.io.*;
import java.net.URL;

public class Personal
{
    public String getIP() throws IOException
    {
        try
        {
            URL url = new URL("http://checkip.amazonaws.com");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    url.openStream()));

            return br.readLine();
        }
        catch (Exception e)
        {
            return "Fail";
        }
    }
}
