package com.sensacare.veepoo.room;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\b&\u0018\u0000 \u00052\u00020\u0001:\u0003\u0003\u0004\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0006"}, d2 = {"Lcom/sensacare/veepoo/room/RoomDatabase;", "", "()V", "Builder", "Callback", "Companion", "app_debug"})
public abstract class RoomDatabase {
    @org.jetbrains.annotations.NotNull
    public static final com.sensacare.veepoo.room.RoomDatabase.Companion Companion = null;
    
    public RoomDatabase() {
        super();
    }
    
    @kotlin.jvm.JvmStatic
    @org.jetbrains.annotations.NotNull
    public static final <T extends com.sensacare.veepoo.room.RoomDatabase>com.sensacare.veepoo.room.RoomDatabase.Builder<T> databaseBuilder(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.Class<T> klass, @org.jetbrains.annotations.NotNull
    java.lang.String name) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0006\u001a\u00020\u0007J\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000J\u000b\u0010\t\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\nJ\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000\u00a8\u0006\f"}, d2 = {"Lcom/sensacare/veepoo/room/RoomDatabase$Builder;", "T", "Lcom/sensacare/veepoo/room/RoomDatabase;", "", "()V", "addCallback", "callback", "Lcom/sensacare/veepoo/room/RoomDatabase$Callback;", "allowMainThreadQueries", "build", "()Lcom/sensacare/veepoo/room/RoomDatabase;", "fallbackToDestructiveMigration", "app_debug"})
    public static final class Builder<T extends com.sensacare.veepoo.room.RoomDatabase> {
        
        public Builder() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final T build() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.room.RoomDatabase.Builder<T> allowMainThreadQueries() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.room.RoomDatabase.Builder<T> fallbackToDestructiveMigration() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.room.RoomDatabase.Builder<T> addCallback(@org.jetbrains.annotations.NotNull
        com.sensacare.veepoo.room.RoomDatabase.Callback callback) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\b"}, d2 = {"Lcom/sensacare/veepoo/room/RoomDatabase$Callback;", "", "()V", "onCreate", "", "db", "Landroidx/sqlite/db/SupportSQLiteDatabase;", "onOpen", "app_debug"})
    public static abstract class Callback {
        
        public Callback() {
            super();
        }
        
        public void onCreate(@org.jetbrains.annotations.NotNull
        androidx.sqlite.db.SupportSQLiteDatabase db) {
        }
        
        public void onOpen(@org.jetbrains.annotations.NotNull
        androidx.sqlite.db.SupportSQLiteDatabase db) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007\u00a8\u0006\r"}, d2 = {"Lcom/sensacare/veepoo/room/RoomDatabase$Companion;", "", "()V", "databaseBuilder", "Lcom/sensacare/veepoo/room/RoomDatabase$Builder;", "T", "Lcom/sensacare/veepoo/room/RoomDatabase;", "context", "Landroid/content/Context;", "klass", "Ljava/lang/Class;", "name", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @kotlin.jvm.JvmStatic
        @org.jetbrains.annotations.NotNull
        public final <T extends com.sensacare.veepoo.room.RoomDatabase>com.sensacare.veepoo.room.RoomDatabase.Builder<T> databaseBuilder(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        java.lang.Class<T> klass, @org.jetbrains.annotations.NotNull
        java.lang.String name) {
            return null;
        }
    }
}