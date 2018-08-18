package com.httputils.handle.okhttp3;

import android.os.Looper;
import android.support.v4.util.ArrayMap;


import com.httputils.Callback;
import com.httputils.HttpContent;
import com.httputils.HttpRequest;
import com.httputils.HttpResponse;
import com.httputils.body.HttpPost;
import com.httputils.body.PostBody;
import com.httputils.body.StreamBody;
import com.httputils.execption.WriteTimeOutException;
import com.httputils.handle.HttpExecutor;
import com.httputils.handle.ResponseGetter;
import com.httputils.utils.Encryption;
import com.httputils.utils.Signature;

import org.json.JSONException;
import org.json.JSONStringer;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpRetryException;
import java.net.ProtocolException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by Administrator on 2018/1/24.
 */

public class OkHttp3Executor implements HttpExecutor {

    private static final String EMPTY_TEXT = "";

    private static final String EMPTY_PARAMS = "empty-params";
    private static final String ONLY_PARAMS = "only-params";
    private static final String INCLUDE_STREAM = "include-stream";


    private static final String KEY_PACKET_PARAMS = "packets";
    private static final String KEY_SIGNATURE = "sign";


    HttpClientFactory clientFactory =
            new HttpClientFactory(new DefaultClientProxy());
    private final ResponseAdapter dCallbackAdapter = new ResponseAdapter();

    private final ArrayMap<HttpRequest, Call> runningCalls = new ArrayMap<>();
    private final ArrayMap<Call, HttpPost> runningRequests = new ArrayMap<>();


    private static final class DefaultClientProxy implements OkHttp3ClientProxy {


