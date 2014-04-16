package stream;

/**
 * @author phpusr
 *         Date: 13.04.14
 *         Time: 22:10
 */

/**
 * Человек
 */
public class People {
    private String name;
    private int age;
    private String group;

    public People(String name, int age, String group) {
        this.name = name;
        this.age = age;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGroup() {
        return group;
    }
}
