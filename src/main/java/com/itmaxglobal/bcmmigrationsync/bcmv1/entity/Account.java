package com.itmaxglobal.bcmmigrationsync.bcmv1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "account", schema = "billing")
@Getter
@Setter
@ToString
public class Account
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String imei;
   private String imsi;
   private String msisdn;
   private String modelType;
   private boolean counterfeit;
   private Double yearlyBillingFees;
   private LocalDateTime createdDate;
   private LocalDateTime registeringDate;
   private LocalDateTime updatedDate;
   private LocalDate billingDate;
   private LocalDateTime disablingDate;
   private String billingStatus;
   private boolean roaming;
   private boolean primaryAccount;
   private Integer imeiQuantitySupport;
   private String brand;
   private String model;
   private Integer simSwapCounter;
   private LocalDateTime lastActivityDate;
   private Integer operator;
   private Boolean blocked;
   private String recentSims;
   private Integer accountStatus;
   private LocalDateTime statusUpdateDate;
   private Integer churnStatus;
   private Boolean isCloned;
   private Integer accountOperator;
   private Integer isMigrated;

   public Account(){}
}
