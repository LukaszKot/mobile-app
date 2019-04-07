package com.example.k7.koncowy.projekt.projektkoncowy.repositories;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FileRepository {
    private static final FileRepository ourInstance = new FileRepository();

    public static FileRepository getInstance() {
        return ourInstance;
    }

    private File mainDirectory;

    private FileRepository() {
        File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!pic.exists())
            pic.mkdir();
        mainDirectory = createMainDirectories(pic);
    }

    private File createMainDirectories(File pic) {

        File mainDir  = createFolder(pic, "kot");
        String[] subfolders = new String[]{"miejsca", "ludzie", "rzeczy"};
        for (String folder : subfolders)
            createFolder(mainDir, folder);
        return mainDir;
    }

    public File createFolder(String folder)
    {
        return createFolder(mainDirectory,folder);
    }


    public File createFolder(File dir, String folder) {
        File file = new File(dir, folder);
        if (!file.exists())
            file.mkdir();
        return file;
    }

    public String[] getFolders()
    {
        File[] files = mainDirectory.listFiles();
        String[] strFiles = new String[files.length];
        for (int i=0; i<files.length; i++)
        {
            strFiles[i] = files[i].getName();
        }
        return strFiles;
    }

    public void removeFolder(int i)
    {
        String[] strFolders = getFolders();
        String strFolder = strFolders[i];
        File folder = new File(mainDirectory, strFolder);
        if(folder.exists())
            folder.delete();
    }

    public void saveFile(String folder, byte[] byteArray)
    {
        FileOutputStream fs = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String d = df.format(new Date());
        try
        {
            fs = new FileOutputStream(mainDirectory.toString()+"/"+folder+"/"+d+".jpg");
            fs.write(byteArray);
            fs.close();
        }catch (FileNotFoundException e){}
        catch (IOException e){}
    }

    public ArrayList<File> getFiles(String folderName)
    {
        File file = new File(mainDirectory, folderName);
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(file.listFiles()));
        return files;
    }
}
