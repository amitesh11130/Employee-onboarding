package com.springbootapi.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    private static ResponseEntity<ResponseDTO> createResponse(HttpStatus status, String code, String description, Status s, Object data) {
        Meta meta = Meta.builder().code(code).description(description).status(s).build();
        ResponseDTO responseDTO = ResponseDTO.builder().meta(meta).data(data).build();
        return ResponseEntity.status(status).body(responseDTO);

    }

    public static ResponseEntity<ResponseDTO> success(String description, Object data) {
        return createResponse(HttpStatus.OK, "200", description, Status.SUCCESS, data);
    }

    public static ResponseEntity<ResponseDTO> created(String description, Object data) {
        return createResponse(HttpStatus.CREATED, "201", description, Status.CREATED, data);
    }

    public static ResponseEntity<ResponseDTO> notFound(String description) {
        return createResponse(HttpStatus.NOT_FOUND, "404", description, Status.NOT_FOUND, null);
    }

    public static ResponseEntity<ResponseDTO> failed(String description) {
        return createResponse(HttpStatus.BAD_REQUEST, "400", description, Status.FAILED, null);
    }
    public static ResponseEntity<ResponseDTO> failed(String error,Object errors) {
        return createResponse(HttpStatus.BAD_REQUEST, "400", error, Status.FAILED, errors);
    }

    public static ResponseEntity<ResponseDTO> deleted(String description, Object data) {
        return createResponse(HttpStatus.OK, "200", description, Status.DELETED, data);
    }

    public static ResponseEntity<ResponseDTO> updated(String description, Object data) {
        return createResponse(HttpStatus.OK, "200", description, Status.UPDATED, data);
    }


    public static ResponseEntity<ResponseDTO> error(String userCreationFailed) {
        return createResponse(HttpStatus.BAD_REQUEST, "400", userCreationFailed, Status.FAILED,null);
    }
}
