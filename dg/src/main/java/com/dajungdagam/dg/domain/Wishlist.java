package com.dajungdagam.dg.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table
@Getter
@Builder
public class Wishlist {
    @Id
    @GeneratedValue
    private int id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="gbPost_id")
    private ArrayList<GbPost> gbPost;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="tradePost_id")
    private ArrayList<TradePost> tradePost;

    @Column
    private LocalDateTime createdTime;

    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public Wishlist() {

    }
}
