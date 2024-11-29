package org.example.wms_be.data.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockAccountReq {
    private String username;
    private Boolean active;
}
