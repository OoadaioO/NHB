# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/michaelliu/Documents/Michaelliu/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keepattributes Signature
-optimizationpasses 7
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-allowaccessmodification
-repackageclasses
-dontusemixedcaseclassnames
-dontoptimize
-dontskipnonpubliclibraryclassmembers
-ignorewarnings

#手动启用support keep注解
#http://tools.android.com/tech-docs/support-annotations
-keep,allowobfuscation @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclassmembers class * {
    @android.support.annotation.Keep *;
}

#Android原生混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
-keep public class com.android.vending.licensing.ILicensingService

-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference


# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}


-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
-keep class android.support.**{ *; }

# adding this in to preserve line numbers so that the stack traces
# can be remapped
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-keepattributes InnerClasses

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-dontwarn org.apache.commons.**
#Android原生混淆

#自定义view的混淆
-keep class com.edmodo.cropper.** { *;}
-keep class uk.co.senab.photoview.** { *;}
-keep class com.nostra13.universalimageloader.** { *;}
-keep class com.loopj.android.http.** { *;}
-keep class com.nhb.app.custom.common.view.** { *;}

-keep class com.nhb.app.custom.bean.** { *;}
-keep class com.nhb.app.custom.domain.GMResponse { *;}

#推送的混淆
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }


#避免自定义WebView被混淆
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

#不混淆BuildConfig类及成员
-keep class com.nhb.app.custom.BuildConfig { *;}


# SnappyDB
-keep class sun.misc.**
-keep class sun.nio.**
-keep class java.beans.**
-keep class sun.nio.ch.**
-keep class com.snappydb.**
-keep class com.snappydb.internal.DBImpl { *; }
-keep class com.esotericsoftware.kryo.** { *; }
-dontwarn com.esotericsoftware.kryo.**
-dontwarn org.objenesis.instantiator.**

#注解@see http://jakewharton.github.io/butterknife/
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#fastjson
-dontwarn com.alibaba.fastjson.**
-dontwarn android.support.v4.**

#sharesdk
-keep class android.net.http.SslError
-keep class android.webkit.**{*;}
-keep class m.framework.**{*;}
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-dontwarn cn.sharesdk.**
-dontwarn **.R$*
-keep public class com.nhb.app.custom.R$*{
    public static final int *;
}

#umeng
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

#百度地图混淆
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}

#下拉刷新
-keep public class * extends com.handmark.pulltorefresh.library.PullToRefreshListView
-keep class com.handmark.pulltorefresh.library.** { *;}
############################GMComponent#################################

#webview
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

# 在线热更新
-keep class cn.jiajixin.nuwa.** { *; }

# 不混淆TuSDK本身的代码
-dontwarn org.lasque.tusdk.**
-keep class org.lasque.tusdk.**{public *; protected *; }
-keep class org.lasque.tusdk.core.utils.image.GifHelper{ *; }


-dontwarn com.google.gson.**
-dontwarn com.google.protobuf.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

-keep class org.apache.commons.**{ *; }
-keep class it.sephiroth.android.library.exif2.**{ *; }

#retrofit2网络库
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#picasso图片加载库
-dontwarn com.squareup.okhttp.**

