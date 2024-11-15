package com.itmaxglobal.bcmmigrationsync.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String SQL_HIBERNATE_DIALECT_KEY = "hibernate.dialect";
    public static final String SQL_HIBERNATE_SHOW_SQL_KEY = "hibernate.show_sql";
    public static final String SQL_HIBERNATE_FORMAT_SQL_KEY = "hibernate.format_sql";
    public static final String SQL_HIBERNATE_HBM2DDL_AUTO_KEY = "hibernate.hbm2ddl.auto";
    public static final String JOB_DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public static final String UPDATE_ACCOUNT_ACTIVITY_QUERY = "UPDATE billing.account_activity SET is_migrated = 1 WHERE account_id = ?";
    public static final String ACCOUNT_SELECT_CLAUSE = "select ac.*, aa.last_activity_date as last_activity_date_from_account_activity ";
    public static final String ACCOUNT_FROM_CLAUSE = "from bcm.billing.account ac left join bcm.billing.account_activity aa on ac.id = aa.account_id ";
    public static final String ACCOUNT_WHERE_CLAUSE = " where ac.is_migrated = 0";
    public static final String ACCOUNT_MISSING_WHERE_CLAUSE = " where ac.created_date >= '2024-11-13 06:11:04' and ac.created_date <= '2024-11-13 08:35:22'";
    public static final String ACCOUNT_SORT_KEY = "id";

    public static final String EMAIL_SUBJECT = "Migration Service";
    public static final String EMAIL_TEMPLATE_NAME = "email_template";
    public static final String EMAIL_TEST_TEMPLATE_NAME = "email_test_template";

    public static final String SMTP_REPLY = "no-reply@gmail.com";
    public static final String IMRAN_ITMAX_EMAIL = "imran.ishaq@itmaxglobal.com";
    public static final String ZAIN_ITMAX_EMAIL = "zain.abideen@itmaxglobal.com";
    public static final String LARA_ITMAX_EMAIL = "Lara.khoury@itmaxglobal.com";
    public static final String MOHIT_ITMAX_EMAIL = "Mohit.Dali@itmaxglobal.com";
    public static final String GABY_ITMAX_EMAIL = "Gaby.Makhlouf@itmaxglobal.com";
    public static final String CHRISTIAN_ITMAX_EMAIL = "christian@webtechrdc.com";
    public static final String CHARBEL_ITMAX_EMAIL = "charbel.moussa@itmaxglobal.com";
}