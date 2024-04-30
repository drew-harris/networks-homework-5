import java.io.*;
import java.net.*;
import java.util.Base64;

public class SocketClient {
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  public void startConnection(String ip, int port) throws UnknownHostException, IOException {
    clientSocket = new Socket(ip, port);
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  public String sendMessage(String msg) throws IOException {
    out.println(msg);
    String resp = in.readLine();
    return resp;
  }

  public void stopConnection() throws IOException {
    in.close();
    out.close();
    clientSocket.close();
  }

  public static void main(String[] args) throws UnknownHostException, IOException {
    SocketClient client = new SocketClient();
    client.startConnection("127.0.0.1", 6666);


    String message = "this is a secret message from the client";
    Base64.Encoder encoder = Base64.getEncoder();
    String encodedMessage = encoder.encodeToString(message.getBytes());

    String response = client.sendMessage(encodedMessage);
    Base64.Decoder decoder = Base64.getDecoder();
    byte[] decoded = decoder.decode(response.getBytes());
    message = new String(decoded);
    System.out.println(message);

  }
}
