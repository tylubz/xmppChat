package net.tylubz.chat;

import android.app.Application;
import android.content.Context;

/**
 * Created by Sergei on 07.11.2017.
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
