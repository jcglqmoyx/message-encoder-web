package com.studyxuexi.messageencoderweb.controller;

import com.studyxuexi.messageencoderweb.service.EncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EncoderController {
    @Autowired
    private EncoderService encoderService;

    @PostMapping("/api/")
    public String index(@RequestParam("text") String text) {
        return encoderService.process(text);
    }
}
