package com.chiliasmstudio.worldResidenceManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.sql.*;
import java.util.UUID;

public class SqlDatabase {
    public static HikariDataSource dataSource;

    public void addMaxSize(UUID id, long maxSizeToAdd) {
        long currentMaxSize = getMaxSize(id);
        long newMaxSize = currentMaxSize + maxSizeToAdd;

        String sql = "UPDATE worldresidencemanager_playerdata SET max_size = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, newMaxSize);
            stmt.setString(2, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reduceMaxSize(UUID id, long maxSizeToReduce) {
        long currentMaxSize = getMaxSize(id);
        long newMaxSize = Math.max(0, currentMaxSize - maxSizeToReduce); // 確保不會低於 0

        String sql = "UPDATE worldresidencemanager_playerdata SET max_size = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, newMaxSize);
            stmt.setString(2, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getMaxSize(UUID id) {
        String sql = "SELECT max_size FROM worldresidencemanager_playerdata WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("max_size");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 如果沒有找到資料，返回 -1
    }

    //Free size

    public void addFreeSize(UUID id, long freeSizeToAdd) {
        long currentFreeSize = getFreeSize(id);
        long newFreeSize = currentFreeSize + freeSizeToAdd;

        String sql = "UPDATE worldresidencemanager_playerdata SET free_size = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, newFreeSize);
            stmt.setString(2, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reduceFreeSize(UUID id, long freeSizeToReduce) {
        long currentFreeSize = getFreeSize(id);
        long newFreeSize = Math.max(0, currentFreeSize - freeSizeToReduce); // 確保不會低於 0

        String sql = "UPDATE worldresidencemanager_playerdata SET free_size = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, newFreeSize);
            stmt.setString(2, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getFreeSize(UUID id) {
        String sql = "SELECT free_size FROM worldresidencemanager_playerdata WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("free_size");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 如果沒有找到資料，返回 -1
    }

    //Current size

    public void addCurrentSize(UUID id, long currentSizeToAdd) {
        long currentCurrentSize = getCurrentSize(id);
        long newCurrentSize = currentCurrentSize + currentSizeToAdd;

        String sql = "UPDATE worldresidencemanager_playerdata SET current_size = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, newCurrentSize);
            stmt.setString(2, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reduceCurrentSize(UUID id, long currentSizeToReduce) {
        long currentCurrentSize = getCurrentSize(id);
        long newCurrentSize = Math.max(0, currentCurrentSize - currentSizeToReduce); // 確保不會低於 0

        String sql = "UPDATE worldresidencemanager_playerdata SET current_size = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, newCurrentSize);
            stmt.setString(2, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getCurrentSize(UUID id) {
        String sql = "SELECT current_size FROM worldresidencemanager_playerdata WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("current_size");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 如果沒有找到資料，返回 -1
    }

    public boolean isDataExists(UUID id) {
        String sql = "SELECT 1 FROM worldresidencemanager_playerdata WHERE id = ? LIMIT 1";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertData(UUID id, long maxSize, long freeSize, long currentSize) {
        String sql = "INSERT INTO worldresidencemanager_playerdata (id, max_size, free_size, current_size) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());
            stmt.setLong(2, maxSize);
            stmt.setLong(3, freeSize);
            stmt.setLong(4, currentSize);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateData(UUID id, long maxSize, long freeSize, long currentSize) {
        String sql = "UPDATE worldresidencemanager_playerdata SET max_size = ?, free_size = ?, current_size = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, maxSize);
            stmt.setLong(2, freeSize);
            stmt.setLong(3, currentSize);
            stmt.setString(4, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTableIfNotExists() {
        if (isTableExists("worldresidencemanager_playerdata")) {
            WorldResidenceManager.worldResidenceManager.getComponentLogger().info(Component.text("Tablet exists!").color(TextColor.color(255, 255, 255)));
            return;
        }

        String sql = "CREATE TABLE worldresidencemanager_playerdata ("
                + "id CHAR(36) PRIMARY KEY, "
                + "max_size BIGINT NOT NULL, "
                + "free_size BIGINT NOT NULL, "
                + "current_size BIGINT NOT NULL "
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            WorldResidenceManager.worldResidenceManager.getComponentLogger().info(Component.text("New Tablet created!").color(TextColor.color(0, 255, 0)));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isTableExists(String tableName) {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getTables(null, null, tableName, null)) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean connect() {
        if (WorldResidenceManager.config.Database().equalsIgnoreCase("changeme"))
            return false;

        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://"
                    + WorldResidenceManager.config.Host()
                    + ":"
                    + WorldResidenceManager.config.Port()
                    + "/"
                    + WorldResidenceManager.config.Database()
                    + "?useSSL=false&serverTimezone=UTC"); // 避免時區錯誤

            config.setUsername(WorldResidenceManager.config.Username());
            config.setPassword(WorldResidenceManager.config.Password());
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setMaxLifetime(600000);
            config.setConnectionTimeout(10000);

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            HikariDataSource newDataSource = new HikariDataSource(config);

            try (var conn = newDataSource.getConnection()) {
                dataSource = newDataSource;
                return true;
            }

        } catch (SQLException e) {
            return false;
        }
    }

    public void close() {
        dataSource.close();
    }
}
