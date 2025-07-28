package com.hmall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmall.common.exception.BadRequestException;
import com.hmall.common.exception.BizIllegalException;
import com.hmall.common.exception.ForbiddenException;
import com.hmall.common.utils.UserContext;
import com.hmall.config.JwtProperties;
import com.hmall.domain.dto.LoginFormDTO;
import com.hmall.domain.po.User;
import com.hmall.domain.vo.UserLoginVO;
import com.hmall.enums.UserStatus;
import com.hmall.mapper.UserMapper;
import com.hmall.service.IUserService;
import com.hmall.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final PasswordEncoder passwordEncoder;

    private final JwtTool jwtTool;

    private final JwtProperties jwtProperties;

    @Override
    public UserLoginVO login(LoginFormDTO loginDTO) {
        // validate
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        // search by username/phoneNo
        User user = lambdaQuery().eq(User::getUsername, username).one();
        Assert.notNull(user, "wrong username");
        // 3.if frozen?
        if (user.getStatus() == UserStatus.FROZEN) {
            throw new ForbiddenException("this user is under frozen");
        }
        // 4.password?
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("username or password wrong!");
        }
        // 5.generate TOKEN
        String token = jwtTool.createToken(user.getId(), jwtProperties.getTokenTTL());
        // 6.to vo
        UserLoginVO vo = new UserLoginVO();
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setBalance(user.getBalance());
        vo.setToken(token);
        return vo;
    }

    @Override
    public void deductMoney(String pw, Integer totalFee) {
        log.info("Try deduction");
        // 1.password?
        User user = getById(UserContext.getUser());
        if(user == null || !passwordEncoder.matches(pw, user.getPassword())){
            // pswd wrong
            throw new BizIllegalException("wrong password!");
        }

        // 2.try deduct money
        try {
            baseMapper.updateMoney(UserContext.getUser(), totalFee);
        } catch (Exception e) {
            throw new RuntimeException("deduction failed，balance might not be enough！", e);
        }
        log.info("deduction successful!");
    }
}
