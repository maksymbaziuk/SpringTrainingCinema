package com.baziuk.spring.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Maks on 9/21/16.
 */
public class JSONDataPopulator<T> {

    private File dataFile;

    public List<T> getData(Class<T[]> clazz) throws FileNotFoundException{
        Gson gson = getGsonInstance();
        T[] data = gson.fromJson(new BufferedReader(new FileReader(dataFile)), clazz);
        return Arrays.asList(data);
    }

    protected Gson getGsonInstance(){
        return constructBuilder().create();
    }

    protected GsonBuilder constructBuilder(){
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer <LocalDateTime>) (json, type, jsonDeserializationContext) ->
                        LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()));
    }

    public File getDataFile() {
        return dataFile;
    }

    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }
}