package com.example.demo.client;

import com.example.demo.controllers.*;

import com.example.demo.database.ContDAO;
import com.example.demo.database.MessageFriendDAO;
import com.example.demo.database.PrietenDAO;
import com.google.gson.Gson;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.DemoApplication.initDatabaseConnectionPool;


public class RestClient {

    private static final String INIT ="http://localhost:8088/";
    private static final String Create_PERSONS = "http://localhost:8088/persons/add";
    private static final String GET_PERSON_BY_ID = "http://localhost:8088/persons/{id}";
    private static final String GET_PERSON_BY_NAME = "http://localhost:8088/persons/name/{id}";
    private static final String CREATE_FRIENDSHIP = "http://localhost:8088//friendship/add";
    private static final String SEE_ALL_FRIENDSHIP ="http://localhost:8088/friendship";
    private static final String CHANGE_PASSWORD ="http://localhost:8088/persons/changepassword/{id}";
    private static final String DELETE_FRIENDSHIP = "http://localhost:8088/friendship/{id1}/{id2}";
    private static final String CREATE_MESSAGE=INIT+"messages";
    static RestTemplate restTemplate = new RestTemplate();

    private static Message getMessageByStats(List<Message> messageList, int id1, int id2, String text){
        for(Message mesaj : messageList)
            if(mesaj.getIdSender() == id1 && mesaj.getIdReciever() == id2 && mesaj.getMessage().equals(text.toUpperCase())){
//                    System.out.println(mesaj);
                // get the new id message that i added
                return mesaj;
            }
        return null;
    }
    public static Message callCreateMessage(int id1, int id2, String message){
        try{
             int id = new MessageController().createProduct(id1, id2,message);
             if(id!=-1)
                 return getMessageByStats(new MessageFriendDAO().findAllMessages(),id1,id2, message);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static String callGetAllMessages()
    {
        ResponseEntity<String> messages = restTemplate.exchange(CREATE_MESSAGE,HttpMethod.GET, null, String.class);
        return messages.getBody();
    }
    public static void callDeleteFriendship(int id1, int id2)
    {
        Map<String, Integer> param = new HashMap<>();
        param.put("id1", id1);
        param.put("id2",id2);
         restTemplate.delete(DELETE_FRIENDSHIP, param);
    }

    public static String callGetAllFriendship(){
        ResponseEntity<String> friendships = restTemplate.exchange(SEE_ALL_FRIENDSHIP, HttpMethod.GET,null, String.class);

        return friendships.getBody();

    }
    //    public static void main(String[] args) {
//        callCreatePersonUSerAPi( "name", "password");
//    }

    public static Person getPersonByJson(String json)
    {
        return new Gson().fromJson(json,Person.class);
    }
    public static Person changePassword(int id, String password)
    {
        try {
            new ContDAO().updatePassUsingId(id,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Person response = getPersonByJson(callGeTPersonByIdAPI(id));
        return response;
    }

    public static String callDeletepersonByIdAPi(int idThatIWant)
    {
        Map<String,Integer> param = new HashMap<>();
        param.put("id",idThatIWant);

        restTemplate.delete(GET_PERSON_BY_ID,param);
        try {
            return new PrietenDAO().deleteAllFriendshipWithId(idThatIWant);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static String callGeTPersonByIdAPI(int idThatIWant) {
        Map<String, Integer> param = new HashMap<>();
        param.put("id", idThatIWant);

        String person = restTemplate.getForObject(GET_PERSON_BY_ID, String.class, param);
        return person;

    }

    public static String callGetPersonByName(String name) {
        Map<String, String> param = new HashMap<>();
        param.put("id", name);
        String person =new String();
        try {
             person= restTemplate.getForObject(GET_PERSON_BY_NAME, String.class, param);
            System.out.println(person);
             return person;


        }
        catch (Exception e)
        {
            return  null;
        }




    }

    public static Person callCreatePersonUSerAPi(String name, String password) {
        Map<String, Person> param = new HashMap<>();
        Person person = new Person(name, password);
        param.put("person", person);
        System.out.println(person);

//        ResponseEntity<Person> person2 = restTemplate.postForEntity(Create_PERSONS,);
//        System.out.println(person2.getBody());
        initDatabaseConnectionPool();
        if (new PersonController().createProduct(person).getBody().equals("Product created successfully")) {
            return person;
        } else return null;

    }

    public static Friendship callCreateFriendshipUSerAPi(Friendship f) {
        Map<String, Friendship> param = new HashMap<>();
        param.put("friendship", f);
        System.out.println(f);

        initDatabaseConnectionPool();
        if (new FriendshipController().createProduct(f).getBody().equals("Product created successfully")) {

            return f;
        }
        return null;

    }
}