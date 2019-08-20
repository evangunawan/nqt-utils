import org.bukkit.Bukkit;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HttpPostSend {
    private static String params = "&player_count=";
    private static int data = 11;
    private static HttpURLConnection urlConn;
    private static DataOutputStream wr;
    public static void main(String[] args) {
        String targetUrl = "http://localhost/api/update_player_count";
//        int postDataLen = postData.length;
        try{
            URL url = new URL(targetUrl);

            Timer t = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    String paramsSent = params + data;
                    byte[] postData = paramsSent.getBytes(StandardCharsets.UTF_8);
                    System.out.println("Sending data to server: " + paramsSent);
                    try {
                        urlConn = (HttpURLConnection)url.openConnection();
                        urlConn.setRequestMethod("POST");
                        urlConn.setDoOutput(true);

                        wr = new DataOutputStream(urlConn.getOutputStream());
                        wr.write(postData);
                        wr.close();
                        System.out.println(urlConn.getResponseCode());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    data+=1;
                }
            };
            t.schedule(task, new Date(), 3000);

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

}
