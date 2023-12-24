package com.exemple.dialing.dao;

import com.exemple.dialing.dao.entities.Message;
import com.exemple.dialing.dao.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {


    @Override
    public void save(Message o) {
        Connection connection=DBSingleton.getConnection();
        try {
            PreparedStatement pstm= connection.prepareStatement("INSERT INTO message(message,ID_USER)"+"VALUES (?,?)");
            pstm.setString(1,o.getMessage());
            pstm.setInt(2,o.getSender().getIdUser());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteById(Integer id) {
        Connection connection=DBSingleton.getConnection();
        try {
            PreparedStatement pstm= connection.prepareStatement("DELETE FROM message WHERE"+" ID_MESSAGE=?");
            pstm.setInt(1,id);
            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message getById(Integer id) {
        Connection connection=DBSingleton.getConnection();
        User user =null;
        Message  message =null;
        try {
            PreparedStatement pstm= connection.prepareStatement("SELECT * FROM message WHERE "+"ID_MESSAGE=?");
            pstm.setInt(1,id);
            ResultSet rs =pstm.executeQuery();
            if(rs.next()) {
                message  =new Message();
                message.setID_MESSAGE(rs.getInt("ID_MESSAGE"));
                message.setMessage(rs.getString("message"));
            }

            PreparedStatement pstm1= connection.prepareStatement("SELECT * FROM user WHERE "+"ID_USER=?");
            pstm1.setInt(1,rs.getInt("ID_USER"));
            ResultSet rs1 =pstm1.executeQuery();
            if(rs1.next()) {
                user =new User();
                user.setIdUser(rs1.getInt("ID_USER"));
                user.setPassword(rs1.getString("password"));
                user.setUsername(rs1.getString("username"));
                message.setSender(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        Connection connection=DBSingleton.getConnection();
        List<Message> messages =new ArrayList<>();
        try {
            PreparedStatement pstm= connection.prepareStatement("SELECT * FROM message ");
            ResultSet rs =pstm.executeQuery();
            while(rs.next()) {
               Message message  =new Message();
                message.setID_MESSAGE(rs.getInt("ID_MESSAGE"));
                message.setMessage(rs.getString("message"));



                PreparedStatement pstm1= connection.prepareStatement("SELECT * FROM user WHERE "+"ID_USER=?");
                pstm1.setInt(1,rs.getInt("ID_USER"));
                ResultSet rs1 =pstm1.executeQuery();
                if(rs1.next()) {
                    User user =new User();
                    user.setIdUser(rs1.getInt("ID_USER"));
                    user.setPassword(rs1.getString("password"));
                    user.setUsername(rs1.getString("username"));
                    message.setSender(user);
                }
                messages.add(message);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return messages;
    }

    @Override
    public void update(Message o) {
        System.out.println("updating the messages");
    }


}
