package org.example.oracle;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OracleConnection {
    private final String url;
    private final String id;
    private final String password;

    private OracleConnectionPoolDataSource oracleConnPoolDataSource;
    private PooledConnection pooledConnection;

    public OracleConnection(String url, String id, String password) throws SQLException {
        this.url = url;
        this.id = id;
        this.password = password;

        createOracleConnectionPool();
        getOracleConnectionFromPool();
    }

    private void createOracleConnectionPool() throws SQLException {
        this.oracleConnPoolDataSource = new OracleConnectionPoolDataSource();
        this.oracleConnPoolDataSource.setURL(this.url);
        this.oracleConnPoolDataSource.setUser(this.id);
        this.oracleConnPoolDataSource.setPassword(this.password);
    }

    private void getOracleConnectionFromPool() throws SQLException {
        this.pooledConnection = this.oracleConnPoolDataSource.getPooledConnection();
    }

    public void executeSQL(String sql) throws SQLException {
        Connection conn = this.pooledConnection.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.execute();
        conn.close();
    }

}
