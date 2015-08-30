#When using ProGuard, you need to specify that generated classes should be kept, and that annotated fields and methods should not be renamed. To achieve these criteria, the following lines can be added to your ProGuard configuration:
-keep class me.denley.preferenceinjector.** { *; }
-dontwarn me.denley.preferenceinjector.internal.**
-keep class **$$SharedPreferenceInjector { *; }

-keepclasseswithmembernames class * {
    @me.denley.preferenceinjector.* <fields>;
}

-keepclasseswithmembernames class * {
    @me.denley.preferenceinjector.* <methods>;
}