package com.springbootapi.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTOError {

    private String defaultMessage;
    private String objectName;
    private String field;
    private String rejectedValue;

}
