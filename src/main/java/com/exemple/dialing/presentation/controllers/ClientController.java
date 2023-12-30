package com.exemple.dialing.presentation.controllers;

import com.exemple.dialing.dao.UserDaoImpl;
import com.exemple.dialing.dao.entities.User;
import com.exemple.dialing.service.IServiceUserImpl;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;
public class ClientController {
    private static Socket socket;
    private static BufferedWriter bufferedWriter;
    private static BufferedReader bufferedReader;
    private static User user;
    public ClientController(Socket socket, User user){
        try{
            ClientController.socket =socket;
            bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            ClientController.user =user;

        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public static void sendMessage(String message){
        try{
            while (socket.isConnected()){
                if (!message.trim().isEmpty()) {
                    bufferedWriter.write(message);
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
    public  static  void listenForMessage(Consumer<String> messageConsumer){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgReceived;
                while(socket.isConnected()){
                    try{
                        msgReceived=bufferedReader.readLine();
                        System.out.println(msgReceived);
                        messageConsumer.accept(msgReceived);
                    }catch (IOException e){
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();
    }
    public static void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
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

//    public static void main(String[] args) throws IOException {
//        Scanner scanner=new Scanner(System.in);
//        User user=null;
//        do{
//            System.out.println("Welcome to dialing app: \n 1-login ,2-register ");
//            int choice=scanner.nextInt();
//            scanner.nextLine();
//            if(choice==1){
//                System.out.println("give me ur username :");
//                String usernameLogin=scanner.nextLine();
//                System.out.println("give me ur password :");
//                String passwordLogin=scanner.nextLine();
//                IServiceUserImpl iServiceUser =new  IServiceUserImpl(new UserDaoImpl());
//                user=iServiceUser.findUserbyNameAndPassword(usernameLogin,passwordLogin);
//                if(user==null){
//                    System.out.println("wrong password or username");
//                }else{
//                    System.out.println("Login success");
//                }
//            }else{
//                System.out.println("give me ur username :");
//                String usernameLogin=scanner.nextLine();
//                System.out.println("give me ur password");
//                String passwordLogin=scanner.nextLine();
//                IServiceUserImpl iServiceUser =new  IServiceUserImpl(new UserDaoImpl());
//                if(iServiceUser.findUserbyNameAndPassword(usernameLogin,passwordLogin)==null){
//                    user=new User(usernameLogin,passwordLogin);
//                    iServiceUser.addUser(user);
//                    user=iServiceUser.findUserbyNameAndPassword(usernameLogin,passwordLogin);
//                    System.out.println("Registration sucess");
//                }else{
//                    System.out.println("user already exists with that username");
//                }
//            }
//        }while (user==null);
//
//        Socket socket=new Socket("localhost",9090);
//        ClientController client=new ClientController(socket,user);
//        client.listenForMessage();
//        client.sendMessage();
//    }
}
