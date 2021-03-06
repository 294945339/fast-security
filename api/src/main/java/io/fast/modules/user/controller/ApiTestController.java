package io.fast.modules.user.controller;


import io.fast.common.annotation.Login;
import io.fast.common.annotation.LoginUser;
import io.fast.common.rabbltMq.MsgProducer;
import io.fast.common.utils.R;
import io.fast.modules.user.domain.UserDomain;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * 测试接口
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:47
 */
@RestController
@RequestMapping("/api")
@Api(tags = "测试接口")
public class ApiTestController {

    @Resource
    private MsgProducer msgProducer;

    @Login
    @GetMapping("userInfo")
    @ApiOperation(value = "获取用户信息", response = UserDomain.class)
    public R userInfo(@ApiIgnore @LoginUser UserDomain user) {
        return R.ok().put("user", user);
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public R userInfo(@ApiIgnore @RequestAttribute("userId") Integer userId) {
        return R.ok().put("userId", userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public R notToken() {
        msgProducer.sendMsg("hello");
        return R.ok().put("msg", "无需token也能访问。。。");
    }

}
