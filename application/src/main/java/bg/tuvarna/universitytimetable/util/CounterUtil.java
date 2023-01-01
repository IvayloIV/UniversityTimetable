package bg.tuvarna.universitytimetable.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
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
