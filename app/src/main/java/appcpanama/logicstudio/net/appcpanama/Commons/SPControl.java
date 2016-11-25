package appcpanama.logicstudio.net.appcpanama.Commons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import appcpanama.logicstudio.net.appcpanama.JavaBeans.CondicionAnimalBeans;
import appcpanama.logicstudio.net.appcpanama.JavaBeans.infoAnimalBeans;
import appcpanama.logicstudio.net.appcpanama.R;

/**
 * Created by Luis González on 21/10/16.
 */

public class SPControl {

    private Context context;
    private SharedPreferences sharedPreferences;
    private String SharedStringKey = "sharedLuisKey";
    List<infoAnimalBeans> list;
    List<CondicionAnimalBeans> listCondicion;

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
        return sharedPreferences.getFloat(key, 0);
    }

    public String getStringValue(String key)
    {
        return sharedPreferences.getString(key, "");
    }


    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public  List<infoAnimalBeans>  fakeData() {

        list = new ArrayList<>();

        list.add(new infoAnimalBeans("Perezoso de 2 dedos", R.drawable.peresozo2_dedos, R.drawable.peresoz2_dedos_menu, "Choloepus/Megalonychidae", "file:///android_asset/peresozo2dedos.html"));
        list.add(new infoAnimalBeans("Perezoso de 3 dedos" , R.drawable.peresozo3_dedos, R.drawable.peresozo3_dedos_menu, "Bradypus/Bradypodidae", "file:///android_asset/peresozo2dedos.html"));
        list.add(new infoAnimalBeans("Jaguar" , R.drawable.jaguar, R.drawable.jaguar_menu, "Panthera onca", "file:///android_asset/peresozo2dedos.html"));
        return list;

    }


    public  List<CondicionAnimalBeans>  fakeDataCondicion() {

        listCondicion = new ArrayList<>();

        listCondicion.add(new CondicionAnimalBeans("Intentan cruzar la calle"));
        listCondicion.add(new CondicionAnimalBeans("Atropellados"));
        listCondicion.add(new CondicionAnimalBeans("Perdidos" ));
        listCondicion.add(new CondicionAnimalBeans("Otros"));
        return listCondicion;

    }




    public void delete(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
