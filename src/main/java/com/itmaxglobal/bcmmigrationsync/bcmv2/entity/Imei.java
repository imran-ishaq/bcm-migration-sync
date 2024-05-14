package com.itmaxglobal.bcmmigrationsync.bcmv2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "imei")
@Getter
@Setter
@ToString
public class Imei {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imei", length = 50)
    private String imei;

    @Column(name = "model_type", length = 50)
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

    @Column(name = "imei_quantity_support")
    private Integer imeiQuantitySupport;

    @Column(name = "brand", length = 500)
    private String brand;

    @Column(name = "model", length = 500)
    private String model;

    @Column(name = "sim_swap_counter")
    private Integer simSwapCounter;

    @Column(name = "blocked")
    private Boolean isStolen;

    @Column(name = "account_status")
    private Integer imeiStatus;

    @Column(name = "status_update_date")
    private Date statusUpdateDate;

    @Column(name = "is_cloned")
    private Boolean isCloned;
}
