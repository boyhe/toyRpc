package rpc.service;

import rpc.Exception.SimpleException;
import rpc.model.Person;

public interface PersonService {
    Person getInfo(int id) throws SimpleException;
}
