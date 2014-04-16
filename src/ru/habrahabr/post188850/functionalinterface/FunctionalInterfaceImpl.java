package ru.habrahabr.post188850.functionalinterface;

/**
 * @author phpusr
 *         Date: 16.04.14
 *         Time: 11:00
 */

import java.lang.*;

/**
 * Реализация функциональнго интерфейса
 *
 * Демонстрация работы Lambda
 */
public class FunctionalInterfaceImpl implements FunctionalInterface/*, FunctionalInterface2*/ {

    /**
     * Вызов default-метода
     */
    public static void main(String[] args) {
        FunctionalInterfaceImpl fi = new FunctionalInterfaceImpl();
        //fi.hello();

        // Вызываем метод hz
        // С помощью лямбды переопределяем метод testPrint()
        // В интерфейсе должен быть только 1 метод, который нужно переопределить
        // Если нет параметров в переопределяемом методе, то: fi.hz(() -> {}
        // Если 1 параметр, то: fi.hz(a -> {}
        // Если 1 и более параметр, то: fi.hz((a, b) -> {}
        fi.hz((a, b) -> System.out.println("hz: " + a + b) );
    }

    /** Метод который будет переопределен */
    @Override
    public void testPrint(int a, int b) {
        System.out.println("Standard method must be override");
    }

    /**
     * Функция будет запущена с переопределенным параметром interf
     */
    void hz(FunctionalInterface interf) {
        interf.testPrint(1, 2);
    }

}
