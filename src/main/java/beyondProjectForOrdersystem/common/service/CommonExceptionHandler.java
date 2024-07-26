package beyondProjectForOrdersystem.common.service;

import beyondProjectForOrdersystem.common.dto.CommonErrorDto;
import beyondProjectForOrdersystem.common.dto.CommonResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonErrorDto> entityNotFoundHandler(EntityNotFoundException e){
        CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(commonErrorDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonErrorDto> illegalHandler(IllegalArgumentException e){
        CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(commonErrorDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonErrorDto> validHandler(MethodArgumentNotValidException e){
        CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), "arguments is not valid.");
        return new ResponseEntity<>(commonErrorDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonErrorDto> exceptionHandler(Exception e){
        CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERROR.");
        return new ResponseEntity<>(commonErrorDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
