package br.com.fiap.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.sql.SQLException;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        exception.printStackTrace();

        if (exception instanceof SQLException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensageErro("Erro no banco de dados. Tente novamente mais tarde.", 500))
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new MensageErro("Ocorreu um erro inesperado no servidor.", 500))
                .build();
    }
}