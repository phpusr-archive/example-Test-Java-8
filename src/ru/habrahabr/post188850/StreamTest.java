package ru.habrahabr.post188850;

/**
 * @author phpusr
 *         Date: 16.04.14
 *         Time: 10:47
 */

import java.util.Arrays;
import java.util.List;

/**
 * Работа со Stream
 *
 * http://habrahabr.ru/post/188850/
 */
public class StreamTest {

    public static void main(String[] args) {
        String[] strings = {"0", "1", "2"};
        List<String> list = Arrays.asList(strings);

        double result = list.stream().mapToDouble(Double::parseDouble).sum();
        System.out.println(result);
    }

}
