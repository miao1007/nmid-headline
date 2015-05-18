#ButterKnife
#Butter Knife generates and uses classes dynamically which means that static analysis tools like ProGuard may think they are unused. In order to prevent them from being removed, explicitly mark them to be kept. To prevent ProGuard renaming classes that use @InjectView on a member field the keepclasseswithmembernames option is used.

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#Otto
#This ensures your annotated methods aren't removed by ProGuard.
#http://square.github.io/otto/
-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

#retrofit
#If you are using Proguard in your project add the following lines to your configuration:
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# OkHttp/Picasso
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**


# Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**
