package Server;

import java.io.*;

public class ServerStrategyStringReverser implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter toClient = new PrintWriter(outputStream);

        String phrase;
        try {
            String reversedPhrase;
            while (!(phrase = fromClient.readLine()).equals("Thanks!")) {
                reversedPhrase = new StringBuilder(phrase).reverse().toString();
                toClient.write(reversedPhrase+"\r\n");
                toClient.flush();
            }
            toClient.write("you welcome, bye!\r\n");
            toClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
