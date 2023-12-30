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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientHandler implements  Runnable{
    public  static Map<Integer,ClientHandler> clientHandlers=new HashMap<>();
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
            System.out.println("user id :"+idUser);
            this.user=iServiceUser.getUserbyId(idUser);
            clientHandlers.put(idUser,this);
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
                System.out.println(messageFromClient);

                //devide  between the two
                String []splits=messageFromClient.split(  "=>");

                //get received user
                int receiverUserId=Integer.parseInt(splits[0]);
                User receiverUser=iServiceUser.getUserbyId(receiverUserId);
                String messageFromSender=splits[1];
                if(messageFromSender.equals("$$loadMessages$$")){
                        loadOldMessages(receiverUserId);
                }else{
                    //add message

                    Message msg=new Message(messageFromSender, user,receiverUser);
                    iServiceMessage.addMessage(msg);

                    sendMessage(this.user,receiverUser,messageFromSender);
                }

            }catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;
            }
        }
    }
    // sendMessage need the  username Sender
    public  void sendMessage(User sender,User receiverUserId,String messageToSend){


        try {
            ClientHandler clientHandler=clientHandlers.get(receiverUserId.getIdUser());
            if(clientHandler!=null){
                clientHandler.bufferedWriter.write(sender.getUsername()+"=>"+messageToSend);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            }


            }catch (IOException e){

                closeEverything(socket,bufferedReader,bufferedWriter);
            }

    }
    //load allmessages
    public  void loadOldMessages(int receiverUserId)  {

        List<Message> msgs= iServiceMessage.getConversation(user.getIdUser(),receiverUserId);
        for(Message msg:msgs){
            System.out.println(receiverUserId+" "+msg.getMessage());

            sendMessage(msg.getSender(),user,msg.getMessage());
        }
    }
    public  void removeClientHandler(){
//        clientHandlers.remove(this);
//        broadcastMessage("SERVER: "+user.getUsername()+" has left the chat!" );
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
