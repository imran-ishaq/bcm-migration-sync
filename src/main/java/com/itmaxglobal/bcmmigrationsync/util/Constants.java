package com.itmaxglobal.bcmmigrationsync.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static String SQL_HIBERNATE_DIALECT_KEY = "hibernate.dialect";
    public static String SQL_HIBERNATE_SHOW_SQL_KEY = "hibernate.show_sql";
    public static String SQL_HIBERNATE_FORMAT_SQL_KEY = "hibernate.format_sql";
    public static String SQL_HIBERNATE_HBM2DDL_AUTO_KEY = "hibernate.hbm2ddl.auto";
    public static final String JOB_DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    public static final String UPDATE_ACCOUNT_ACTIVITY_QUERY = "UPDATE billing.account_activity SET is_migrated = 1 WHERE account_id = ?";
    public static final String ACCOUNT_SELECT_CLAUSE = "select ac.*, aa.last_activity_date as last_activity_date_from_account_activity ";
    public static final String ACCOUNT_FROM_CLAUSE = "from bcm.billing.account ac left join bcm.billing.account_activity aa on ac.id = aa.account_id ";
    public static final String ACCOUNT_WHERE_CLAUSE = " where ac.is_migrated = 0";
    public static final String ACCOUNT_SORT_KEY = "id";

}
