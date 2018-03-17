package com.webapp.webapp.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年11月24日 下午11:05:27
 */
@Controller
public class SysPageController {

    @RequestMapping("webapp/{module}/{url}.html")
    public String module(@PathVariable("module") String module, @PathVariable("url") String url) {
        return "webapp/" + module + "/" + url;
    }

    @RequestMapping("{url}.html")
    public String url(@PathVariable("url") String url) {
        return url;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
