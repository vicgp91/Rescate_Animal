package org.rescateanimal.com.rescateanimal.Commons;

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

    public <E> E getValue(pointerPreference pointer, String key) {

        E evaule = null;
        switch (pointer){

            case GET_INT:
                evaule = intValue(key);
                break;

            case GET_FLOAT:
                evaule = floatValue(key);

                break;

            case GET_STRING:
                evaule = stringValue(key);

                break;
        }
        return evaule;
    }


    public void setValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public void setValue(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public void setValue(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();

    }



    private <E> E intValue(String key)
    {
        return (E) new Integer(sharedPreferences.getInt(key, 0));
    }


    private <E> E floatValue(String key)
    {
        return (E) new Float (sharedPreferences.getFloat(key,0));
    }

    private <E> E stringValue(String key)
    {
        return (E) new String (sharedPreferences.getString(key,""));
    }


    public Boolean contains(String key)
    {
        return sharedPreferences.contains("key");
    }

    public enum pointerPreference {
        GET_INT,
        GET_STRING,
        GET_FLOAT;
    }
}
