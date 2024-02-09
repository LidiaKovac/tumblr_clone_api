package tumblr.api.tumblr_api.exceptions;

import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.HibernateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler({
            BadRequestException.class,
            MissingServletRequestPartException.class,
            DataIntegrityViolationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handle400(Exception error) {
        return new ErrorDTO(error.getMessage(), LocalDateTime.now());
    }

//    @ExceptionHandler(MissingServletRequestPartException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorDTO handleFD400(MissingServletRequestPartException error) {
//        return new ErrorDTO(error.getMessage(), LocalDateTime.now());
//    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorDTO alreadyExists(BadRequestException error) {
//        return new ErrorDTO("This email is already in use. Details: " + error, LocalDateTime.now());
//    }


//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorDTO handle500(Exception error) {
//        return new ErrorDTO("Dang! What's happening? Details: " + error.getMessage(), LocalDateTime.now());
//    }

    @ExceptionHandler(ElementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleEntityNotFound(ElementNotFoundException error) {
        return new ErrorDTO("Entity not found: " + error.getMessage(), LocalDateTime.now());
    }
}
