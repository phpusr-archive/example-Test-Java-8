package ru.habrahabr.post188850.functionalinterface;

/**
 * @author phpusr
 *         Date: 16.04.14
 *         Time: 10:57
 */

/**
 * Демонстрация функционаьного интерфейса
 *
 * Это интерфейс с методами, у которых обазательно должна быть реализация, их не обязательно переопределять
 *
 * Они открывают доступ к множественному наследованию
 */
public interface FunctionalInterface {

    default void hello() {
        System.out.println("Hello");
    }

    void testPrint(int a, int b);

}
