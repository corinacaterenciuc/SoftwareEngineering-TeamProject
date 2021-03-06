package theotherhalf.superconference.api;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import theotherhalf.superconference.exceptions.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
    //other exception handlers

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(
            ValidationException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<Object> handleServiceException(
            ServiceException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

    @ExceptionHandler(ControllerException.class)
    protected ResponseEntity<Object> handleControllerException(
            ControllerException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

    @ExceptionHandler(ConferenceManagementSystemException.class)
    protected ResponseEntity<Object> handleGenericException(
            ConferenceManagementSystemException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

    @ExceptionHandler(CMSForbidden.class)
    protected ResponseEntity<Object> handleForbiddenException(
            CMSForbidden ex) {
        ApiError apiError = new ApiError(UNAUTHORIZED);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }
}