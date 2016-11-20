package ru.prgmt.warehouse.database.sql;

import ru.prgmt.warehouse.io.ArgumentInput;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Вспомогательный класс для представления результатов запроса SQL ({@link ResultSet})
 * в виде локального системного интерфейса {@link ArgumentInput}.
 */
public class ResultSetInput implements ArgumentInput {
    private ResultSet rset;

    /**
     * Конструктор
     * @param rset Результат запроса SQL
     */
    public ResultSetInput(ResultSet rset) {
        this.rset = rset;
    }

    @Override
    public String argument(String key) {
        try {
            return rset.getString(key.toUpperCase());
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить значение поля", e);
        }
    }
}
