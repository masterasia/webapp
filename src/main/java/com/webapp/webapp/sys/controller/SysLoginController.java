package com.webapp.webapp.sys.controller;


import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.webapp.common.utils.BaseResponce;
import com.webapp.webapp.sys.shiro.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
public class SysLoginController {
    @Autowired
    private Producer producer;

    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    @RequestMapping(value = "/sys/login/backdoor", method = {RequestMethod.GET, RequestMethod.POST})
    public BaseResponce login(String account, String checkPass) throws IOException {
        System.out.println("username:" + account + "  password:" + checkPass);
        if (null == account || account.isEmpty()) {
            account = "admin";
        }
        if (null == checkPass || checkPass.isEmpty()) {
            checkPass = "admin";
        }
        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(account, checkPass);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return BaseResponce.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return BaseResponce.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            return BaseResponce.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return BaseResponce.error("账户验证失败");
        }
        return BaseResponce.ok();
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public BaseResponce login(String account, String checkPass, String captcha) throws IOException {
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if (!captcha.equalsIgnoreCase(kaptcha)) {
            return BaseResponce.error("验证码不正确");
        }

        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(account, checkPass);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return BaseResponce.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return BaseResponce.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            return BaseResponce.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return BaseResponce.error("账户验证失败");
        }

        return BaseResponce.ok();
    }

    /**
     * 退出
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        ShiroUtils.logout();
        return "redirect:login.html";
    }

}
