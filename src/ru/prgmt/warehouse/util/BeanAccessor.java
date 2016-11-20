package ru.prgmt.warehouse.util;

import java.lang.reflect.Field;

/**
 * <p>Вспомогательный класс для получения значения поля из объекта.</p>
 * <p>Используется в {@link ru.prgmt.warehouse.database.sql.SqlDatabase}.</p>
 */
public abstract class BeanAccessor {
    /**
     * Получить занчение указанного поля
     * @param obj Объект
     * @param fieldName Название поля
     * @return Значение поля
     */
    public static Object get(Object obj, String fieldName) {
        Class<?> current = obj.getClass();

        while (current != Object.class) {
            try {
                Field field = current.getDeclaredField(fieldName);
                field.setAccessible(true);

                return field.get(obj);

            } catch (IllegalAccessException e) {
                throw new RuntimeException("Не удалось получить значение поля " + obj.getClass().getName() + '.' + fieldName, e);

            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }

        throw new RuntimeException("Не удалось найти поле " + obj.getClass().getName() + '.' + fieldName);
    }
}
