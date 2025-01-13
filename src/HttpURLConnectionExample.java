import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionExample {
    private static final String API_URL = "https://jsonplaceholder.typicode.com/albums";

    public static void main(String[] args) {
        try{
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) !=null){
                    response.append(line).append("\n");
                }
                reader.close();

                System.out.println("Response from API:");
                System.out.println(response);

                writeFile("albums_httpurlconncetion.json", response.toString());
            }else{
                System.out.println("Failed " + responseCode);
            }
            connection.disconnect();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private static void writeFile(String fileName, String data){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write(data);
            System.out.println("Data written to file " + fileName);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
