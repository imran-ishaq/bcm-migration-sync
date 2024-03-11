package com.itmaxglobal.bcmmigrationsync.bcmv2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "imsi_msisdn")
@Getter
@Setter
@ToString
public class ImsiMsisdn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "imsi", length = 100)
    private String imsi;

    @Column(name = "msisdn", length = 50)
    private String msisdn;

    @Column(name = "roaming")
    private Boolean roaming;

    @Column(name = "operator")
    private Integer operator;

    @Column(name = "account_operator")
    private Integer accountOperator;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "registered_at")
    private LocalDateTime registeringDate;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
