package com.itmaxglobal.bcmmigrationsync.bcmv1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
   @Column(name = "created_date")
   private LocalDateTime createdDate;
   @Column(name = "registering_date")
   private LocalDateTime registeringDate;
   @Column(name = "updated_date")
   private LocalDateTime updatedDate;
   private boolean roaming;
   @Column(name = "imei_quantity_support")
   private Integer imeiQuantitySupport;
   private String brand;
   private String model;
   @Column(name = "sim_swap_counter")
   private Integer simSwapCounter;
   @Column(name="last_Activity_date")
   private LocalDateTime lastActivityDate;
   private Integer operator;
   private Boolean blocked;
   @Column(name = "account_status")
   private Integer accountStatus;
   @Column(name = "status_update_date")
   private LocalDateTime statusUpdateDate;
   @Column(name = "is_cloned")
   private Integer isCloned;
   @Column(name = "account_operator")
   private Integer accountOperator;
   @Column(name = "is_migrated")
   private Boolean isMigrated;

   public Account(){}
}
