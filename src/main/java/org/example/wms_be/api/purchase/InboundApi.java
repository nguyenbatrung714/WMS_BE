package org.example.wms_be.api.purchase;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.request.InboundReq;
import org.example.wms_be.service.InboundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/inbounds")
public class InboundApi {

    private final InboundService inboundService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<InboundReq>> createInbound(@RequestBody InboundReq inboundReq,
                                                                 HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                201,
                "Create inbound successfully",
                inboundService.createInbound(inboundReq)
        ), HttpStatus.CREATED);
    }
}
