package appcpanama.logicstudio.net.appcpanama.Commons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import java.math.BigInteger;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * Created by LogicStudio on 31/10/16.
 */

public class PhotoClass {

    private final int TAKE_PHOTO_CODE = 1;
    private String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/RescateAnimal/";
    private SecureRandom random = new SecureRandom();

    private void createDirectory(){
        File newdir = new File(dir);
        newdir.mkdirs();
    }

    public Uri takePicture(Activity activity){

        createDirectory();

        String file = dir+ randomString() +".jpg";
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        }
        catch (IOException e)
        {
            return null;
        }

        return Uri.fromFile(newfile);
    }

    private String randomString(){
        return new BigInteger(130, random).toString(32);
    }
}
