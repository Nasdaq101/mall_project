package com.hmall.service.impl;

import com.hmall.domain.po.Address;
import com.hmall.mapper.AddressMapper;
import com.hmall.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
