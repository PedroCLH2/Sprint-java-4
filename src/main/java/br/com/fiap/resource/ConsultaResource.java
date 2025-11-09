package br.com.fiap.resource;

import br.com.fiap.dao.ConsultaDAO;
import br.com.fiap.model.Consulta;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import br.com.fiap.dto.ConsultaDTO;
import br.com.fiap.model.Medico;
import br.com.fiap.model.Paciente;
import jakarta.validation.Valid;

@Path("/consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaResource {

    @Inject
    ConsultaDAO dao;

    @GET
    public List<Consulta> listar() throws SQLException {
        return dao.listar();
    }

    @POST
    public Response agendar(@Valid ConsultaDTO dto) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setDataHora(dto.getDataHora());
        consulta.setMotivo(dto.getMotivo());

        Paciente p = new Paciente();
        p.setId(dto.getIdPaciente());
        consulta.setPaciente(p);

        Medico m = new Medico();
        m.setId(dto.getIdMedico());
        consulta.setMedico(m);

        dao.cadastrar(consulta);
        return Response.status(Response.Status.CREATED).entity(consulta).build();
    }
}