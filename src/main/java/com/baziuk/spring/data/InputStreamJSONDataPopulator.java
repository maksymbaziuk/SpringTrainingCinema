package com.baziuk.spring.data;

import java.io.*;

/**
 * Created by Maks on 11/12/16.
 */
public class InputStreamJSONDataPopulator<T> extends JSONDataPopulator<T> {

    private InputStream inputStream;

    @Override
    protected Reader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
