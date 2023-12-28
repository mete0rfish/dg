package com.dajungdagam.dg.api;

import com.dajungdagam.dg.domain.User;
import com.dajungdagam.dg.domain.dto.UserKakaoLoginResponseDto;
import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TestController {

    private final kakaoApi kakaoApi;

    @Autowired
    private UserService userService;


    // 프론트(테스트용)
    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("kakaoApiKey", kakaoApi.getKakaoApiKey());
        model.addAttribute("redirectUri", kakaoApi.getKakaoRedirectUri());
        return "login";
    }

    // 카카오 로그인으로 nick 및 jwtToken 저장
    @ResponseBody
    @RequestMapping("/login/oauth2/code/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code){
        // 1. 인가 코드 받기
        log.info("eeeee");

        // 2. 토큰 받기
        ArrayList<String> tokens = new ArrayList<>();
        tokens = kakaoApi.getAccessToken(code);

        String accessToken = tokens.get(0);
        String refreshToken = tokens.get(1);

        // 3. 사용자 정보 받기
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        // 4. 이미 회원가입된 회원인지 확인 + 저장
        // 그다음에 jwt 토큰 발행
        UserKakaoLoginResponseDto userKakaoLoginResponseDto =  userService.kakaoLogin(userInfo);

        // 5. 상태 ,메시지, 객체 (UserkakaoLoginResponseDto)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", StandardCharsets.UTF_8));



        // ~~ 확인용 ~~
        //String email = (String)userInfo.get("email");
        String kakaoName = userKakaoLoginResponseDto.getUser().getKakaoName();
        String jwtToken = userKakaoLoginResponseDto.getJwtToken();

        //System.out.println("email = " + email);
        log.info("kakaoName: "+ kakaoName);
        log.info("jwtToken: "+ jwtToken);

        return new ResponseEntity<>(userKakaoLoginResponseDto, headers, userKakaoLoginResponseDto.getHttpStatus());
    }

//    @PostMapping("/login/details/v1")
//    public ResponseEntity<String> loginDetailsNickName(@RequestParam String kakaoName, @RequestParam String nickName){
//        // 1. kakaoName 으로 유저 찾기
//        UserResponseDto userResponseDto = userService.findByUserKakaoNickName(kakaoName);
//        User user = userResponseDto.getUser();
//
//        // 2. user의 nickName에 추가하기
//        user.setNickName(nickName);
//
//    }

    @PostMapping("/login/details/v2")
    public ResponseEntity<String> loginDetailsArea(@RequestParam String kakaoName, @RequestParam String gu_name, @RequestParam String dong_name){
        // 1. kakaoName 으로 유저 찾기
    }
}
