package com.exemple.dialing.presentation.controllers;

import com.exemple.dialing.dao.UserDaoImpl;
import com.exemple.dialing.dao.entities.User;
import com.exemple.dialing.service.IServiceUserImpl;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
public class ClientController {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private User user;
    public ClientController(Socket socket, User user){
        try{
            this.socket=socket;
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            this.user=user;

        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public  void sendMessage(){
        String username=user.getUsername();
        try{
            System.out.println("username : "+username);
            bufferedWriter.write(user.getIdUser());
            bufferedWriter.flush();

            Scanner scanner=new Scanner(System.in);
            while (socket.isConnected()){
                String messageToSend=scanner.nextLine();
                if (!messageToSend.trim().isEmpty()) {
                    bufferedWriter.write(messageToSend);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } else {
                    System.out.println("Please enter a non-empty message.");
                }
            }
        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public  void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while(socket.isConnected()){
                    try{
                        msgFromGroupChat=bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    }catch (IOException e){
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();
    }
    public  void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
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

    public static void main(String[] args) throws IOException {
        Scanner scanner=new Scanner(System.in);
        User user=null;
        do{
            System.out.println("Welcome to dialing app: \n 1-login ,2-register ");
            int choice=scanner.nextInt();
            scanner.nextLine();
            if(choice==1){
                System.out.println("give me ur username :");
                String usernameLogin=scanner.nextLine();
                System.out.println("give me ur password :");
                String passwordLogin=scanner.nextLine();
                IServiceUserImpl iServiceUser =new  IServiceUserImpl(new UserDaoImpl());
                user=iServiceUser.findUserbyNameAndPassword(usernameLogin,passwordLogin);
                if(user==null){
                    System.out.println("wrong password or username");
                }else{
                    System.out.println("Login success");
                }
            }else{
                System.out.println("give me ur username :");
                String usernameLogin=scanner.nextLine();
                System.out.println("give me ur password");
                String passwordLogin=scanner.nextLine();
                IServiceUserImpl iServiceUser =new  IServiceUserImpl(new UserDaoImpl());
                if(iServiceUser.findUserbyNameAndPassword(usernameLogin,passwordLogin)==null){
                    user=new User(usernameLogin,passwordLogin);
                    iServiceUser.addUser(user);
                    user=iServiceUser.findUserbyNameAndPassword(usernameLogin,passwordLogin);
                    System.out.println("Registration sucess");
                }else{
                    System.out.println("user already exists with that username");
                }
            }
        }while (user==null);

        Socket socket=new Socket("localhost",9090);
        ClientController client=new ClientController(socket,user);
        client.listenForMessage();
        client.sendMessage();
    }
}
