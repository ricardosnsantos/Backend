/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetas;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ricardo
 */

@Stateless
@Path("/planetas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlanetaService {
    
    @PersistenceContext(unitName = "PlanetasPU")
    private EntityManager entityManager;

    public PlanetaService() {

    }
    
    @GET
    public List<Planeta> listar(){
        Query query = entityManager.createQuery("SELECT p FROM Planeta p");
        return query.getResultList();
    }   
    
    @POST
    public Planeta adicionar(Planeta planeta) {
        entityManager.persist(planeta);
        return planeta;
    }
    
    @PUT
    @Path("{id}")
    public Planeta atualizar(@PathParam("id") Integer id, Planeta planetaAtualizado) {
        Planeta planetaEncontrado = entityManager.find(Planeta.class, id);
        planetaEncontrado.setNome(planetaAtualizado.getNome());
        planetaEncontrado.setClima(planetaAtualizado.getClima());
        planetaEncontrado.setTerreno(planetaAtualizado.getTerreno());
        entityManager.merge(planetaEncontrado);
        return planetaEncontrado;
    }
    
    @DELETE
    @Path("{id}")
    public Planeta excluir(@PathParam("id") Integer id) {
        Planeta planeta = entityManager.find(Planeta.class, id);
        entityManager.remove(planeta);
        return planeta;
    }
    
    
    @GET
    @Path("{id}")
    public List<Planeta> buscar(@PathParam("id") Integer id) {
         Query query = entityManager.createQuery("SELECT p FROM Planeta p WHERE p.id = " + id);
        return query.getResultList();
    }   
    
    @GET
    @Path("{nome}")
    public List<Planeta> consultar(@PathParam("nome") String nome) {
        Query query = entityManager.createQuery("SELECT p FROM Planeta p WHERE p.nome LIKE '%" + nome + "%'");
        return query.getResultList();
    }
    
}
