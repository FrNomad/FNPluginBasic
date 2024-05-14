package com.frnomad.pluginbasic.store;

import com.frnomad.pluginbasic.utils.Store;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

public abstract class StoreConfig {

    private StoreTasker tasker;
    private final String path;
    private final String storeKey;

    public StoreConfig(String path, String key, Object scase) {
        this.path = path;
        this.storeKey = key;
        Store.set(key, scase);
    }

    public boolean loadFile() {
        if(!this.makeFile()) return false;
        Charset cs = StandardCharsets.UTF_8;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String data;

        try {
            data = String.join("", Files.readAllLines(Paths.get(tasker.getPlugin().getDataFolder() + "/" + this.path), cs));
        } catch (IOException e) {
            tasker.getPlugin().log(Level.SEVERE, "File named " + this.path + " doesn't exist. Please restart the server.");
            return false;
        }

        Store.set(storeKey, gson.fromJson(data, Store.get(storeKey).getClass()));
        return true;
    }

    public boolean saveFile() {
        if(!this.makeFile()) return false;
        Charset cs = StandardCharsets.UTF_8;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String data;
        File dataFile = new File(tasker.getPlugin().getDataFolder() + "/" + this.path);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));
            data = gson.toJson(Store.get(storeKey));
            tasker.getPlugin().log(Level.INFO, data);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            tasker.getPlugin().log(Level.SEVERE, "File named " + this.path + " doesn't exist. Please restart the server.");
            return false;
        }

        return true;
    }

    public void setTasker(StoreTasker tasker) {
        this.tasker = tasker;
    }

    private boolean makeFile() {
        File f = new File(tasker.getPlugin().getDataFolder() + "/" + this.path);
        if(!f.exists()) {
            try {
                f.getParentFile().mkdirs();
                f.createNewFile();
                tasker.getPlugin().log(Level.INFO, "File named " + this.path + " successfully created.");
            } catch (Exception e) {
                e.printStackTrace();
                tasker.getPlugin().log(Level.SEVERE, "Error occurred while make new file.");
                return false;
            }
        }
        return true;
    }

}
