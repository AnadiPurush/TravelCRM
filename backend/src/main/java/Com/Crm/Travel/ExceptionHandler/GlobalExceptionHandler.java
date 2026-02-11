//package Com.Crm.Travel.ExceptionHandler;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleAll(Exception ex) {
//
//        return ResponseEntity.unprocessableEntity().body(new ApiError("INTERNAL_ERROR",
//                "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR.value()));
//    }
//
//    // validation error 404
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
//        String msg = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
//        return ResponseEntity.unprocessableEntity().body(new ApiError("VALIDATION_ERROR",
//                msg, HttpStatus.BAD_REQUEST.value()));
//    }
//
//}