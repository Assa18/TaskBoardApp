package edu.bbte.idde.mtim2378.controller;

import edu.bbte.idde.mtim2378.controller.dto.ErrorResponse;
import edu.bbte.idde.mtim2378.service.exception.BoardNotFoundException;
import edu.bbte.idde.mtim2378.service.exception.ServiceException;
import edu.bbte.idde.mtim2378.service.exception.TodoNotFoundException;
import edu.bbte.idde.mtim2378.service.exception.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServiceException(ServiceException e) {
        return new ErrorResponse("Internal error", e.getMessage());
    }

    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTodoNotFoundException(TodoNotFoundException e) {
        return new ErrorResponse("Todo not found", e.getMessage());
    }

    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleBoardNotFoundException(BoardNotFoundException e) {
        return new ErrorResponse("Board not found", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ErrorResponse(
                ex.getBindingResult().getFieldErrors().get(0).getField(),
                ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String field = ex.getName();
        String message = String.format("Value '%s' could not be converted to type %s",
                ex.getValue(), ex.getRequiredType()
                        != null ? ex.getRequiredType().getSimpleName() : null);

        return new ErrorResponse(field, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConversionError(ConstraintViolationException ex) {
        return new ErrorResponse("Constraint violation", ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ErrorResponse("Email not found", e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        return new ErrorResponse("User not found", e.getMessage());
    }
}
