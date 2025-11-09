package br.com.fiap.dao;

import br.com.fiap.model.Paciente;
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
public class PacienteDAO {

    @Inject
    DataSource dataSource;

    public void cadastrar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO TB_PACIENTE (id_paciente, nm_paciente, ds_email, ds_telefone) " +
                "VALUES (SQ_PACIENTE.NEXTVAL, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection(); // MUDANÇA AQUI
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id_paciente"})) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getEmail());
            stmt.setString(3, paciente.getTelefone());
            if (paciente.getTelefone() != null) {
                stmt.setString(3, paciente.getTelefone());
            } else {
                stmt.setNull(3, java.sql.Types.VARCHAR);
            }
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    paciente.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    public List<Paciente> listar() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM TB_PACIENTE ORDER BY nm_paciente";
        try (Connection conn = dataSource.getConnection(); // MUDANÇA AQUI
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong("id_paciente");
                String nome = rs.getString("nm_paciente");
                String email = rs.getString("ds_email");
                String telefone = rs.getString("ds_telefone");
                Paciente paciente = new Paciente(id, nome, email, telefone);
                pacientes.add(paciente);
            }
        }
        return pacientes;
    }

    public Paciente pesquisarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM TB_PACIENTE WHERE id_paciente = ?";
        Paciente paciente = null;
        try (Connection conn = dataSource.getConnection(); // MUDANÇA AQUI
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nm_paciente");
                    String email = rs.getString("ds_email");
                    String telefone = rs.getString("ds_telefone");
                    paciente = new Paciente(id, nome, email, telefone);
                }
            }
        }
        return paciente;
    }

    public void atualizar(Paciente paciente) throws SQLException {
        String sql = "UPDATE TB_PACIENTE SET nm_paciente = ?, ds_email = ?, ds_telefone = ? WHERE id_paciente = ?";
        try (Connection conn = dataSource.getConnection(); // MUDANÇA AQUI
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getEmail());
            if (paciente.getTelefone() != null) { // Adicionado verificação para nulo
                stmt.setString(3, paciente.getTelefone());
            } else {
                stmt.setNull(3, java.sql.Types.VARCHAR);
            }
            stmt.setLong(4, paciente.getId());
            stmt.executeUpdate();
        }
    }

    public void remover(Long id) throws SQLException {
        String sql = "DELETE FROM TB_PACIENTE WHERE id_paciente = ?";
        try (Connection conn = dataSource.getConnection(); // MUDANÇA AQUI
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}