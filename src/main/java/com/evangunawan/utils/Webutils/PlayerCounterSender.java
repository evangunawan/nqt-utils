package com.evangunawan.utils.Webutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//The Utility Tool to send player list to the server.
public class PlayerCounterSender extends Thread {

    private URL url;
    private String playerCountParameter;
    private HttpURLConnection urlConn;
    private Integer updateInterval;
    private boolean debugMode;
    private DataOutputStream wr;

    public PlayerCounterSender(FileConfiguration c){
        String targetUrl = c.getString("web-connections.route-url");
        this.playerCountParameter = c.getString("web-connections.data-params");
        this.updateInterval = c.getInt("web-connections.post-interval");
        this.debugMode = c.getBoolean("web-connections.debug");

        try {
            if(targetUrl!=null) this.url = new URL(targetUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Bukkit.getServer().getLogger().info(ChatColor.YELLOW + "Starting Auto Post Engine...");
        this.start();
    }

    private boolean sendDataToServer(Integer data){
        String parameters = "&" + playerCountParameter + "=" + data.toString();
        byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);

        if(debugMode) System.out.println("Sending data to server: " + parameters);
        try {
            urlConn = (HttpURLConnection)url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setDoOutput(true);

            wr = new DataOutputStream(urlConn.getOutputStream());
            wr.write(postData);
            wr.close();
            urlConn.getResponseCode();
            return true;

        } catch (IOException e) {
            Bukkit.getServer().getLogger().warning("ERROR: " + e.getMessage());
            Bukkit.getServer().getLogger().warning(ChatColor.RED + "Auto Post is stopping...");
            return false;
        }
    }

    @Override
    public void run() {
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(!sendDataToServer(Bukkit.getServer().getOnlinePlayers().size())){
                    t.cancel();
                    t.purge();
                }
            }
        };
        t.schedule(task, new Date(), updateInterval*1000);

    }
}
