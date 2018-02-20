-keep class android.net.http.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.kobjects.** { *; }
-keep class org.ksoap2.** { *; }
-keep class org.kxml2.** { *; }
-keep class org.xmlpull.** { *; }
-keep class okhttp3.OkHttpClient { *; }

-dontwarn sun.reflect.**
-dontwarn android.test.**
-dontwarn org.mockito.**
-dontwarn okhttp3.internal.**
-dontwarn okhttp3.**
-dontwarn net.bytebuddy.**
-dontwarn okio.**
-dontwarn org.junit.**
-dontwarn org.xmlpull.v1.**
-dontwarn org.objenesis.**
-dontwarn org.apache.http.**
-dontwarn android.net.http.**
-dontwarn junit.runner.**
-dontwarn junit.framework.**

-dontnote sun.reflect.**
-dontnote android.test.**
-dontnote org.mockito.**
-dontnote okhttp3.internal.**
-dontnote okhttp3.**
-dontnote net.bytebuddy.**
-dontnote okio.**
-dontnote org.junit.**
-dontnote org.xmlpull.v1.**
-dontnote org.objenesis.**
-dontnote org.apache.http.**
-dontnote android.net.http.**
-dontnote junit.runner.**
-dontnote junit.framework.**
-dontnote org.kobjects.**


