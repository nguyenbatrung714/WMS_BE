package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.CustomerDto;
import org.example.wms_be.data.dto.SupplierDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerApi {
    private final CustomerService customerService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerDto>>> getAllCustomers(HttpServletRequest httpServletRequest){
        return new ResponseEntity<>( new ApiResponse<>(
                httpServletRequest.getRequestURI(),
                200,
                "List of customer",
                customerService.getAllCustomer()
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDto>> saveSupplier(HttpServletRequest request, @RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(new ApiResponse<>(
            request.getRequestURI(),
            200,
            "Customer save successfully ",
            customerService.saveCustomer(customerDto)
    ),HttpStatus.OK);
}
    @DeleteMapping("/{maKhachHang}")
    public ResponseEntity<ApiResponse<Void>> deleteSupplier(@PathVariable Integer maKhachHang,
                                                            HttpServletRequest request){

        return new ResponseEntity<>(new ApiResponse<>(
            request.getRequestURI(),
            200,
            "Customer delete successfully",
            customerService.deleteCustomer(maKhachHang)
    ),HttpStatus.OK);
}
    @GetMapping("/{maKhachHang}")
    public ResponseEntity<ApiResponse<CustomerDto>> getSupplierById(@PathVariable Integer maKhachHang,
                                                                    HttpServletRequest request)
 {
         return new ResponseEntity<>(new ApiResponse<>(
            request.getRequestURI(),
            200,
            "Customer found successfully",
            customerService.getAllCustomerById (maKhachHang)
        ),HttpStatus.OK);
}

}

