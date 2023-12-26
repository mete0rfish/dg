package com.dajungdagam.dg.service;

import com.dajungdagam.dg.domain.RoleType;
import com.dajungdagam.dg.domain.User;
import com.dajungdagam.dg.domain.dto.UserKakaoLoginResponseDto;
import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.jwt.jwtTokenProvider;
import com.dajungdagam.dg.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// TODO DB에 user정보가 존재하면 DB에 가입하는 절차 -> 했든 안했든 JWT 토큰 발행
// https://jules-jc.tistory.com/239
@Service
public class UserService {

    @Autowired
    private UserJpaRepository repository;

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000* 60 * 60l;

    private static int userIdx = 0;

    public UserKakaoLoginResponseDto kakaoLogin(Map<String, Object> userInfo) {
        String nickName = (String)userInfo.get("nickname");

        UserResponseDto userResponseDto = findByUserKakaoNickName(nickName);

        if(userResponseDto == null) {
            // 기존에 등록된 유저가 아니면, DB에 저장 후 다시 불러오기
            signUp(userInfo);
            userResponseDto = findByUserKakaoNickName(nickName);
        }

        String token = jwtTokenProvider.createToken(nickName, secretKey, expiredMs);
        return new UserKakaoLoginResponseDto(HttpStatus.OK, token, userResponseDto.getUser());
    }

    public UserResponseDto findByUserKakaoNickName(String kakaoNickName){
        User user = repository.findByNickName(kakaoNickName);
        if(user == null){
            return null;
        }
        return new UserResponseDto(user);
    }

    @Transactional
    public int signUp(Map<String, Object> userInfo) {
        int id = 0;
        String nickName = (String)userInfo.get("nickname");
        try{
            User user = new User(0, nickName, RoleType.USER);
            id = repository.save(user).getId();


        } catch(Exception e){
            System.out.println(e);
        }
        return id;
    }


}
