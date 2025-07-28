package com.yunfei.service.impl;

import com.yunfei.domain.po.Address;
import com.yunfei.mapper.AddressMapper;
import com.yunfei.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
