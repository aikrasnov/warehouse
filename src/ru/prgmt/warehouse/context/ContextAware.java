package ru.prgmt.warehouse.context;

/**
 * Интерфейс для компонентов системы, использующих в своей работе контекст исполнения
 */
public interface ContextAware {
    /**
     * Установить текущий контекст исполнения
     * @param context Контекст исполнения
     */
    void setContext(Context context);
}
