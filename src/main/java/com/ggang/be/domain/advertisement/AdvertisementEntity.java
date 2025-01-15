package com.ggang.be.domain.advertisement;

import com.ggang.be.domain.BaseTimeEntity;
import jakarta.persistence.*;

@Entity(name =  "advertisement")
public class AdvertisementEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;
}
