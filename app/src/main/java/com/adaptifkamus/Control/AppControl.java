package com.adaptifkamus.Control;

import android.content.Context;
import android.database.Cursor;

import com.adaptifkamus.Model.DBAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hilmy on 19/06/2016.
 */
public class AppControl {

    Context baseContext;

    public AppControl(Context baseContext) {
        this.baseContext = baseContext;
    }

    public void inisiasiDB() {
        String dirTujuan = "/data/data/"+baseContext.getPackageName()+"/databases/";
        String pathTujuan = dirTujuan + "AdaptifKamus";
        File f = new File(pathTujuan);
        if (!f.exists()) {
            File directory = new File(dirTujuan);
            directory.mkdirs();
            try {
                CopyDB(baseContext.getAssets().open("AdaptifKamus.sql"),
                        new FileOutputStream(pathTujuan));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void CopyDB (InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        inputStream.close();
        outputStream.close();
    }

    public List<String> InisiasiMember(DBAdapter db) {
        List<String> listMember = new ArrayList<String>();
        db.open();
        Cursor cLisMember = db.AmbilAllMember();
        short i = 0;
        while (cLisMember.moveToNext()) {
            listMember.add(i, cLisMember.getString(0));
            System.out.println(listMember.get(i) + " ditambahkan");
            i++;
        }
        db.close();

        return listMember;
    }
}