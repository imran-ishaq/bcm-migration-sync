package com.itmaxglobal.bcmmigrationsync.bcmv2.entity;

import com.itmaxglobal.bcmmigrationsync.model.ChargeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * imsi operator config that holds for each imsi prefix the corresponding operator
 * @author cme
 *
 */
@Entity
@Table(name = "imsi_operator_config")
@Getter
@Setter
public class ImsiOperatorConfig
{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   @Column(name="imsi_prefix")
   private String imsiPrefix;

   @Column(name="operator_id")
   private int operatorId;

   @Column(name="operator_name")
   private String operatorName;

   @Column(name="hss_provider")
   private String hssProvider;

   @Column(name="ocs_provider")
   private String ocsProvider;

   @Enumerated(EnumType.STRING)
   @Column(name="charge_type")
   private ChargeType chargeType;
}
