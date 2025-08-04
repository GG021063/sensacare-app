package com.sensacare.veepoo.room;

@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\u0002\u0018\u00002\u00020\u0001B$\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007R\u000f\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\bR\u000f\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\tR\u0015\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/sensacare/veepoo/room/Index;", "", "value", "", "", "name", "unique", "", "()Ljava/lang/String;", "()Z", "()[Ljava/lang/String;", "app_debug"})
public abstract @interface Index {
    
    public abstract java.lang.String[] value() default {};
    
    public abstract java.lang.String name() default "";
    
    public abstract boolean unique() default false;
}