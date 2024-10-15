package com.itmaxglobal.bcmmigrationsync.bcmv2.mapper;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.BrandModel;

public class BrandModelMapper {

    public static BrandModel toEntity(Account account) {
        BrandModel brandModel = new BrandModel();

        brandModel.setTacNumber(account.getImei().substring(0, 8));
        brandModel.setBrand(account.getBrand());
        brandModel.setModel(account.getModel());
        brandModel.setImeiQuantitySupport(account.getImeiQuantitySupport());

        return brandModel;

    }
}
