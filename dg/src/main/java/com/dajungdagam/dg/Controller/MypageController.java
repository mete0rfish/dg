package com.dajungdagam.dg.Controller;

import com.dajungdagam.dg.domain.dto.UserKakaoLoginResponseDto;
import com.dajungdagam.dg.domain.dto.UserWrittenPostDto;
import com.dajungdagam.dg.jwt.jwtTokenDecoder;
import com.dajungdagam.dg.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MypageController {

    private UserService userService;
    private jwtTokenDecoder tokenDecoderoken;

    public MypageController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/mypage/{userId}")
    public ResponseEntity<String> getWrittenPosts(Authentication authentication) {
        UserWrittenPostDto userWrittenPostDto =

        return ResponseEntity.ok().body(authentication.getName() + "님의 마이페이지 입니다.");
    }

    // JWT Token 복호화 테스트
    @GetMapping("/jwt/claims")
    public String getClaims(String jwtToken){
        Jws<Claims> claims = tokenDecoderoken.getClaims(jwtToken);
        log.info(claims.getBody().get())
    }

}
