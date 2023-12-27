package com.dajungdagam.dg.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MypageController {

    @PostMapping("/mypage/{userId}")
    public ResponseEntity<String> gotoMypage(Authentication authentication) {
        return ResponseEntity.ok().body(authentication.getName() + "님의 마이페이지 입니다.");
    }

}
