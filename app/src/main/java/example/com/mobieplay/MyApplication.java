package example.com.mobieplay;

import android.app.Application;
import android.content.Context;

/**
 * Created by 14819 on 2018/3/4.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;
    /**
     * 获取context
     * @return
     */
    public static Context getInstance() {
        if (mInstance == null) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }
}
