package com.itmaxglobal.bcmmigrationsync.bcmv2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "brand_model")
public class BrandModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_model_id")
    private Integer brandModelId;

    @Column(name = "tac_number")
    private String tacNumber;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "imei_quantity_support")
    private Integer imeiQuantitySupport;
}
