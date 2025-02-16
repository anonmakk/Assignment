package com.assignment.test.controller;

import com.assignment.test.dto.request.InfoRequest;
import com.assignment.test.dto.response.InfoResponse;
import com.assignment.test.service.InfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/info")
public class InfomationController {

    @Autowired
    private InfoService infoService;

    @PostMapping("/infoMain")
    public Mono<InfoResponse> infomationMain(@RequestBody @Valid InfoRequest req){

        return infoService.getInfo(req);
    }
}
