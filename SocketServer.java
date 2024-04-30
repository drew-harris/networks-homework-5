import java.io.*;
import java.net.*;
import java.util.Base64;


public class SocketServer {
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  public void start(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    clientSocket = serverSocket.accept();
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    String message = in.readLine();
    Base64.Decoder decoder = Base64.getDecoder();
    byte[] decoded = decoder.decode(message.getBytes());
    message = new String(decoded);
    System.out.println(message);

    String responseMessage = "this is a secret message from the server";
    Base64.Encoder encoder = Base64.getEncoder();
    String encodedMessage = encoder.encodeToString(responseMessage.getBytes());
    out.println(encodedMessage);
  }

  public void stop() throws IOException {
    in.close();
    out.close();
    clientSocket.close();
    serverSocket.close();
  }
  public static void main(String[] args) throws IOException {
    SocketServer server=new SocketServer();
    server.start(6666);
  }
}
