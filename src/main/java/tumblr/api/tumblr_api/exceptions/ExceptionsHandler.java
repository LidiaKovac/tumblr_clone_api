package tumblr.api.tumblr_api.exceptions;


import io.jsonwebtoken.ExpiredJwtException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.Date;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler({
            BadRequestException.class,
            MissingServletRequestPartException.class,
            DataIntegrityViolationException.class,
            HttpMediaTypeNotSupportedException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handle400(Exception error) {
        return new ErrorDTO(error.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ElementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleEntityNotFound(ElementNotFoundException error) {
        return new ErrorDTO("Entity not found: " + error.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
    public ErrorDTO handleTooBig(SizeLimitExceededException error) {
        return new ErrorDTO("The file was too big!", LocalDateTime.now());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleEndpointNotFound() {
        return new ErrorDTO("This endpoint does not exist!", LocalDateTime.now());
    }

    @ExceptionHandler({UnauthorizedException.class, ExpiredJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handle401(UnauthorizedException err) {
        return new ErrorDTO(err.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({AccessDeniedException.class, ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleForbidden(Exception e) {
        return new ErrorDTO("You do not have the necessary permissions to do this operation. Details: \n " + e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleGeneric(Exception e) {
        e.printStackTrace();
        return new ErrorDTO("Errore generico, risolveremo il prima possibile", LocalDateTime.now());
    }
}
