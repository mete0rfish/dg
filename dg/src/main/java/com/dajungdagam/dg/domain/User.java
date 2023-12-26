package com.dajungdagam.dg.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name="users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true, length = 40)
    @Nullable
    private String email;
    @Column(unique = true, length = 10)
    @Nullable
    private String nickName;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="area_id")
    private Area area;

    @Column
    private RoleType role;

    @Column(length = 50)
    private String info;

    public User(int id, String email, String nickName, Area area, RoleType role, String info) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.area = area;
        this.role = role;
        this.info = info;
    }

    public User(int id, String nickName, RoleType role) {
        this.id = id;
        this.nickName = nickName;
        this.role = role;
    }

    public User() {

    }
}
