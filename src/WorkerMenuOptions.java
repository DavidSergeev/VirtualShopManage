import java.util.HashMap;
import java.util.Map;

public enum WorkerMenuOptions {
    PRINT_ALL_CUSTOMERS(1),
    PRINT_ALL_CLUB_MEMBERS(2),
    PRINT_ALL_BUYERS(3),
    PRINT_MAX_BUYER(4),
    ADD_PRODUCT(5),
    CHANGE_PRODUCT_STATUS(6),
    BUY_PRODUCT(7),
    EXIT(8);

    private final int value;
    private static final Map<Integer, WorkerMenuOptions> map = new HashMap<>();

    WorkerMenuOptions(int value) {
        this.value = value;
    }

    static {
        for (WorkerMenuOptions choiceType : WorkerMenuOptions.values()) {
            map.put(choiceType.value, choiceType);
        }
    }

    public static WorkerMenuOptions valueOf(int choiceType) {
        return map.get(choiceType);
    }

    public int getValue() {
        return value;
    }
}