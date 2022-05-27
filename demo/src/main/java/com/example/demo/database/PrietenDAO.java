package com.example.demo.database;


import com.example.demo.controllers.Friendship;
import com.example.demo.controllers.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.DemoApplication.dataSource;

public class PrietenDAO {

    public List<Integer> seAllFriendsWithId(int id) {
        List<Integer> integerList = new ArrayList<>();
        try {

            Connection con = dataSource.getConnection();
            Statement stmt = con.createStatement();
            String sql = "SELECT DISTINCT ID_FIRST, ID_SECOND FROM PRIETEN where id_First =" + id + " OR ID_SECOND ="+id+ " ORDER BY ID_FIrST";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int ids = rs.getInt("id_second");
                integerList.add(ids);
                System.out.println(ids);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return integerList;


    }
    public int numberOfIds() {
        int idMax1 = 0;
        try {
            Connection con = dataSource.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select count(*) as count from(select count(*) from prieten group by ID_FirST)";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            idMax1 = rs.getInt("count");
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return Math.max(idMax1, 0);

    }

    public String deleteAllFriendshipWithId(int id) throws SQLException
    {
        Connection con = dataSource.getConnection();
        int idMax1 = 0;
        try (PreparedStatement pstm = con.prepareStatement("delete from prieten where ID_FIRST = (?) or ID_SECOND=(?)")) {
            Statement stmt = con.createStatement();

            pstm.setInt(1, id);
            pstm.setInt(2, id);
            pstm.executeUpdate();
            con.commit();


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            con.close();
        }
        return "Done";
    }


    public int maxID() {
        int idMax1 = 0;
        try {
            Connection con = dataSource.getConnection();
            Statement stmt = con.createStatement();
            String sql = "SELECT MAX(ID_First) FROM PRIETEN ";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            idMax1 = rs.getInt("MAX(ID_First)");
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return Math.max(idMax1, 0);

    }

    public int muchii(){
        int idMax1 = 0;
        try {
            Connection con = dataSource.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select count(*) from prieten where ID_SECOND!=ID_FIRST ";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            idMax1 = rs.getInt("count(*)");
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return Math.max(idMax1, 0);

    }
    public void create(int idFirst, int idSecond) throws SQLException {

        Connection con = dataSource.getConnection();

        try (PreparedStatement pstm = con.prepareStatement("insert into Prieten (id_first, ID_SECOND,CREATED_AT) values ((?), (?), SYSDATE)")) {
            Statement stmt = con.createStatement();

            pstm.setInt(1, idFirst);
            pstm.setInt(2, idSecond);
            pstm.executeUpdate();
            con.commit();


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            con.close();
        }
    }

    public Person findPersonUsingUsernameAndPass(String name, String password) throws SQLException
    {
        Person person= new Person(0,null,null,null);
        Connection con = dataSource.getConnection();
        List<Person> personList= new ContDAO().findAll();

        for(Person pers : personList)
        {
            if(pers.getName().equals(name) && pers.getPassword().equals(password)) person=pers;

        }
        return person;

    }



    public List<Friendship> findAll() {
//         initializare

        int idFirst, idSecond;
        String firstName = new String();
        String secondName = new String();
        List<Friendship> friendshipList = new ArrayList<>();
        try {
            Connection con = dataSource.getConnection();
            Statement stmt = con.createStatement();
            /// NU STIU SIGUR DACA TREBUIE DISTINCT AICI...
            String sql = "Select DISTINCT * from prieten order by id_first";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // adding the elements from the db to the class Person
                idFirst = rs.getInt("ID_first");
                idSecond = rs.getInt("ID_SECOND");

//                listaNume.add(new Person(currentId,currentName));
                friendshipList.add(new Friendship(idFirst, idSecond));

            }

            //closing everything i created
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendshipList;
    }
}
