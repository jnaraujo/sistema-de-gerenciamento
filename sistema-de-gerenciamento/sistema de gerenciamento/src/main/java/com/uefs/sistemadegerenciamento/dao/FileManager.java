package com.uefs.sistemadegerenciamento.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class FileManager<K, T> {
    private String FILE_NAME;

    public FileManager(String fileName) {
        FILE_NAME = fileName;

        boolean doesFileExist = new java.io.File(FILE_NAME).exists();
        if (!doesFileExist) {
            save(new HashMap<K, T>());
        }
    }

    public void save(HashMap<K, T> objects) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            objectOutputStream.write(new byte[0]); // clear file

            objectOutputStream.writeObject(objects);

            objectOutputStream.close();
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<K, T> load() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_NAME));
            HashMap objects = (HashMap<K, T>) objectInputStream.readObject();

            objectInputStream.close();

            return objects;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
