package stream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

/**
 * @author phpusr
 *         Date: 13.04.14
 *         Time: 22:11
 */

/**
 * Демонстрация работы со Stream
 *
 * https://www.youtube.com/watch?v=Zq--4Vh5RLI
 *
 * Характеристики Stream
 * ordered
 * distinct
 * sorted
 * sized
 * notnull
 * immutable
 * concurrent
 * subsized
 */
public class StreamPipeline {

    public static void main(String[] args) throws FileNotFoundException {
        //printGroups();

        //createStream();

        //intermediateOperations();

        //terminalOperations();

        //collectors();

        parallelism();
    }

    /**
     * Демонстрация возможностей Stream
     */
    public static void printGroups() {
        List<People> peoples = new ArrayList<People>() {{
            add(new People("Ivan", 66, "Rammstein"));
            add(new People("Vasiliy", 81, "Hand Up"));
            add(new People("Masha", 73, "Hand Up"));
            add(new People("Anastacia", 64, "USB"));
        }};

        peoples.stream()
                // Intermediate operations: Stream -> Stream
                .filter(p -> p.getAge() > 65)
                .map(People::getGroup)
                .distinct()
                .sorted(comparing(String::length))
                // Terminal operation: Stream -> Profit!
                // Intermediate операции не выполняются, если нет Terminal операции
                .forEach(System.out::println);
    }

    /**
     * Создание Stream
     */
    static void createStream() throws FileNotFoundException {
        // Создание Stream из массива
        String[] arr = new String[]{"one", "two", "tree"};
        Stream<String> s0 = Arrays.stream(arr);
        s0.forEach(System.out::println);

        // Создание Stream из значений
        Stream<Integer> s = Stream.of(0, 1, 2);
        s.forEach(System.out::println);

        Stream<Object> s2 = Stream.builder().add(4).add(5).build();
        s2.forEach(System.out::println);

        // Создание бесконечного Stream, используя фун-ю для генерации
        IntStream s3 = IntStream.range(0, 50);
        s3.forEach(System.out::print);

        // Создание бесконечного Stream итерациями
        AtomicInteger init = new AtomicInteger(0);
        Stream<Integer> s4 = Stream.generate(init::incrementAndGet);
//        s4.forEach(System.out::println);

        Stream<Integer> s5 = Stream.iterate(0, i -> i + 1);
//        s5.forEach(System.out::println);

        // BufferedReader
        Stream<String> lines = new BufferedReader(new FileReader("src/stream/People.java")).lines();
        lines.map(str -> "# " + str).forEach(System.out::println);

        // Бесконечный поток случайных double
        DoubleStream doubles = new SplittableRandom().doubles();
        double[] array = doubles.limit(10).toArray();
        for(int i=0;i<10;i++) System.out.println(array[i]);
    }

    static IntStream getIntStream() {
        System.out.println("---------------");
        return IntStream.range(0, 50);
    }

    static Stream<String> getStringStream() {
        System.out.println("---------------");
        String[] arr = new String[]{"one", "two", "three", "two", "four", "five"};
        return Arrays.stream(arr);
    }

    /**
     * Промежуточные операции
     */
    static void intermediateOperations() {
        // Фильтрация содержимого Стрима
        getIntStream().filter(e -> e % 10 == 0).forEach(System.out::println);

        // Отображение Стрима в другой стрим
        getIntStream().map(e -> e + 5).forEach(System.out::println);

        // Склеивание подсписков в один список
        getStringStream().flatMap(el -> Arrays.stream(el.split(""))).forEach(System.out::println);

        // Для выполнения каких либо действий над потоком (как правило нужен для дебага)
        //getIntStream().peek()

        // Сортировка элементов Стрима
        getStringStream().sorted().forEach(System.out::println);

        // Удаление повторяющихся элементов
        getStringStream().distinct().forEach(System.out::println);

        // Возвращает НЕ упорядоченный поток
        getStringStream().unordered().forEach(System.out::println);

        // Возвращает часть Стрима
        getStringStream().limit(3).forEach(System.out::println);

        // Переводит Стрим в режим параллельной обработки
        getIntStream().parallel().forEach(System.out::println);

        // Переводит Стрим в режим последовательной обработки
        getIntStream().sequential().forEach(System.out::println);
    }

    /**
     * Терминальные операции <br/>
     *
     * дают результат <br/>
     * параллельно или последовательно (в зависимотси от того, что уже есть в наборе операций) <br/>
     */
    static void terminalOperations() {
        // Итераторы
        //  Делает действие над каждым элементов
        getStringStream().forEach(System.out::println);
        //  Можно вытаскивать элементы из потока по очереди (больше необходимо для совместимости)
        //   Единственная ленивая терминальная операция
        Iterator<Integer> iterator = getIntStream().iterator();
        while (iterator.hasNext())  System.out.println(iterator.next());

        // Возвращает первое значение Стрима (перед ним м.б. filter)
        Optional<String> first = getStringStream().findFirst();
        System.out.println(first.get());

        // Возвращает какое-то значение (не обазательно первое, может работать быстрее)
        Optional<String> any = getStringStream().findAny();
        System.out.println(any.get());

        // Находит первое четное в бесконечной послед-ти
        int v = Stream.iterate(1, i -> i + 1).filter(i -> i%2 == 0).findFirst().get();
        System.out.println("\n" + v);

        stremSumExperiments();
        System.out.println(getIntStream().sum());

        // Берут поток и дают некоторый скаляр
        //  На reduce() основаны методы: sum(), min(), max(), count()
        v = getIntStream().reduce(0, (x, y) -> x + y);
        System.out.println("\nreduce: " + v);
        OptionalInt opInt = getIntStream().reduce((x, y) -> x + y);
        System.out.println("\nreduce: " + opInt.getAsInt());
    }

    /** Сумма элементов стрима */
    private static int getSum(IntStream s) {
        int[] sum = new int[1];
        s.forEach(i -> sum[0]+=i);
        return sum[0];
    }

    /** Эксперименты с суммой Стримов */
    static void stremSumExperiments() {
        int sum = getSum(IntStream.range(0, 100).map(i -> 1));
        System.out.println(sum);

        // Из-за того, что нет синхронизации значение каждый раз меняется
        // Одно из решений: использовать AtomicInteger
        sum = getSum(IntStream.range(0, 100).map(i -> 1).parallel());
        System.out.println(sum);
    }

    /**
     * Collectors <br/>
     *
     * Конвертирование Стримов в коллекции <br/>
     */
    static void collectors() {
        IntStream range = IntStream.range(0, 10);
        // Конвертирует IntStream -> Stream<Integer>
        Stream<Integer> boxed = range.boxed();

        // IntStream -> List
        List<Integer> list = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        for (Integer el : list) {
            System.out.print(el + ", ");
        }
        System.out.println();

        // IntStream -> Array
        IntStream.range(0, 100).toArray();

        // Делиметр строк
        String[] a = new String[]{"a", "b", "c"};
        String collect = Arrays.stream(a).collect(Collectors.joining(", "));
        System.out.println(collect);
    }

    /**
     * многие источники хорошо бьются на части <br/>
     * многие операции хорошо параллелизуются <br/>
     * библиотека делает все работу за нас <br/>
     * "под капотом" используется ForkJoinPool <br/>
     * но: нужно эксплицитно просить библиотеку <br/>
     */
    static void parallelism() {
        int v = IntStream.range(0, 100).parallel().reduce(Math::max).getAsInt();
        System.out.println(v);
    }
}
