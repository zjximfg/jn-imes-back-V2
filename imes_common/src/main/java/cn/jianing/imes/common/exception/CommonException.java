package cn.jianing.imes.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class CommonException extends RuntimeException {

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public CommonException(HttpStatus httpStatus) {
        super();
        this.httpStatus = httpStatus;
    }
}
