package com.hmall.controller;


import com.hmall.common.exception.BadRequestException;
import com.hmall.common.utils.BeanUtils;
import com.hmall.common.utils.CollUtils;
import com.hmall.common.utils.UserContext;
import com.hmall.domain.dto.AddressDTO;
import com.hmall.domain.po.Address;
import com.hmall.service.IAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Api(tags = "address controller")
public class AddressController {

    private final IAddressService addressService;

    @ApiOperation("find address by id")
    @GetMapping("{addressId}")
    public AddressDTO findAddressById(@ApiParam("address_id") @PathVariable("addressId") Long id) {
        Address address = addressService.getById(id);

        Long userId = UserContext.getUser();
        if(!address.getUserId().equals(userId)){
            throw new BadRequestException("address not belongs to current user!");
        }
        return BeanUtils.copyBean(address, AddressDTO.class);
    }
    @ApiOperation("get MY address")
    @GetMapping
    public List<AddressDTO> findMyAddresses() {
        List<Address> list = addressService.query().eq("user_id", UserContext.getUser()).list();

        if (CollUtils.isEmpty(list)) {
            return CollUtils.emptyList();
        }
        // 3. to vo
        return BeanUtils.copyList(list, AddressDTO.class);
    }
}
