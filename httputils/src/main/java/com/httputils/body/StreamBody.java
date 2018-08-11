package com.httputils.body;

import java.io.InputStream;

/**
 * Created by Administrator on 2018/1/25.
 */

public class StreamBody {
    private final String name;
    private final String mediaType;
    private final InputStream inputStream;
    private final long contentLength;

    public StreamBody(String name, InputStream inputStream, long contentLength, String mediaType) {
        this.name = name;
        this.inputStream = inputStream;
        this.contentLength = contentLength;
        this.mediaType = mediaType;
    }

    public InputStream inputStream() {
        return inputStream;
    }


    public String name() {
        return name;
    }

    public String mediaType() {
        return mediaType;
    }

    public long contentLength() {
        return contentLength;
    }
}
