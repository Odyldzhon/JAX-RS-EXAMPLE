package ua.lviv.odyldzhon.contacts.endpoint;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ua.lviv.odyldzhon.contacts.domain.InMemoryDB;
import ua.lviv.odyldzhon.contacts.domain.entity.Person;
import ua.lviv.odyldzhon.contacts.endpoint.exception.PersistentException;

@Singleton
@Path("contacts")
public class Contacts {

    @EJB
    private InMemoryDB persistentService;

    @GET
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person get(@PathParam("uuid") String name) {
        return persistentService.getUser(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Person[] getUsers() {
        return persistentService.getUsers();
    }

    @PUT
    @Path("{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("uuid") String uuid, Person user) {
        try {
            persistentService.updateUser(uuid, user);
            return Response.status(201).entity(user).build();
        } catch (PersistentException e) {
            return Response.status(404).type("text/plain")
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Person user) {
        try {
            persistentService.addUser(user);
            return Response.status(200).entity(user).build();
        } catch (PersistentException e) {
            return Response.status(409).type("text/plain")
                    .entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{uuid}")
    public Response delete(@PathParam("uuid") String uuid) {
        persistentService.deleteUser(uuid);
        return Response.status(200).build();
    }
}
