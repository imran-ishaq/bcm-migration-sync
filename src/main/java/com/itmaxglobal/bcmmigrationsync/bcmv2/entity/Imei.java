package com.itmaxglobal.bcmmigrationsync.bcmv2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itmaxglobal.bcmmigrationsync.model.DeviceStatus;
import com.itmaxglobal.bcmmigrationsync.model.DeviceStatusConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class Imei {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imei", length = 17)
    private String imei;

    @Column(name = "model_type", length = 10)
    private String modelType;

    @Column(name = "counterfeit")
    private Integer counterfeit;

    @Column(name = "created_date")
    private LocalDateTime createdAt;

    @Column(name = "registering_date")
    private LocalDateTime registeringDate;

    @JsonIgnore
    @Column(name = "updated_date")
    private LocalDateTime updatedAt;

    @Column(name = "brand_model_id")
    private Integer brandModelId;

    @Column(name = "imei_quantity_support")
    private Integer imeiQuantitySupport;
//
//    @Column(name = "brand", length = 500)
//    private String brand;
//
//    @Column(name = "model", length = 500)
//    private String model;

    @Column(name = "sim_swap_counter")
    private Integer simSwapCounter;

    @Column(name = "blocked")
    private Boolean isStolen;

    @Column(name = "account_status")
    @Convert(converter = DeviceStatusConverter.class)
    private DeviceStatus imeiStatus;

    @Column(name = "status_update_date")
    private LocalDateTime statusUpdateDate;

    @Column(name = "is_cloned")
    private Boolean isCloned;
}
