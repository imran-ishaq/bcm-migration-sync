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
   @Column(name = "model_type")
   private String modelType;
   private boolean counterfeit;
   @Column(name = "yearly_billing_fees")
   private Double yearlyBillingFees;
   @Column(name = "created_date")
   private LocalDateTime createdDate;
   @Column(name = "registering_date")
   private LocalDateTime registeringDate;
   @Column(name = "updated_date")
   private LocalDateTime updatedDate;
   @Column(name = "billing_date")
   private LocalDate billingDate;
   @Column(name = "disabling_date")
   private LocalDateTime disablingDate;
   @Column(name = "billing_status")
   private String billingStatus;
   private boolean roaming;
   @Column(name = "primary_account")
   private boolean primaryAccount;
   @Column(name = "imei_quantity_support")
   private Integer imeiQuantitySupport;
   private String brand;
   private String model;
   @Column(name = "sim_swap_counter")
   private Integer simSwapCounter;
   @Column(name = "last_activity_date")
   private LocalDateTime lastActivityDate;
   private Integer operator;
   private Boolean blocked;
   private String recentSims;
   @Column(name = "account_status")
   private Integer accountStatus;
   @Column(name = "status_update_date")
   private LocalDateTime statusUpdateDate;
   private Integer churnStatus;
   @Column(name = "is_cloned")
   private Boolean isCloned;
   @Column(name = "account_operator")
   private Integer accountOperator;
   @Column(name = "is_migrated")
   private Boolean isMigrated;

   public Account(){}
}
