package com.gft.inditex.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestData {

    private Map<String, String> pathVariables;
    private Map<String, String> queryParams;
    private String body;
}
