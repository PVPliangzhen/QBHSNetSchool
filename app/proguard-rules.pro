# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



-keep class bitter.jnibridge.* { *; }
-keep class com.unity3d.player.* { *; }
-keep class org.fmod.* { *; }
-keep class com.qjtc.magicalar.unity.common.* { *; }
-keep class com.qjtc.magicalar.unity.aractivity.* { *; }
-keep class com.unity3d.unitygar.* {*;}
-keep class com.unity3d.plugin.* {*;}
-keep class com.unity3d.player.* {*;}
-keep class com.yusufolokoba.natcorder.* {*;}
-keep class com.yusufolokoba.natcam.rendering.* {*;}
-keep class com.EasyMovieTexture.* {*;}
-keep class com.github.hiteshsondhi88.libffmpeg.** {*;}
-keep class com.vincent.videocompressor.** {*;}
-keep class com.flurgle.blurkit.** {*;}
-keep class com.tangyx.video.** {*;}
-keep class com.huawei.** {*;}
-keep class com.oppo.** {*;}
-keep class com.google.** {*;}
-keep class com.facebook.** {*;}
-ignorewarnings

-keep class **.R$* {*;}


#默认保留
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.view.View
-keep class android.support.** {*;}


# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# 保留Parcelable序列化的类不被混淆
-keepclassmembers class * implements android.os.Parcelable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

-keep class com.qjtc.magicalar.mvp.home.childs.FullLayoutManager{
   !private *;
}


#Gson 相关的混淆配置
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Bugly
-dontwarn com.tencent.bugly.**
-keep class com.tencent.bugly.** {*;}


# Glide
-keep public class * implements com.bumptech.glide.module.**{*;}
-keep public class * implements com.bumptech.glide.load.engine.**{*;}
-keep class com.bumptech.glide.module.**{*;}
-keep class com.bumptech.glide.load.engine.**{*;}
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
 **[] $VALUES;
 public *;
}

# Fresco
-keep class com.facebook.fresco.** {*;}
-keep interface com.facebook.fresco.** {*;}
-keep enum com.facebook.fresco.** {*;}
-keep class com.facebook.imagepipeline.nativecode.**{
    native <methods>;
}

# ButterKnife
-keep public class * implements butterknife.Unbinder {
    public <init>(**, android.view.View);
}
-keep class butterknife.*
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

# Retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**
-dontwarn javax.annotation.**


# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# okhttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn com.squareup.okhttp.**
#okio
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}

#mob(第三方分享组件)
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-keep class m.framework.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**
-dontwarn **.R$*

-keep class com.google.android.exoplayer2.**{ *; }
-keep class com.qjtc.viewmodule.ViewPager.DirectionalViewPager{*;}
-keep class com.qjtc.viewmodule.ViewPager.DirectionalViewPager$**{*;}

#手动启用support keep注解
#http://tools.android.com/tech-docs/support-annotations
-dontskipnonpubliclibraryclassmembers
-printconfiguration
-keep,allowobfuscation @interface android.support.annotation.Keep

-keep @android.support.annotation.Keep class *
-keepclassmembers class * {
    @android.support.annotation.Keep *;
}




