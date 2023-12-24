package com.exemple.dialing.server;

import com.exemple.dialing.dao.MessageDaoImpl;
import com.exemple.dialing.dao.UserDaoImpl;
import com.exemple.dialing.dao.entities.Message;
import com.exemple.dialing.dao.entities.User;
import com.exemple.dialing.service.IServiceMessageImpl;
import com.exemple.dialing.service.IServiceUserImpl;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements  Runnable{
    public  static ArrayList<ClientHandler> clientHandlers=new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private IServiceMessageImpl iServiceMessage;
    private IServiceUserImpl iServiceUser;
    private  User user;
    public ClientHandler(Socket  socket){
        iServiceUser=new IServiceUserImpl(new UserDaoImpl());
        iServiceMessage=new IServiceMessageImpl(new MessageDaoImpl());
        try{
            this.socket=socket;
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            int idUser=bufferedReader.read();
            System.out.println("use id :"+idUser);
            this.user=iServiceUser.getUserbyId(idUser);
            clientHandlers.add(this);
            loadOldMessages();
            broadcastMessage("Server: "+user.getUsername()+" has entered the chat!");
        }catch (IOException e){
            e.printStackTrace();
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    @Override
    public void run() {
        while(socket.isConnected()){
            try{
                String messageFromClient=bufferedReader.readLine();
                Message msg=new Message(messageFromClient, user);
                iServiceMessage.addMessage(msg);
                broadcastMessage(messageFromClient);
            }catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;
            }
        }
    }

    public  void broadcastMessage(String messageToSend){
        for(ClientHandler  clientHandler:clientHandlers){

            try{
                if(!clientHandler.user.getUsername().equals(this.user.getUsername())){
                    System.out.println(messageToSend.toLowerCase().contains("server"));
                    if(messageToSend.toLowerCase().contains("server")){
                        clientHandler.bufferedWriter.write(messageToSend);

                    }else {
                        String sender=this.user.getUsername();
                        clientHandler.bufferedWriter.write(sender+":"+messageToSend);
                    }
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch (IOException e){

                closeEverything(socket,bufferedReader,bufferedWriter);
            }
        }
    }
    public  void loadOldMessages()  {
        List<Message> msgs= iServiceMessage.getAllMessages();
        for(Message msg:msgs){
            String sender="Me";
            if(msg.getSender().getIdUser()!=this.user.getIdUser()){
                sender=msg.getSender().getUsername();
            }
            String formattedMsg=sender+":"+msg.getMessage();
            try {
                this.bufferedWriter.write(formattedMsg);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            } catch (IOException e) {
                closeEverything(socket,bufferedReader,bufferedWriter);
            }
        }
    }
    public  void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: "+user.getUsername()+" has left the chat!" );
    }
    public  void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        removeClientHandler();
        try{
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
            if(socket !=null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
