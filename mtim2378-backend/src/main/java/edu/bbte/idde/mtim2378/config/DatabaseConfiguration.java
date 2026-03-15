package edu.bbte.idde.mtim2378.config;

public class DatabaseConfiguration {

    private String dbImplType;
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
    private int dbConnectionPoolSize;

    public String getDbImplType() {
        return dbImplType;
    }

    public void setDbImplType(String dbImplType) {
        this.dbImplType = dbImplType;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public int getDbConnectionPoolSize() {
        return dbConnectionPoolSize;
    }

    public void setDbConnectionPoolSize(int dbConnectionPoolSize) {
        this.dbConnectionPoolSize = dbConnectionPoolSize;
    }
}
