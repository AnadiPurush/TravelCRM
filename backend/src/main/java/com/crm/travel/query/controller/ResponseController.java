package com.crm.travel.query.controller;

import com.crm.travel.common.enums.EnumResponse;
import com.crm.travel.common.enums.Mapper;
import com.crm.travel.query.enums.QueriesPriority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("api/v1/response")
public class ResponseController {
    @GetMapping("query/generate/dropdown/priorities/status/response")
    public List<EnumResponse> generateResponse() {

        return Mapper.map(QueriesPriority.class);

    }
}
