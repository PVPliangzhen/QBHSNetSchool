package com.httputils;


import com.httputils.body.FileStreamBody;
import com.httputils.body.PostBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpContent {

    private static final List EMPTY_EXTRA = Collections.emptyList();
    private static final int NODE_LIST_SIZE = 5;


    public static final String F_MULTI = "multipart";
    /**
     * 指明请求参数打包方式
     *数据格式：empty-params|only-params|include-stream/json|xml
     */
    public static final String F_PACKET = "data-packet";
    /**
     * 数据加密方式
     * 数据格式：AES/0FB451072D3FB25E
     */
    public static final String F_ENCRYPT = "data-encrypt";
    /**
     * 数字签名
     * 数据格式：MD5/DSEW224RR|R34FDF3F
     */
    public static final String F_SIGNATURE = "signature";
    /**
     * 请求体格式
     * 数据格式：{*}|*:{*}
     */
    public static final String F_BODY="request-body";


    private final String httpUrl;

    private final int connectTimeout;
    private final int readTimeout;
    private final int writeTimeout;


    private List<PostBody> extraValues = EMPTY_EXTRA;

    private static final class NodeList {
        PostBody next;
        private int mLen;

        public NodeList(PostBody nex) {
            this.next = nex;
            mLen++;
        }

        public void add(PostBody item) {
            if (next == null) {
                this.next = item;
                return;
            }
            item.next = next;
            next = item;
            mLen++;
        }


        public PostBody get(String name) {
            for (PostBody nv = next; nv != null; nv = nv.next) {
                if (nv.name == name || (name != null && name.equals(nv.name))) {
                    return nv;
                }
            }
            return null;
        }

        public boolean remove(PostBody value) {
            if (mLen > 0) {
                PostBody pre = null;
                for (PostBody nv = next; nv != null; nv = nv.next) {
                    if (nv == value) {
                        if (pre == null) {
                            next = nv.next;
                        } else {
                            pre.next = nv.next;
                        }
                        mLen--;
                        break;
                    }
                    pre = nv;
                }
            }
            return false;
        }

    }

    private final NodeList[] getValueList = new NodeList[NODE_LIST_SIZE];

    private int mExtra;
    private int mPost;


    public static final class Builder {
        private String httpUrl;
        private int connectTimeout = 0;
        private int readTimeout = 0;
        private int writeTimeout = 0;

        public Builder(String url) {
            this.httpUrl = url;
        }

        public Builder setConnectTimeout(TimeUnit unit, long timeout) {
            this.connectTimeout = toTimeMillis(timeout, unit);
            return this;
        }

        public Builder setWriteTimeout(TimeUnit unit, long timeout) {
            this.writeTimeout = toTimeMillis(timeout, unit);
            return this;
        }

        public Builder setReadTimeOut(TimeUnit unit, long timeout) {
            this.readTimeout = toTimeMillis(timeout, unit);
            return this;
        }

        public HttpContent build(){
            return new HttpContent(this);
        }

    }


    private HttpContent(Builder builder) {
        this.httpUrl = builder.httpUrl;
        this.connectTimeout = builder.connectTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.readTimeout = builder.readTimeout;

    }

    public HttpContent(HttpContent original) {
        this.httpUrl = original.url();
        this.connectTimeout = original.connectTimeOut();
        this.writeTimeout = original.writeTimeOut();
        this.readTimeout = original.readTimeOut();
        this.extraValues = original.copyExtras();
        this.mExtra = original.extraCount();
        this.mPost = original.postSize();
    }


    public int writeTimeOut() {
        return writeTimeout;
    }

    public int readTimeOut() {
        return readTimeout;
    }

    public int connectTimeOut() {
        return connectTimeout;
    }


    public String url() {
        return httpUrl;
    }

    public void addHeader(String key, String content) {
        addPostBody(key, content, PostBody.HEADER, false);
    }

    public void addParameter(String key, Object value) {
        addPostBody(key, value, PostBody.PARAM, true);
    }

    public void addFeature(String key, Object value) {
        addPostBody(key, value, PostBody.FEATURE, false);
    }

    public void setTag(Object value) {
        this.addTag(null, value);
    }

    public void addTag(String tag, Object value) {
        addPostBody(tag, value, PostBody.EXTRA, false);
    }


    private void addPostBody(String k, Object v, int t, boolean post) {
        if ((post && postSize() > 0) || (!post && extraCount() > 0)) {
            PostBody body = getPostBody(getValueList, k, t);
            if (null != body) {
                setTo(body, v);
                return;
            }
        }
        addPostBody(new PostBody(k, v, t));
        mExtra++;
        if (post) mPost++;
    }

    public void addFile(String key, File file, String mediaType) {
        if (postSize() > 0) {
            PostBody item = getPostBody(getValueList, key, PostBody.STREAM);
            if (null != item) {
                try {
                    setTo(item, new FileStreamBody(file, mediaType));
                } catch (FileNotFoundException e) {
                    removeItemBody(item);
                    mPost--;
                    mExtra--;
                }
                return;
            }
        }
        try {
            addPostBody(new PostBody(key, new FileStreamBody(file, mediaType), PostBody.STREAM));
        } catch (FileNotFoundException e) {
            return;
        }
        mExtra++;
        mPost++;
    }


    public final PostBody[] extras() {
        return extraValues.toArray(new PostBody[mExtra]);
    }

    List<PostBody> copyExtras() {
        if (extraValues == EMPTY_EXTRA) {
            return EMPTY_EXTRA;
        }
        return Collections.unmodifiableList(extraValues);
    }


    public final int extraCount() {
        return mExtra;
    }

    public final int postSize() {
        return mPost;
    }


    private void addPostBody(PostBody value) {
        if (value != null) {
            if (extraValues == EMPTY_EXTRA) {
                extraValues = new ArrayList<>(2);
                extraValues.add(value);
            } else {
                addPostBody(extraValues, value);
            }
            int index = indexTo(value.type);
            NodeList nl = getValueList[index];
            if (nl == null)
                getValueList[index] = new NodeList(value);
            else nl.add(value);

        }

    }

    private void removeItemBody(PostBody body) {
        if (body != null && extraCount() > 0) {
            extraValues.remove(body);
            int index = indexTo(body.type);
            NodeList nl = getValueList[index];
            if (null != nl)
                nl.remove(body);

        }
    }


    private static void addPostBody(List<PostBody> extras, PostBody body) {
        if (null != body && extras != EMPTY_EXTRA) {
            extras.add(body);
        }
    }

    private static PostBody getPostBody(final NodeList[] bucks, String name, int type) {
        int index = (type - 1) >> 1;
        if (index < 0 || index > NODE_LIST_SIZE)
            return null;
        NodeList bck = bucks[index];
        if (bck == null)
            return null;
        return bck.get(name);
    }

    private static int toTimeMillis(long timeout, TimeUnit unit) {
        if (timeout < 0)
            throw new IllegalArgumentException("timeout < 0");
        if (unit == null)
            throw new NullPointerException("unit == null");
        long millis = unit.toMillis(timeout);
        if (millis > Integer.MAX_VALUE)
            throw new IllegalArgumentException("Timeout too large.");
        if (millis == 0 && timeout > 0)
            throw new IllegalArgumentException("Timeout too small.");
        return (int) millis;
    }

    private static void setTo(PostBody body, Object v) {
        if (null != body) body.setValue(v);
    }

    private static int indexTo(int type) {
        return ((type - 1) >> 1);
    }


}
