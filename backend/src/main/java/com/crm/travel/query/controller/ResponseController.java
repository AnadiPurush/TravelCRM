package com.crm.travel.query.controller;

import com.crm.travel.query.enums.QueriesPriority;
import com.crm.travel.query.enums.QueriesStatus;
import com.crm.travel.common.enums.Mapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/response")
public class ResponseController {
    @GetMapping("query/generate/dropdown/priorities/status/response")
    public Map<String, Object> generateResponse() {

        return Map.of(
                "priorities", Mapper.map(QueriesPriority.values()),
                "status", Mapper.map(QueriesStatus.values())
        );
    }
}
