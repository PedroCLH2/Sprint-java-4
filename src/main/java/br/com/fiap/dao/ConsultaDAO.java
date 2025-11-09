package br.com.fiap.dao;

import br.com.fiap.model.Consulta;
import br.com.fiap.model.Medico;
import br.com.fiap.model.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsultaDAO {

    @Inject
    DataSource dataSource;

    public void cadastrar(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO TB_CONSULTA (id_consulta, id_paciente, id_medico, dt_hora, ds_motivo) " +
                "VALUES (SQ_CONSULTA.NEXTVAL, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_consulta"})) {

            stmt.setLong(1, consulta.getPaciente().getId());
            stmt.setLong(2, consulta.getMedico().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setString(4, consulta.getMotivo());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    consulta.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    public List<Consulta> listar() throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT C.id_consulta, C.dt_hora, C.ds_motivo, " +
                "P.id_paciente, P.nm_paciente, " +
                "M.id_medico, M.nm_medico " +
                "FROM TB_CONSULTA C " +
                "JOIN TB_PACIENTE P ON C.id_paciente = P.id_paciente " +
                "JOIN TB_MEDICO M ON C.id_medico = M.id_medico " +
                "ORDER BY C.dt_hora";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getLong("id_consulta"));
                consulta.setDataHora(rs.getTimestamp("dt_hora").toLocalDateTime());
                consulta.setMotivo(rs.getString("ds_motivo"));

                Paciente paciente = new Paciente();
                paciente.setId(rs.getLong("id_paciente"));
                paciente.setNome(rs.getString("nm_paciente"));
                consulta.setPaciente(paciente);

                Medico medico = new Medico();
                medico.setId(rs.getLong("id_medico"));
                medico.setNome(rs.getString("nm_medico"));
                consulta.setMedico(medico);

                consultas.add(consulta);
            }
        }
        return consultas;
    }

    // (Opcional para agora) Você pode implementar o pesquisarPorId, atualizar e remover
    // seguindo a mesma lógica do listar (usando JOINs no pesquisarPorId).
    // Por enquanto, vamos focar em fazer o cadastro e listagem funcionarem primeiro.
}