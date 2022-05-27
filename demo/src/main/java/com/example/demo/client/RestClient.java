package com.example.demo.client;

import com.example.demo.controllers.Friendship;
import com.example.demo.controllers.FriendshipController;
import com.example.demo.controllers.Person;
import com.example.demo.controllers.PersonController;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.example.demo.DemoApplication.initDatabaseConnectionPool;


public class RestClient {
    private static final String Create_PERSONS = "http://localhost:8088/persons/add";
    private static final String GET_PERSON_BY_ID = "http://localhost:8088/persons/{id}";
    private static final String GET_PERSON_BY_NAME = "http://localhost:8088/persons/name/{id}";
    private static final String CREATE_FRIENDSHIP = "http://localhost:8088//friendship/add";
    private static final String SEE_ALL_FRIENDSHIP ="http://localhost:8088/friendship";
    static RestTemplate restTemplate = new RestTemplate();

    public static String callGetAllFriendship(){
        ResponseEntity<String> friendships = restTemplate.exchange(SEE_ALL_FRIENDSHIP, HttpMethod.GET,null, String.class);

        return friendships.getBody();

    }
    //    public static void main(String[] args) {
//        callCreatePersonUSerAPi( "name", "password");
//    }

    public static String callDeletepersonByIdAPi(int idThatIWant)
    {
        Map<String,Integer> param = new HashMap<>();
        param.put("id",idThatIWant);

        restTemplate.delete(GET_PERSON_BY_ID,param);
        return "DONE";

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
        String person = restTemplate.getForObject(GET_PERSON_BY_NAME, String.class, param);
        System.out.println(person);

        return person;


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