        @Override
        public OkHttpClient newClient(int writeTimeOut
                , int readTimeOut, int connectTimeOut
                , OkHttpClient oldClient) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (oldClient != null) {
                builder.dispatcher(oldClient.dispatcher())
                        .connectionPool(oldClient.connectionPool())
                        .authenticator(oldClient.authenticator())
                        .cache(oldClient.cache())
                        .connectionSpecs(oldClient.connectionSpecs())
                        .certificatePinner(oldClient.certificatePinner())
                        .cookieJar(oldClient.cookieJar())
                        .dns(oldClient.dns())
                        .hostnameVerifier(oldClient.hostnameVerifier())
                        .protocols(oldClient.protocols())
                        .proxy(oldClient.proxy())
                        .proxySelector(oldClient.proxySelector())
                        .proxyAuthenticator(oldClient.proxyAuthenticator())
                        .socketFactory(oldClient.socketFactory());
            }
            builder.connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                    .readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
            return builder.build();
        }
    }


    static class HttpClientFactory {


        private Client client;


        private static class Client extends WeakReference<HttpContent> {

            OkHttpClient httpClient;

            public Client(HttpContent referent, OkHttpClient httpClient) {
                super(referent);
                this.httpClient = httpClient;
            }

            public OkHttpClient okHttpClient() {
                return httpClient;
            }
        }

        private OkHttp3ClientProxy clientProxy;

        public HttpClientFactory(OkHttp3ClientProxy proxy) {
            this.clientProxy = proxy;
        }

        public OkHttpClient client(HttpContent httpContent) {
            Client c = this.client;
            OkHttpClient hc = null;
            HttpContent old;
            if (c == null || (old = c.get()) == null || old != httpContent) {
                c = new Client(httpContent, hc = newClient(httpContent.writeTimeOut(), httpContent.readTimeOut()
                        , httpContent.connectTimeOut(), (c == null ? null : c.okHttpClient())));
                this.client = c;
            }
            if (hc == null)
                hc = c.okHttpClient();
            return hc;
        }

        OkHttpClient newClient(int writeTimeOut, int readTimeOut, int connectTimeOut, OkHttpClient oldClient) {
            if (null != clientProxy) {
                return clientProxy.newClient(writeTimeOut, readTimeOut, connectTimeOut, oldClient);
            }
            return new OkHttpClient.Builder().connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS).readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                    .build();
        }
    }

    final class ResponseAdapter implements okhttp3.Callback {

        @Override
        public void onFailure(Call call, IOException e) {
            System.out.println(e.getMessage());
            HttpPost ret = runningRequests.remove(call);
            if (null != ret) {
                ret.request.recycle();
                runningCalls.remove(ret.request);
                parseFailure(e, ret);
                if (null != ret.callback) {
                    ret.callback.onResponse(ret.response);
                }
            }


        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            System.out.println(response.toString());
            HttpPost ret = runningRequests.remove(call);
            if (null != ret) {
                ret.request.recycle();
                runningCalls.remove(ret.request);
                parseResponse(response, ret);
                if (null != ret.callback) {
                    ret.callback.onResponse(ret.response);
                }
            }
        }


    }

    @Override
    public void processRequest(HttpPost post, String httpMethod) {
        if (post.request.isRecycled()) {
            ResponseGetter.fill(post.response
                    , post.origContent, HttpResponse.RECYCLED, "HTTP Request Recycled", null);
            return;
        }
        int flags = post.flags;

        if ((flags & 0xF0) != HttpPost.OK_HTTP_3) {
            System.err.println("request type err");
            ResponseGetter.fill(post.response
                    , post.origContent, 10011, "Can't Process OKHttp3's Request", null);
            return;
        }
        if ((flags & HttpPost.REQ_ONE_WAY) == 0 && post.callback == null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                System.err.println("request on main thread");
                ResponseGetter.fill(post.response
                        , post.origContent, 10012, "HTTP Request Running On Main Thread", null);
                return;
            }
            flags = HttpPost.REQ_ONE_WAY | (flags & ~HttpPost.REQ_ONE_WAY);

        }
        HttpContent httpContent = new HttpContent(post.origContent);
        PostBody[] extras = httpContent.extras();
        if (httpContent.extraCount() > 0) {
            Arrays.sort(extras);
        }
        goRequestLocked(clientFactory.client(post.origContent), httpContent, post.response
                , post.callback, post, extras, flags, httpMethod);

    }

    @Override
    public void cancelRequest(HttpRequest request) {
        Call call = runningCalls.get(request);
        if (call != null)
            call.cancel();
    }


    private static MediaType checkMediaType(String type) {
        if (null != type && !type.trim().isEmpty()) {
            return MediaType.parse(type);
        }
        return null;
    }

    private void goRequestLocked(OkHttpClient httpClient
            , HttpContent httpContent, HttpResponse response
            , Callback callback, HttpPost post, PostBody[] extras, int flags, String httpMethod) {
        if (httpClient != null && null != httpContent
                && null != response) {
            /*解析请求链接*/
            HttpUrl url = HttpUrl.parse(httpContent.url());
            /*
            如果解析失败，那么直接将失败信息写入“response”并返回
            * */
            if (url == null) {
                return;
            }

            Request.Builder request = new Request.Builder().url(url);
            /*HTTP请求METHOD=GET*/
            if (httpContent.extraCount() == 0) {
                request.get();
            }

            final int extraCount = httpContent.extraCount();
            if (extraCount > 0) {
                /*网HTTP请求填充HEADERS*/
                int mod = 0;
                mod = setHeadersToRequest(request, extras, mod, extraCount);
                final int postSize = httpContent.postSize();
                if ((extraCount - mod - postSize) >= 0) {
                    final int featureStart = mod;
                    mod = countOfExtraType(extras, featureStart, extraCount, PostBody.FEATURE);
                    final int paramsStart = featureStart + mod;
                    if (postSize > 0 || mod > 0) {
                        /*检查请求“数据打包”特性*/
                        int packet = indexOfExtras(extras, featureStart, paramsStart, HttpContent.F_PACKET);//数据打包
                        MediaType packetType = (packet >= 0 ? checkMediaType((String) extras[packet].value) : null);
                        /*如果post参数不为空或者“packet”特性是空参请求，那么构建HTTP POST 参数*/
                        if (postSize > 0 || (packetType != null
                                && packetType.type().equals(EMPTY_PARAMS))) {
                            RequestBody requestBody;
                            int multipart = indexOfExtras(extras, featureStart, paramsStart, HttpContent.F_MULTI);//适用于文件上传
                            int bodyType = indexOfExtras(extras, featureStart, paramsStart, HttpContent.F_BODY);//请求体格式
                            mod = countOfExtraType(extras, paramsStart, extraCount, PostBody.PARAM);
                            final int streamStart = paramsStart + mod;
                            mod = countOfExtraType(extras, paramsStart, extraCount, PostBody.STREAM);
                            final int extraStart = streamStart + mod;

                            int encrypt = indexOfExtras(extras, featureStart, paramsStart, HttpContent.F_ENCRYPT);//数据加密方式
                            int signature = indexOfExtras(extras, featureStart, paramsStart, HttpContent.F_SIGNATURE);//数字签名

                            /*进一步检查media type*/
                            MediaType multiType = (multipart >= 0 ? checkMediaType((String) extras[multipart].value) : null);
                            if (multiType != null) {
                                MultipartBody.Builder multiRequestBody = new MultipartBody.Builder().setType(multiType);
                                if ((extraStart - streamStart) > 0) {
                                    for (int i = streamStart; i < extraStart; i++) {
                                        addStreamPart(extras[i], multiRequestBody);
                                    }
                                }
                                if (packet >= 0) {
                                    String params = packetParams(extras, paramsStart, extraStart, streamStart, packetType);
                                    if (encrypt >= 0) {
                                        params = encryptData(params, (String) extras[encrypt].value);
                                    }

                                    if (bodyType == -1 || "{*}".equals(extras[bodyType].value)) {
                                        multiRequestBody.addPart(MultipartBody.Part.create(
                                                RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), params)
                                        ));
                                    } else {
                                        multiRequestBody.addFormDataPart(KEY_PACKET_PARAMS, params);
                                        if (signature >= 0) {
                                            multiRequestBody.addFormDataPart(KEY_SIGNATURE, signMultiData(extras[signature].value, params
                                                    , extras, streamStart, extraStart));
                                        }
                                    }
                                }
                                requestBody = multiRequestBody.build();
                            } else {
                                if (packet >= 0) {
                                    String params = packetParams(extras, paramsStart, extraStart, streamStart, packetType);
                                    if (encrypt >= 0) {
                                        params = encryptData(params, (String) extras[encrypt].value);
                                    }
                                    System.out.println(params);
                                    if (bodyType == -1 || "{*}".equals(extras[bodyType].value)) {
                                        requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), params);
                                    } else {
                                        FormBody.Builder formRequestBody = new FormBody.Builder()
                                                .add(KEY_PACKET_PARAMS, params);
                                        if (signature >= 0) {
                                            formRequestBody.add(KEY_SIGNATURE, signData(extras[signature].value, params));
                                        }
                                        requestBody = formRequestBody.build();
                                    }
                                } else {
                                    String content;
                                    MediaType alEncrypt = null;
                                    FormBody.Builder formRequestBody = new FormBody.Builder();
                                    if (encrypt >= 0)
                                        alEncrypt = MediaType.parse((String) extras[encrypt].value);
                                    for (int i = paramsStart; i < streamStart; i++) {
                                        content = (String) extras[i].value;
                                        formRequestBody.add(extras[i].name, encrypt >= 0 ? encryptData(content, alEncrypt) : content);
                                    }
                                    requestBody = formRequestBody.build();

                                }

                            }
                            switch (httpMethod){
                                case "POST":
                                    request.post(requestBody);
                                    break;
                                case "PATCH":
                                    request.patch(requestBody);
                                    break;
                                case "PUT":
                                    request.put(requestBody);
                                    break;
                                case "DELETE":
                                    request.delete(requestBody);
                                    break;
                            }
                        } else
                            request.get();

                    }


                }

            }

            /*发起HTTP请求*/
            Call newCall = httpClient.newCall(request.build());
            runningCalls.put(post.request, newCall);
            if ((flags & HttpPost.REQ_ONE_WAY) == HttpPost.REQ_ONE_WAY) {
                try {
                    parseResponse(newCall.execute(), post);
                } catch (IOException e) {
                    parseFailure(e, post);
                }
                runningCalls.remove(newCall);
                post.request.recycle();

            } else {
                runningRequests.put(newCall, post);
                newCall.enqueue(dCallbackAdapter);
            }
        }
    }

    private static int setHeadersToRequest(Request.Builder request, PostBody[] extras, int start, int count) {
        int size = 0;
        if (start < count) {
            for (int i = start; i < count; i++) {
                if (extras[i].type != PostBody.HEADER) {
                    break;
                }
                request.addHeader(extras[i].name, (String) extras[i].value);
                size++;
            }
        }
        return size;
    }

    private static void addStreamPart(PostBody value, MultipartBody.Builder request) {
        if (null != value) {
            StreamBody body = (StreamBody) value.value;
            request.addFormDataPart(value.name, body.name(), createStreamBody(body));
        }
    }

    private static String packetParams(PostBody[] extras, int start, int end, int param, MediaType packetType) {
        if (packetType != null) {
            String[] typ = packetType.type().split("|");
            String pack = packetType.subtype();
            int flags = 0;
            for (int i = 0; i < typ.length; i++) {
                if (typ[i].equals(EMPTY_PARAMS)) {
                    flags = 4 | (flags & ~4);
                } else if (typ[i].equals(INCLUDE_STREAM)) {
                    flags = 2 | (flags & ~2);
                } else if (typ[i].equals(ONLY_PARAMS)) {
                    flags = 1 | (flags & ~1);
                }
            }
            int ec = ((2 & flags) == 2 ? end : param);
            if ((4 & flags) == 4 || (ec - start) > 0) {
                int nc = ec - start;
                if (pack.equals("json")) {
                    JSONStringer stringer = new JSONStringer();
                    try {
                        stringer.object();
                        if (nc > 0)
                            for (int i = start; i < ec; i++)
                                stringer.key(extras[i].name).value(extras[i].value);
                        stringer.endObject();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return stringer.toString();
//                    StringBuilder string = new StringBuilder();
//                    string.append("{");
//                    if (nc > 0) {
//                        int i = 0;
//                        if (nc > 1) {
//                            for (i = start; i < ec - 1; i++) {
//                                string.append(extras[i].name);
//                                string.append(":");
//                                string.append(extras[i].value);
//                                string.append(",");
//                            }
//                        }
//                        string.append(extras[i].name);
//                        string.append(":");
//                        string.append(extras[i].value);
//                    }
//                    string.append("}");
//                    return string.toString();
                }

            }

        }
        return EMPTY_TEXT;
    }

    private static String encryptData(String data, String algorithm) {
        if (data != null && algorithm != null) {
            MediaType encrypt = MediaType.parse(algorithm);
            if (null != encrypt) {
                return encryptData(data, encrypt);
            }
        }
        return data;

    }

    private static String encryptData(String data, MediaType encrypt) {
        if (null != data && null != encrypt) {
            if (encrypt.type().equalsIgnoreCase("AES")) {
                return Encryption.AES(data, encrypt.subtype());
            }
        }
        return data;
    }

    private static String signMultiData(Object algorithm, String packedParams,
                                        PostBody[] extras, int start, int end) {
        Object[] o = new Object[2 + end - start];
        o[0] = algorithm;
        o[1] = packedParams;
        int index = 2;
        StreamBody body;
        for (int i = start; i < end; i++) {
            body = (StreamBody) extras[i].value;
            o[index++] = body.name();
        }

        return signData(o);
    }

    private static String signData(Object... data) {
        if (data == null || data.length == 0) {
            return EMPTY_TEXT;
        }
        Object algorithm = data[0];
        int nl = data.length - 1;
        if (algorithm instanceof String) {
            MediaType type = MediaType.parse((String) algorithm);
            if (type.type().equals("MD5")) {
                String[] extra = null;
                if (type.subtype() != null) {
                    extra = type.subtype().split("|");
                    nl += extra.length;
                }
                Object[] nb = new Object[nl];
                if (extra != null) {
                    System.arraycopy(extra, 0, nb, 0, extra.length);
                }
                System.arraycopy(data, 1, nb, extra.length, data.length - 1);
                return Signature.MD5(data);
            }
        }
        return EMPTY_TEXT;
    }

    static final RequestBody createStreamBody(final StreamBody body) {
        return new RequestBody() {

            @Override
            public MediaType contentType() {
                return MediaType.parse(body.mediaType());
            }

            @Override
            public long contentLength() {
                return body.contentLength();
            }

            @Override
            public void writeTo(BufferedSink sink) throws WriteTimeOutException {
                Source source = null;
                try {
                    source = Okio.source(body.inputStream());
                    sink.writeAll(source);
                } catch (IOException e) {
                    throw new WriteTimeOutException("Write Time Out", e);
                } finally {
                    okhttp3.internal.Util.closeQuietly(source);
                }
            }
        };
    }

    private static void parseFailure(IOException e, HttpPost post) {
        if (e instanceof UnknownHostException) {
            ResponseGetter.fill(post.response, post.origContent, 10031
                    , "可能因网络问题，无法连接上主机"
                    , ResponseGetter.EMPTY_BODY);
            return;
        }
        int code = 10000;
        String message = EMPTY_TEXT;
        String exceptionMsg = e.getMessage();
        if (exceptionMsg == null)
            exceptionMsg = EMPTY_TEXT;
        switch (exceptionMsg) {
            case "Canceled":
                code = HttpResponse.CANCELED;
                message = "Request Cancelled";
                break;
            default:
                if (e instanceof ProtocolException) {
                    message = e.getMessage();
                } else if (e instanceof HttpRetryException) {
                    code = ((HttpRetryException) e).responseCode();
                    message = e.getMessage();
                } else if (e instanceof WriteTimeOutException) {
                    code = 10032;
                    message = e.getMessage();
                }
        }
        ResponseGetter.fill(post.response, post.origContent
                , code, message, ResponseGetter.EMPTY_BODY);
    }

    private static void parseResponse(Response result, HttpPost post) {
        System.out.println(result.request().headers().toString());
        ResponseGetter.fill(post.response, post.origContent, result.code(), result.message()
                , result.body());
    }

    private static int countOfExtraType(PostBody[] extras, int start, int count, int type) {
        int size = 0;
        if (start < count) {
            for (int i = start; i < count; i++) {
                if (extras[i].type != type) {
                    break;
                }
                size++;
            }
        }
        return size;
    }

    private static final int indexOfExtras(PostBody[] extras, int start, int end, String name) {
        if (end - start > 0) {
            for (int i = start; i < end; i++) {
                if (name.equals(extras[i].name)) {
                    return i;
                }
            }
        }
        return ~0;
    }

}
