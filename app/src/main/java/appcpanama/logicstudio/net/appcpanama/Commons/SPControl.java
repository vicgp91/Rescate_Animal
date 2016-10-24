package appcpanama.logicstudio.net.appcpanama.Commons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Luis González on 21/10/16.
 */

public class SPControl {

    private Context context;
    private SharedPreferences sharedPreferences;
    private String SharedStringKey = "sharedLuisKey";

    public SPControl(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SharedStringKey, Activity.MODE_PRIVATE);

    }

    public void setStringValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public void setIntValue(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public void setFloatValue(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();

    }

    public int getIntValue(String key)
    {
        return sharedPreferences.getInt(key, 0);
    }


    public Float getFloatValue(String key)
    {
        return sharedPreferences.getFloat(key,0);
    }

    public String getStringValue(String key)
    {
        return sharedPreferences.getString(key,"");
    }


    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }


}
