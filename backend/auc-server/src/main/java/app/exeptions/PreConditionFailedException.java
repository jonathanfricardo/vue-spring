package app.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class PreConditionFailedException extends RuntimeException {
    public PreConditionFailedException(String message) {
        super(message);
    }
}
