package org.example.wms_be.constant;
import lombok.Getter;

@Getter
public class Status {
    public static final String APPROVING = "approving";
    public static final String CONFIRM = "confirm";
    public static final String REJECT = "reject";
    public static final String OPEN = "open";
    private Status() {
        throw new IllegalStateException("Utility class");
    }
}
