package com.httputils.body;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/1/25.
 */

public class FileStreamBody extends StreamBody {

    private final File file;

    public FileStreamBody(File file, String mediaType) throws FileNotFoundException {
        this(file.getName(), new FileInputStream(file), file.length(), mediaType, file);
    }

    public FileStreamBody(String name, InputStream inputStream, long contentLength, String mediaType
            , File file) {
        super(name, inputStream, contentLength, mediaType);
        this.file = file;
    }

    public File file() {
        return file;
    }
}
