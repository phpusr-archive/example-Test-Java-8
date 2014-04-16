import java.util.ArrayList;
import java.util.List;

/**
 * @author phpusr
 *         Date: 13.04.14
 *         Time: 21:49
 */

/**
 * Мои эксперименты
 */
public class MyExperiments {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>() {{
            add("One");
            add("Two");
        }};

        list.stream().map(p -> p).forEach(System.out::println);
    }


}
