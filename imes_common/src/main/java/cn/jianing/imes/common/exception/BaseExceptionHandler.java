package cn.jianing.imes.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Void> error (HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        e.printStackTrace();
        if (e.getClass().equals(RuntimeException.class)) {
            CommonException commonException = (CommonException) e;
            return new ResponseEntity<>(commonException.getHttpStatus());
        } else {
            return new ResponseEntity<>(new CommonException().getHttpStatus());
        }
    }
}
