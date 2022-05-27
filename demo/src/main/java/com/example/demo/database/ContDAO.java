package com.example.demo.database;

import com.example.demo.controllers.Person;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.DemoApplication.dataSource;

public class ContDAO {

    public void updateUsingId(int id, String changeWith) throws SQLException{
        Connection con = dataSource.getConnection();
        try{
            // sql statemant
            String sql = "update cont set name = ? where id=?";
            PreparedStatement statement= con.prepareStatement(sql);
            statement.setString(1,changeWith);
            statement.setInt(2, id);
            statement.executeUpdate();
            // commit to work properly
            con.commit();
            con.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void updatePassUsingId(int id, String changeWith) throws SQLException{
        Connection con = dataSource.getConnection();
        try{
            // sql statemant
            String sql = "update cont set password = ? where id=?";
            PreparedStatement statement= con.prepareStatement(sql);
            statement.setString(1,changeWith);
            statement.setInt(2, id);
            statement.executeUpdate();
            // commit to work properly
            con.commit();
            con.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public Integer findByName(String name) throws SQLException {
        Connection con = dataSource.getConnection();
        name=name.toUpperCase();
        int ids=-1;
        try {
            Statement stmt = con.createStatement();
            String sql = "select id from cont where name =\'" + name + "\' order by id";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            ids = rs.getInt("ID");
            con.close();
        }catch (Exception e)
        {
            System.out.println(e);
        }
        finally {
            con.close();

        }
        return ids;
    }
    public String findPasswordById(int id)
    {

        try {
            List<Person> personList = findAll();

            for(Person p:personList)
            {
                if(p.getId()==id)
                   return p.getPassword();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Person getFullPersonById(int id)
    {
        try {
            List<Person> personList = findAll();
            for(Person p:personList)
            {
                if(p.getId()==id)
                    return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


    public int getCount() throws SQLException{

        Connection con = dataSource.getConnection();
        int count=0;
        try{

            Statement statement = con.createStatement();
            String sql = "SELECT COUNT(*) FROM cont ";
            ResultSet resultSet= statement.executeQuery(sql);
            resultSet.next();
            count= resultSet.getInt("COUNT(*)");

            resultSet.close();
            statement.close();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally {
            con.close();


        }
        return count;
    }
    public String findById(int id) throws SQLException {
        Connection con = dataSource.getConnection();
        String ids=null;
        try {
            Statement stmt = con.createStatement();
            String sql = "select name from cont where id =" + id;
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            ids = rs.getString("NAME");
            con.close();
        }catch (Exception e)
        {
            System.out.println(e);
        }
        finally {
            con.close();

        }
        return ids;

    }
    public void delete(int id) throws  SQLException{
        Connection con = dataSource.getConnection();
        try{
            // sql statemant
            String sql = "DELETE FROM cont where id=?";
            PreparedStatement statement= con.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
            // commit to work properly
            con.commit();
            con.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        con.close();
    }
    public void create(String name, String password) throws SQLException {
        Connection con = dataSource.getConnection();
        name=name.toUpperCase();

        try(PreparedStatement pstm = con.prepareStatement("insert into cont (ID, NAMe,PASSWORD, CREATED_AT ) values ((?), (?), (?), sysdate)")){
            Statement stmt = con.createStatement();
            String sql="select max(id) from CONT";
            ResultSet rs= stmt.executeQuery(sql);
            rs.next();
            int ids=rs.getInt("MAX(ID)");
            pstm.setInt(1,(ids+1));
            pstm.setString(2, name);
            pstm.setString(3,password);

            LocalDateTime now = LocalDateTime.now();
            pstm.executeUpdate();

            con.commit();


        }catch (Exception e){
            System.out.println(e);
        }
        finally {
            con.close();
        }
    }
    public List<Person> findAll() throws SQLException {

        //initialization
        int currentId;
        String currentName=new String();
        String currentPassword= new String();
        // the returned list
        List<Person> listaNume=new ArrayList<>();
        //establising connection
        Connection con = dataSource.getConnection();

        try{

            //statement creation
            Statement stmt = con.createStatement();
            String sql= "Select * from cont order by name";

            ResultSet rs = stmt.executeQuery(sql);
            //parsing all the resulted lines
            while(rs.next()){
                // adding the elements from the db to the class Person
                currentId = rs.getInt("ID");
                currentName=rs.getString("NAME");
                currentPassword = rs.getString("PASSWORD");
                Date currentDate = rs.getDate("CREATED_AT");
                listaNume.add(new Person(currentId,currentName,currentPassword,currentDate));
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
        return listaNume;
    }

}
