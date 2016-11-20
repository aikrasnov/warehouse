package ru.prgmt.warehouse.database.sql;

import ru.prgmt.warehouse.context.Context;
import ru.prgmt.warehouse.context.ContextAware;
import ru.prgmt.warehouse.context.Parameters;
import ru.prgmt.warehouse.database.Database;
import ru.prgmt.warehouse.device.Device;
import ru.prgmt.warehouse.device.DeviceFactory;
import ru.prgmt.warehouse.device.DeviceNotFoundException;
import ru.prgmt.warehouse.util.BeanAccessor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>Реализация БД, использующая SQL РСУБД в качестве хранилища.</p>
 * <p>Не тестировалась на работоспособность.</p>
 */
public class SqlDatabase implements Database, ContextAware {
    private Context context;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;

    /**
     * Конструктор
     * @param jdbcDriver Название класса JDBC драйвера СУБД
     * @param jdbcUrl Адрес подключения
     * @param jdbcUsername Учётная запись
     * @param jdbcPassword Пароль
     */
    public SqlDatabase(String jdbcDriver, String jdbcUrl, String jdbcUsername, String jdbcPassword) {
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не удалось инициализировать драйвер JDBC: " + jdbcDriver);
        }

        this.jdbcUrl = jdbcUrl;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Device find(final int sku) throws DeviceNotFoundException {
        try {
            return execute(new Action<Device>() {
                @Override
                public Device execute(Connection connection) throws Exception {
                    try (PreparedStatement stmt = connection.prepareStatement("SELECT \"TYPE\" FROM DEVICES WHERE SKU = ?")) {
                        stmt.setString(1, String.valueOf(sku));
                        ResultSet rset = stmt.executeQuery();

                        if (rset.next()) {
                            String typeId = rset.getString("TYPE");
                            DeviceFactory<Device> factory = context.factories().get(typeId);
                            String tableName = factory.typeId() + "_DEVICES";

                            try (PreparedStatement deviceStmt = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE SKU = ?")) {
                                deviceStmt.setString(1, String.valueOf(sku));
                                rset = deviceStmt.executeQuery();

                                if (rset.next()) {
                                    Parameters parameters = factory.newParameters();
                                    Map<String, Object> values = parameters.parse(new ResultSetInput(rset));

                                    return factory.newDevice(values);
                                }
                            }
                        }
                    }

                    throw new DeviceNotFoundException(sku);
                }
            });

        } catch (DeviceNotFoundException e) {
            throw e;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(final int sku) throws DeviceNotFoundException {
        try {
            execute(new Action<Void>() {
                @Override
                public Void execute(Connection connection) throws Exception {
                    try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM DEVICES WHERE SKU = ?")) {
                        stmt.setString(1, String.valueOf(1));

                        if (0 == stmt.executeUpdate()) {
                            throw new DeviceNotFoundException(sku);
                        }
                    }

                    return null;
                }
            });

        } catch (DeviceNotFoundException e) {
            throw e;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        try {
            execute(new Action<Void>() {
                @Override
                public Void execute(Connection connection) throws Exception {
                    try (Statement stmt = connection.createStatement()) {
                        stmt.execute("DELETE FROM DEVICES");
                    }

                    return null;
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(final Device device) {
        try {
            execute(new Action<Void>() {
                @Override
                public Void execute(Connection connection) throws Exception {
                    DeviceFactory factory = context.factories().get(device.getClass());

                    try {
                        connection.setAutoCommit(false);

                        delete(device.getSku());

                        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO DEVICES (SKU, \"TYPE\") VALUES(?, ?)")) {
                            stmt.setString(1, String.valueOf(device.getSku()));
                            stmt.setString(2, factory.typeId());
                            stmt.executeUpdate();
                        }

                        List<String> values = new ArrayList<>();
                        StringBuilder keys = new StringBuilder();
                        StringBuilder placeholders = new StringBuilder();
                        for (Parameters.Parameter parameter : factory.newParameters()) {
                            Object value = BeanAccessor.get(device, parameter.getKey());
                            String wrappedValue = parameter.getWrapper().wrap(value);
                            values.add(wrappedValue);

                            keys.append('"');
                            keys.append(parameter.getKey());
                            keys.append('"');
                            keys.append(',');

                            placeholders.append("?,");
                        }

                        keys.deleteCharAt(keys.length() - 1);
                        placeholders.deleteCharAt(placeholders.length() - 1);

                        String tableName = factory.typeId().toUpperCase() + "_DEVICES";
                        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + tableName + " (" + keys + ") VALUES(" + placeholders + ')')) {
                            for (int idx = 0; idx < values.size(); ++idx) {
                                stmt.setString(idx + 1, values.get(idx));
                            }

                            stmt.executeUpdate();
                        }

                    } finally {
                        connection.commit();
                    }

                    return null;
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public Iterator<Device> iterator() {
        try {
            List<Integer> skuList = execute(new Action<List<Integer>>() {
                @Override
                public List<Integer> execute(Connection connection) throws Exception {
                    List<Integer> result = new ArrayList<>();

                    try (Statement stmt = connection.createStatement()) {
                        ResultSet rset = stmt.executeQuery("SELECT SKU FROM DEVICES");

                        while (rset.next()) {
                            result.add(Integer.valueOf(rset.getString("SKU")));
                        }
                    }

                    return result;
                }
            });

            final Iterator<Integer> skuIter = skuList.iterator();

            return new Iterator<Device>() {
                @Override
                public boolean hasNext() {
                    return skuIter.hasNext();
                }

                @Override
                public Device next() {
                    int sku = skuIter.next();

                    try {
                        return find(sku);
                    } catch (DeviceNotFoundException e) {
                        throw new RuntimeException("Не удалось получить устройство для SKU " + sku);
                    }
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T execute(Action<T> action) throws Exception {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            return action.execute(connection);

        } catch (SQLException e) {
            throw new RuntimeException("Не удалось выполнить действие", e);

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                // Ignore
            }
        }
    }

    private interface Action<T> {
        T execute(Connection connection) throws Exception;
    }
}
