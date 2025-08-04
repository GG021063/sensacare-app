package com.sensacare.veepoo.room;

@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0002\u0018\u00002\u00020\u0001BJ\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\fR\u0015\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\rR\u000f\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\u000eR\u0013\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u000fR\u000f\u0010\b\u001a\u00020\t\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0010R\u000f\u0010\n\u001a\u00020\t\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u0010R\u0015\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\r\u00a8\u0006\u0011"}, d2 = {"Lcom/sensacare/veepoo/room/ForeignKey;", "", "entity", "Lkotlin/reflect/KClass;", "parentColumns", "", "", "childColumns", "onDelete", "", "onUpdate", "deferred", "", "()[Ljava/lang/String;", "()Z", "()Ljava/lang/Class;", "()I", "app_debug"})
public abstract @interface ForeignKey {
    
    public abstract java.lang.Class<?> entity();
    
    public abstract java.lang.String[] parentColumns() default {};
    
    public abstract java.lang.String[] childColumns() default {};
    
    public abstract int onDelete() default 0;
    
    public abstract int onUpdate() default 0;
    
    public abstract boolean deferred() default false;
}