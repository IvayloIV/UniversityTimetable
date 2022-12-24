package bg.tuvarna.universitytimetable.util;

import org.springframework.stereotype.Component;

@Component
public class CounterUtil {

    private int num;

    public int getAndDecrement() {
        int temp = num;
        --num;
        return temp;
    }

    public int setNum(int num) {
        this.num = num;
        return num;
    }
}
