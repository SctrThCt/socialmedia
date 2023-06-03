package stc.test.socialmedia;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import stc.test.socialmedia.error.IllegalRequestDataException;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleNotValidArgument(MethodArgumentNotValidException ex) {
        String bindingResult = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> String.format("[%s],[%s]", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining("\n"));
        return new ResponseEntity<>(bindingResult, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrity(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(ex.getCause().getCause().getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<String> handleNotFound(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    ResponseEntity<String> handleIO(IOException ex) {
        return new ResponseEntity<>("Ошибка во время загрузки файла", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalRequestDataException.class)
    ResponseEntity<String> handleIllegalRequestData(IllegalRequestDataException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
