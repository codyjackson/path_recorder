package com.cragtographer.app.models;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GlobalApplicationModel {
    public static List<Area> Areas;

    private static File GetSaveFile(Context context) {
        return new File(context.getFilesDir(), "data.txt");
    }

    public static void Load(Context context) {
        try {
            File file = GetSaveFile(context);
            if(!file.exists()) {
                file.createNewFile();
            }

            FileInputStream is = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(is);
                    Areas = (List<Area>)ois.readObject();
                ois.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Areas = new ArrayList<Area>();
        }
    }

    public static void Save(Context context) {
        try {
            File file = GetSaveFile(context);
            if(!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream os = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(Areas);
                oos.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Area GetArea(int index) {
        return Areas.get(index);
    }

    public static Section GetSection(int areaIndex, int sectionIndex) {
        return Areas.get(areaIndex).Sections.get(sectionIndex);
    }
}
