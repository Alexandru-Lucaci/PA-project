package com.example.demo.controllers;


import com.example.demo.database.ContDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {


    @PostMapping(value = "/obj", consumes="application/json")
    public ResponseEntity<String>
    createProduct(@RequestBody Person person) {
        List<Person> persons = getPersons();
        persons.add(person);
        try {
            new ContDAO().create(person.getName(),person.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                "Product created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePerson(
            @PathVariable int id, @RequestParam String name) {
// ID il primesc din path
//        Numele este un parametru request, cu ce voi modifica numele.
        try {
            String stringNumeCautat = new ContDAO().findById(id);
            if(stringNumeCautat==null)
                return new ResponseEntity<>(
                        "Product not found", HttpStatus.NOT_FOUND); //or GONE
            new ContDAO().updateUsingId(id, name);
            return new ResponseEntity<>(
                    "Product updated successsfully", HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                "Something went wrong", HttpStatus.GONE); //or GONE
    }

    @PutMapping("/changepassword/{id}")
    public ResponseEntity<String> updatePassUsingId(
            @PathVariable int id, @RequestParam String password) {
// ID il primesc din path
//        Numele este un parametru request, cu ce voi modifica numele.
        try {
            String stringNumeCautat = new ContDAO().findById(id);
            if(stringNumeCautat==null)
                return new ResponseEntity<>(
                        "Product not found", HttpStatus.NOT_FOUND); //or GONE
            new ContDAO().updatePassUsingId(id, password);
            return new ResponseEntity<>(
                    "Product updated successsfully", HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                "Something went wrong", HttpStatus.GONE); //or GONE
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable int id){
        try {
            String person = new ContDAO().findById(id);
//          If i can't find the person with the id that i got
            if(person==null) return new ResponseEntity<>("Person not found",HttpStatus.GONE);
            List<Person> personList = getPersons();
//            deleting from the database
            new ContDAO().delete(id);
//            Update in the person list. I don't think that i need to do that but...
            personList.remove(getPerson(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Persone with id= "+id+" removed",HttpStatus.OK);
    }
    @PostMapping
    public String createProduct(@RequestParam String name, @RequestParam String password) {
        //int id=-1;
        String response="";
        try {
            response =new ContDAO().create(name,password);
            System.out.println(response);
            //id= new ContDAO().findByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;

    }
    @PostMapping("/add")
    public int create(@RequestParam Person person) {
        int id=-1;
        try {
            new ContDAO().create(person.getName(),person.getPassword());
            id= new ContDAO().findByName(person.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;

    }


    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id){
        List<Person> personList=getPersons();
        return personList.stream().filter( p-> p.getId()==id).findFirst().orElse(null);
    }

    @GetMapping("/name/{id}")
    public Person getPersonByName(@PathVariable("id") String name){
        List<Person> personList=getPersons();
        return personList.stream().filter(p->p.getName().equals(name)).findFirst().orElse(null);

    }

    @GetMapping
    public List<Person> getPersons(){
        List<Person> list= new ArrayList<>();
        try {
            list = new ContDAO().findAll();
        }catch (Exception e){
            System.out.println("Error -> "+e);

        }
        return list;
    }
}
