package com.studyxuexi.messageencoderweb.service;

import org.springframework.stereotype.Service;

@Service
public interface EncoderService {
    String process(String text);
}
