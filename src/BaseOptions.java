import java.util.HashMap;
import java.util.Map;

public enum BaseOptions {
    OPTION_1(1),
    OPTION_2(2),
    OPTION_3(3);

    private final int value;
    private static final Map<Integer, BaseOptions> map = new HashMap<>();

    BaseOptions(int value) {
        this.value = value;
    }

    static {
        for (BaseOptions choiceType : BaseOptions.values()) {
            map.put(choiceType.value, choiceType);
        }
    }

    public static BaseOptions valueOf(int choiceType) {
        return map.get(choiceType);
    }

    public int getValue() {
        return value;
    }
}