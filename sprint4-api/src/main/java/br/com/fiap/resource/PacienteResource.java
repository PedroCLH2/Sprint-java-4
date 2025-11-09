package br.com.fiap.resource;

import br.com.fiap.dao.PacienteDAO;
import br.com.fiap.model.Paciente;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import br.com.fiap.dto.PacienteDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

    @Inject
    ModelMapper modelMapper;

    @Inject
    PacienteDAO dao;

    @GET
    public List<Paciente> listar() throws SQLException {
        return dao.listar();
    }

    @POST
    public Response cadastrar(@Valid PacienteDTO dto) throws SQLException {
        // Converte o DTO recebido para a Entidade Paciente
        Paciente paciente = modelMapper.map(dto, Paciente.class);

        // Chama o DAO para salvar a entidade no banco
        dao.cadastrar(paciente);

        // Retorna 201 Created com o paciente salvo
        return Response.status(Response.Status.CREATED).entity(paciente).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) throws SQLException {
        Paciente paciente = dao.pesquisarPorId(id);
        if (paciente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(paciente).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Paciente paciente) throws SQLException {
        paciente.setId(id);
        dao.atualizar(paciente);
        return Response.ok(paciente).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Long id) throws SQLException {
        dao.remover(id);
        return Response.noContent().build();
    }
}