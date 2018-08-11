package com.qbhsnetschool.test;

public class TestClient {

    public static void main(String args []){
//        HttpExecutorFactory executorFactory= new NativeExecutorFactory();
//        HttpRequestTracker requestTracker=new HttpRequestTracker();
//        HttpUtils.setImpl(new OKHttp3Utils(new NativeRequestFactory(
//                new HttpRequestRecycler(executorFactory,requestTracker))
//                ,requestTracker,executorFactory));
//        HttpContent request;
//        request=new HttpContent.Builder("http://192.168.1.17:8888/app/tel_codes/18813155412")
//                .setConnectTimeout(TimeUnit.SECONDS, 3)
//                .setWriteTimeout(TimeUnit.SECONDS, 10)
//                .setReadTimeOut(TimeUnit.SECONDS, 10).build();
//        request.addFeature(HttpContent.F_PACKET,"only-params/json");
//        request.addFeature(HttpContent.F_BODY,"{*}");
//        HttpUtils.impl().request(request, new Callback() {
//            @Override
//            public void onResponse(HttpResponse response) {
//                System.out.println(response.code());
//                System.out.println(response.message());
//                try {
//                    System.out.println(((RealResponseBody)response.body()).string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        NorHttpContentBuilder requestBuilder = new NorHttpContentBuilder("http://192.168.1.17:8888/app/nopasswords");
//        final HttpContent request = requestBuilder
//                .setConnectTimeout(TimeUnit.SECONDS, 3)
//                .setReadTimeOut(TimeUnit.SECONDS, 10)
//                .setWriteTimeout(TimeUnit.SECONDS, 10)
//                .build();
//        requestBuilder.setParams(request, new PostParams(3).add("tel", "18515010456").add("password", "123456").add("tel_code", "532576"));
//        HttpUtils.impl().request(request, new Callback() {
//            @Override
//            public void onResponse(HttpResponse response) {
//                try {
//                    System.out.println(((RealResponseBody)response.body()).string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
