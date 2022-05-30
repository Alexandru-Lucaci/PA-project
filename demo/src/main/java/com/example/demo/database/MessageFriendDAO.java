package com.example.demo.database;

import com.example.demo.controllers.Message;
import com.example.demo.controllers.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.DemoApplication.dataSource;

public class MessageFriendDAO {



    public List<Message> findAllMessages() throws SQLException{
        int id;
        int currentId1;
        int currentId2;
        String curentMesaj= new String();
        List<Message> messageList =new ArrayList<>();
        Connection con = dataSource.getConnection();

        try{

            //statement creation
            Statement stmt = con.createStatement();
            String sql= "Select * from messageFriend order by CREATED_AT";

            ResultSet rs = stmt.executeQuery(sql);
            //parsing all the resulted lines
            while(rs.next()){
                // adding the elements from the db to the class Person
                currentId1 = rs.getInt("ID_Sender");
                currentId2 = rs.getInt("ID_reciever");
                id = rs.getInt("ID");
                curentMesaj=rs.getString("mesaj");
                Date currentDate = rs.getDate("CREATED_AT");
                messageList.add(new Message(id,currentId1, currentId2, curentMesaj,currentDate));
            }
            //closing everything i created
            rs.close();
            stmt.close();


        }catch (Exception e)// Exeption handleling
        {
            System.out.println(e);
        }
        finally {
//            closing the connection
            con.close();


        }
        return messageList;


    }

    public void create(String mesaj, int idCont, int idReciever) throws SQLException {


        Connection con = dataSource.getConnection();
        mesaj=mesaj.toUpperCase();
        try(PreparedStatement pstm = con.prepareStatement("insert into messagefriend (ID,ID_SENDER,ID_RECIEVER, mesaj, created_at) values ((?), (?),(?),(?) ,sysdate)")){
            Statement stmt = con.createStatement();
            String sql="select max(id) from messageFriend";
            ResultSet rs= stmt.executeQuery(sql);
            rs.next();
            int ids=rs.getInt("MAX(ID)");
            pstm.setInt(1,(ids+1));
            pstm.setString(4, mesaj);
            pstm.setInt(2,idCont);
            pstm.setInt(3, idReciever);
            pstm.executeUpdate();

            con.commit();


        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            con.close();
        }
    }
}
