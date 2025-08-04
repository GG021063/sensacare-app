package com.sensacare.veepoo.room;

/**
 * Stub implementations of Room database annotations and classes.
 * These are temporary implementations to allow compilation until proper Room integration is working.
 */
@kotlin.annotation.Target(allowedTargets = {kotlin.annotation.AnnotationTarget.CLASS})
@kotlin.annotation.Retention(value = kotlin.annotation.AnnotationRetention.RUNTIME)
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(value = {java.lang.annotation.ElementType.TYPE})
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0087\u0002\u0018\u00002\u00020\u0001BJ\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0005\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005R\u0015\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0005\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u000bR\u0015\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0015\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\rR\u0015\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\fR\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/sensacare/veepoo/room/Entity;", "", "tableName", "", "indices", "", "Lcom/sensacare/veepoo/room/Index;", "primaryKeys", "foreignKeys", "Lcom/sensacare/veepoo/room/ForeignKey;", "ignoredColumns", "()[Lcom/sensacare/veepoo/room/ForeignKey;", "()[Ljava/lang/String;", "()[Lcom/sensacare/veepoo/room/Index;", "()Ljava/lang/String;", "app_debug"})
public abstract @interface Entity {
    
    public abstract java.lang.String tableName() default "";
    
    public abstract com.sensacare.veepoo.room.Index[] indices() default {};
    
    public abstract java.lang.String[] primaryKeys() default {};
    
    public abstract com.sensacare.veepoo.room.ForeignKey[] foreignKeys() default {};
    
    public abstract java.lang.String[] ignoredColumns() default {};
}