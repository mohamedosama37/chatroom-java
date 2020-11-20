/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {
    

    ServerSocket  serversocket;
    public ChatServer()
    {
        try {
            serversocket    = new ServerSocket(5005);
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    while(true)
    {
        Socket s;
            try {
                s = serversocket.accept();
            } catch (IOException ex) {
                Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                new ChatHandler(s);
            } catch (IOException ex) {
                Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    }
public static void main (String[] args)
{
new ChatServer();
}

class ChatHandler extends Thread
{
DataInputStream dis;
PrintStream ps;
static Vector<ChatHandler> clientsVector=new Vector<ChatHandler>();
public ChatHandler(Socket cs)throws IOException
{
dis = new DataInputStream(cs.getInputStream());
ps= new PrintStream(cs.getOutputStream());
clientsVector.add(this);
start();
}
@Override
     public void run()
    {
    while(true)
    {
        try{
    String str= dis.readLine();
    sendMessageToAll(str);}
        catch(Exception e){
            clientsVector.remove(this);
        }
    }
    }
    void sendMessageToAll(String msg)
    {
    for(ChatHandler ch: clientsVector)
    {
    ch.ps.println(msg);
    }
    }
}
}
