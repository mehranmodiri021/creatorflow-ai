# Add project specific ProGuard rules here.
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.creatorflow.ai.data.model.** { *; }
-keep class com.creatorflow.ai.data.local.** { *; }
-keep class com.creatorflow.ai.data.remote.** { *; }

# Retrofit & OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}

# Gson
-keepattributes EnclosingMethod
-keepclassmembers enum * { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Room
-keep class * extends androidx.room.RoomDatabase
