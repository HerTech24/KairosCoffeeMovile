# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# ========== DEBUGGING (Descomenta en desarrollo) ==========
# Mantiene los números de línea para debugging de crashes
-keepattributes SourceFile,LineNumberTable
# Oculta el nombre del archivo fuente original
-renamesourcefileattribute SourceFile

# ========== ANOTACIONES Y FIRMAS ==========
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# ========== RETROFIT ==========
# Mantiene las interfaces de Retrofit
-keep interface retrofit2.** { *; }
-keep class retrofit2.** { *; }
-keepattributes Exceptions

# Mantiene los métodos de servicios API
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# ========== GSON ==========
# Mantiene clases genéricas de Gson
-keep class com.google.gson.** { *; }
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

# Mantiene todos los DTOs (Data Transfer Objects)
-keep class com.android.kairoscoffeemovile.data.remote.dto.** { *; }

# Mantiene constructores de clases serializadas
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# ========== ROOM DATABASE ==========
# Mantiene la base de datos y entidades
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Mantiene las entidades de Room
-keep class com.android.kairoscoffeemovile.data.local.entities.** { *; }

# Mantiene los DAOs
-keep interface com.android.kairoscoffeemovile.data.local.dao.** { *; }

# ========== KOTLIN COROUTINES ==========
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# ========== JETPACK COMPOSE ==========
# Mantiene funciones composables
-keep class androidx.compose.** { *; }
-keep @androidx.compose.runtime.Composable class * { *; }

# ========== VIEWMODEL ==========
-keep class * extends androidx.lifecycle.ViewModel {
    <init>();
}
-keep class * extends androidx.lifecycle.AndroidViewModel {
    <init>(android.app.Application);
}

# ========== KOTLIN REFLECTION ==========
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** { *; }
-dontwarn kotlin.reflect.**

# ========== MODELOS DE LA APP ==========
# Mantiene todos los modelos de datos sin ofuscar
-keep class com.android.kairoscoffeemovile.data.local.entities.** { *; }
-keep class com.android.kairoscoffeemovile.data.remote.dto.** { *; }

# ========== NAVEGACIÓN ==========
-keep class androidx.navigation.** { *; }

# ========== ELIMINACIÓN DE WARNINGS ==========
# Ignora warnings de librerías de terceros
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe

# ========== OPTIMIZACIÓN ==========
# Permite optimización agresiva
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose

# ========== CRASHLYTICS (Si lo usas en el futuro) ==========
# -keepattributes *Annotation*
# -keepattributes SourceFile,LineNumberTable
# -keep public class * extends java.lang.Exception

# ========== TESTING (No afecta release) ==========
-dontwarn org.junit.**
-dontwarn org.mockito.**
-dontwarn org.hamcrest.**