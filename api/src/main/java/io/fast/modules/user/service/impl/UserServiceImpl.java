package io.fast.modules.user.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.fast.common.exception.RRException;
import io.fast.common.validator.Assert;
import io.fast.modules.user.dao.UserDao;
import io.fast.modules.user.domain.TokenDomain;
import io.fast.modules.user.domain.UserDomain;
import io.fast.modules.user.form.LoginForm;
import io.fast.modules.user.service.TokenService;
import io.fast.modules.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserDomain> implements UserService {
	@Autowired
	private TokenService tokenService;

	@Override
	public UserDomain queryByMobile(String mobile) {
		UserDomain userDomain = new UserDomain();
		userDomain.setMobile(mobile);
		return baseMapper.selectOne(userDomain);
	}

	@Override
	public Map<String, Object> login(LoginForm form) {
		UserDomain user = queryByMobile(form.getMobile());
		Assert.isNull(user, "手机号或密码错误");

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
			throw new RRException("手机号或密码错误");
		}

		//获取登录token
		TokenDomain tokenDomain = tokenService.createToken(user.getUserId());

		Map<String, Object> map = new HashMap<>(2);
		map.put("token", tokenDomain.getToken());
		map.put("expire", tokenDomain.getExpireTime().getTime() - System.currentTimeMillis());

		return map;
	}

}
