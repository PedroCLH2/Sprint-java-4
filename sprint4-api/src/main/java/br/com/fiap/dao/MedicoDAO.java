package br.com.fiap.dao;

import br.com.fiap.model.Medico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MedicoDAO {

    @Inject
    DataSource dataSource;

    public void cadastrar(Medico medico) throws SQLException {
        String sql = "INSERT INTO TB_MEDICO (id_medico, nm_medico, nr_crm, ds_especialidade) VALUES (SQ_MEDICO.NEXTVAL, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_medico"})) {
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getEspecialidade());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    medico.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    public List<Medico> listar() throws SQLException {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM TB_MEDICO ORDER BY nm_medico";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Medico medico = new Medico(
                        rs.getLong("id_medico"),
                        rs.getString("nm_medico"),
                        rs.getString("nr_crm"),
                        rs.getString("ds_especialidade")
                );
                medicos.add(medico);
            }
        }
        return medicos;
    }

    public Medico pesquisarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM TB_MEDICO WHERE id_medico = ?";
        Medico medico = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    medico = new Medico(
                            rs.getLong("id_medico"),
                            rs.getString("nm_medico"),
                            rs.getString("nr_crm"),
                            rs.getString("ds_especialidade")
                    );
                }
            }
        }
        return medico;
    }

    public void atualizar(Medico medico) throws SQLException {
        String sql = "UPDATE TB_MEDICO SET nm_medico = ?, nr_crm = ?, ds_especialidade = ? WHERE id_medico = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setLong(4, medico.getId());
            stmt.executeUpdate();
        }
    }

    public void remover(Long id) throws SQLException {
        String sql = "DELETE FROM TB_MEDICO WHERE id_medico = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}