package net.kyrin.air.lib.utils.file.container;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class ConfigManager<T extends Config> {

    private Path configFile;
    private Gson gson;
    private T config;
    private Class clazz;


    public ConfigManager(Path configFile, Gson gson, Class clazz) {
        this.gson = gson;
        this.configFile = configFile;
        this.clazz = clazz;
        if (!Files.exists(configFile)) {
            try {
                Files.createFile(configFile);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile.toFile()), StandardCharsets.UTF_8));
                gson.toJson(getDefaults(), clazz, bufferedWriter);
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = read();
    }

    public T get(){
        return config;
    }

    public void set(T object){
        config = object;
        save(object);
    }

    private void save(T object) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile.toFile()), StandardCharsets.UTF_8));
            gson.toJson(object, clazz, bufferedWriter);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private T read(){
        try {
            JsonReader reader = new JsonReader(new FileReader(configFile.toFile()));
            return gson.fromJson(reader, clazz);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public void update(){
        this.config = read();
    }

    protected abstract T getDefaults();



}
