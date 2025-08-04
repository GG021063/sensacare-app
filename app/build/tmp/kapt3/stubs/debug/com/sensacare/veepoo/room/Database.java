package com.sensacare.veepoo.room;

@kotlin.annotation.Target(allowedTargets = {kotlin.annotation.AnnotationTarget.CLASS})
@kotlin.annotation.Retention(value = kotlin.annotation.AnnotationRetention.RUNTIME)
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(value = {java.lang.annotation.ElementType.TYPE})
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0087\u0002\u0018\u00002\u00020\u0001B<\u0012\u0012\b\u0002\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\u0012\b\u0002\u0010\t\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003R\u0019\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\nR\u000f\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u000bR\u000f\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\fR\u0019\u0010\t\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2 = {"Lcom/sensacare/veepoo/room/Database;", "", "entities", "", "Lkotlin/reflect/KClass;", "version", "", "exportSchema", "", "views", "()[Ljava/lang/Class;", "()Z", "()I", "app_debug"})
public abstract @interface Database {
    
    public abstract java.lang.Class<?>[] entities() default {};
    
    public abstract int version() default 1;
    
    public abstract boolean exportSchema() default true;
    
    public abstract java.lang.Class<?>[] views() default {};
}