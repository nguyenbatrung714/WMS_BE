package org.example.wms_be.converter.inventory;

import org.example.wms_be.data.request.ConsignmentReq;
import org.example.wms_be.entity.inventory.Consignment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsignmentConverter {
    Consignment toConsignment(ConsignmentReq request);

    ConsignmentReq toConsignmentReq(Consignment consignment);
}
