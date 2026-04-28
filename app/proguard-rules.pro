# Add project specific ProGuard rules here.
-keepattributes *Annotation*
-keep class androidx.room.** { *; }
-keep class com.antigravity.twentyfortyeight.data.** { *; }
-keep class com.antigravity.twentyfortyeight.engine.** { *; }
-dontwarn org.conscrypt.**
