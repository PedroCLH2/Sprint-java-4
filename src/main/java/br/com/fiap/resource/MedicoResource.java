package br.com.fiap.resource;

import br.com.fiap.dao.MedicoDAO;
import br.com.fiap.model.Medico;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import br.com.fiap.dto.MedicoDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;

@Path("/medicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicoResource {

    @Inject
    ModelMapper modelMapper;

    @Inject
    MedicoDAO dao;

    @GET
    public List<Medico> listar() throws SQLException {
        return dao.listar();
    }

    @POST
    public Response cadastrar(@Valid MedicoDTO dto) throws SQLException {
        Medico medico = modelMapper.map(dto, Medico.class);
        dao.cadastrar(medico);
        return Response.status(Response.Status.CREATED).entity(medico).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) throws SQLException {
        Medico medico = dao.pesquisarPorId(id);
        if (medico == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(medico).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Medico medico) throws SQLException {
        medico.setId(id);
        dao.atualizar(medico);
        return Response.ok(medico).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Long id) throws SQLException {
        dao.remover(id);
        return Response.noContent().build();
    }
}