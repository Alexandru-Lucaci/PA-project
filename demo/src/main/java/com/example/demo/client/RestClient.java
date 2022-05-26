package com.example.demo.client;

import com.example.demo.controllers.Person;
import com.example.demo.controllers.PersonController;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.example.demo.DemoApplication.initDatabaseConnectionPool;


public class RestClient {
    private static final String Create_PERSONS ="http://localhost:8088/persons/add";
    private static final String GET_PERSON_BY_ID="http://localhost:8088/persons/{id}";
    private static final String GET_PERSON_BY_NAME="http://localhost:8088/persons/name/{id}";


    static RestTemplate restTemplate=new RestTemplate();



    //    public static void main(String[] args) {
//        callCreatePersonUSerAPi( "name", "password");
//    }
    public static void callGeTPersonByIdAPI(int idThatIWant){
        Map<String, Integer> param =new HashMap<>();
        param.put("id",idThatIWant);

        String person= restTemplate.getForObject(GET_PERSON_BY_ID,String.class, param);
        System.out.println(person);

    }
    public static String  callGetPersonByName(String name){
        int idThatIGot=-1;

        Map<String, String> param =new HashMap<>();
        param.put("id",name);
        String person = restTemplate.getForObject(GET_PERSON_BY_NAME,String.class, param);
        System.out.println(person);

        return person;


    }
    public static Person callCreatePersonUSerAPi(String name, String password){
        Map<String, Person> param =new HashMap<>();
        Person person = new Person(name,password);
        param.put("person", person) ;
        System.out.println(person);

//        ResponseEntity<Person> person2 = restTemplate.postForEntity(Create_PERSONS,);
//        System.out.println(person2.getBody());
        initDatabaseConnectionPool();
       if(new PersonController().createProduct(person).getBody().equals("Product created successfully")){
            return person;
       }
       else return null;


    }
}
