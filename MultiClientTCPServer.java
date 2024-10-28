import java.io.*;
import java.net.*;
public class MultiClientTCPServer{
 public static void main(String[] args){
  try(ServerSocket serverSocket=new ServerSocket(6789)){
   System.out.println("Server is listening on port 6789");
   while(true){
    Socket clientSocket=serverSocket.accept();
    System.out.println("New client connected");
    new ClientHandler(clientSocket).start();
  }
 }catch(IOException ex){
   ex.printStackTrace();
  }
 }
}
class ClientHandler extends Thread{
 private Socket clientSocket;
 public ClientHandler(Socket socket){
  this.clientSocket=socket;
 }
  public void run(){
   try(InputStream input=clientSocket.getInputStream();
     BufferedReader reader=new BufferedReader(new InputStreamReader(input));
     OutputStream output=clientSocket.getOutputStream();
     PrintWriter writer=new PrintWriter(output,true)){
     String clientMessage;
  while((clientMessage=reader.readLine())!=null){
  System.out.println("Received from client:"+clientMessage);
  writer.println("Server received:"+clientMessage);
}
}catch(IOException ex){
ex.printStackTrace();
}finally{
try{
clientSocket.close();
}catch(IOException e)
{
e.printStackTrace();
}
System.out.println("Client disconnected");
}
}
}