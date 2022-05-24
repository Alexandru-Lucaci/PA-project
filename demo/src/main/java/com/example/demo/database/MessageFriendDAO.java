package com.example.demo.database;

import java.sql.*;

import static com.example.demo.DemoApplication.dataSource;

public class MessageFriendDAO {



    public void create(String mesaj, int idCont, int idReciever) throws SQLException {


        Connection con = dataSource.getConnection();
        mesaj=mesaj.toUpperCase();
        try(PreparedStatement pstm = con.prepareStatement("insert into mesaj (ID,ID_SENDER,ID_RECIEVER, mesaj, created_at) values ((?), (?),(?), sysdate)")){
            Statement stmt = con.createStatement();
            String sql="select max(id) from mesaj";
            ResultSet rs= stmt.executeQuery(sql);
            rs.next();
            int ids=rs.getInt("MAX(ID)");
            pstm.setInt(1,(ids+1));
            pstm.setString(3, mesaj);
            pstm.setInt(2,idCont);
            pstm.setInt(4, idReciever);
            pstm.executeUpdate();

            con.commit();


        }catch (Exception e){
            System.out.println(e);
        }
        finally {
            con.close();
        }
    }
}
