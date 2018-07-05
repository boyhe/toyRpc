package rpc.impl;

import rpc.Exception.SimpleException;
import rpc.annotation.RpcService;
import rpc.model.Person;
import rpc.service.PersonService;

@RpcService(PersonService.class)
public class PersonServiceImp implements PersonService {
    @Override
    public Person getInfo(int id) throws SimpleException {
        Person person = Person.info.get(id);
        if(person != null) return person;
        else throw new SimpleException(id + " is not exists");
    }
}
