package ua.lviv.odyldzhon.contacts.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ejb.Singleton;

import ua.lviv.odyldzhon.contacts.domain.entity.Person;
import ua.lviv.odyldzhon.contacts.endpoint.exception.PersistentException;

@Singleton
public class InMemoryDB {

    private Map<UUID, Person> contacts;

    public InMemoryDB() {
        contacts = new HashMap<UUID, Person>();
    }

    public void addUser(Person user) throws PersistentException {
        if (contacts.containsValue(user)) {
            throw new PersistentException("Person already exists");
        }
        UUID uuid = UUID.randomUUID();
        contacts.put(uuid, user);
        user.setUuid(uuid.toString());
    }

    public Person getUser(String uuid) {
        return contacts.get(UUID.fromString(uuid));
    }

    public Person[] getUsers() {
        return contacts.values().toArray(new Person[contacts.size()]);
    }

    public void updateUser(String uuid, Person user) throws PersistentException {
        if (!contacts.containsKey(UUID.fromString(uuid))) {
            throw new PersistentException("Person doesn't exists with uuid : "
                    + uuid);
        } else if (user.getUuid() != null && !uuid.equals(user.getUuid())) {
            throw new PersistentException("You cannot override uuid");
        } else {
            user.setUuid(uuid);
            contacts.put(UUID.fromString(uuid), user);
        }
    }

    public void deleteUser(String uuid) {
        contacts.remove(UUID.fromString(uuid));
    }
}
