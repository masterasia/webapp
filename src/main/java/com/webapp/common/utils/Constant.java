package com.webapp.common.utils;

/**
 * 常量
 *
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2016年11月15日 下午1:23:52
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;

    /**
     * 菜单类型
     *
     * @author Robert.XU
     * @email <xutao@bjnja.com>
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author Robert.XU
     * @email <xutao@bjnja.com>
     * @date 2017年11月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author Robert.XU
     * @email <xutao@bjnja.com>
     * @date 2017年11月3日 上午12:07:22
     */
    public enum HttpMethod {
        /**
         * 正常
         */
        GET(0),
        /**
         * 暂停
         */
        POST(1);

        private int value;

        private HttpMethod(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        private CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 迁入登记类型
     *
     * @author Robert.XU
     * @email <xutao@bjnja.com>
     * @date 2017年11月15日 下午1:24:29
     */
    public enum MoveType {
        /**
         * 登记
         */
        REGISTER("已登记"),
        /**
         * 审核通过-迁出地已审核
         */
        EXAMINED("迁出地已审核"),
        /**
         * 迁出放许可迁出,迁入地woreda已审核
         */
        SUGGEST("待迁入"),
        /**
         * 已办完
         */
        DOWN("迁入已办结"),
        /**
         * 废弃
         */
        ABANDONED("废弃");

        private String value;

        private MoveType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 登记类型
     *
     * @author Robert.XU
     * @email <xutao@bjnja.com>
     * @date 2017年11月15日 下午1:24:29
     */
    public enum IDCardType {
        /**
         * 申请
         */
        REGISTER("申请"),
        /**
         * 审核通过
         */
        EXAMINED("审核通过"),
        /**
         * 制证中
         */
        MAKE("制证中"),
        /**
         * 制证中
         */
        FINISHED("制证完成"),
        /**
         * 运输中
         */
        TRANSPORT("运输中"),
        /**
         * 待领取
         */
        GRANT("待领取"),
        /**
         * 已发放
         */
        DOWN("已发放"),
        /**
         * 废弃
         */
        ABANDONED("废弃");

        private String value;

        private IDCardType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * @author Robert.XU
     * @email <xutao@bjnja.com>
     * @date 2017/12/12 0012 下午 03:35
     */
    public enum PopulationType {
        /**
         * 出生
         */
        BORN("出生"),
        /**
         * 人口调查登记
         */
        CENSUS("普查登记"),
        /**
         * 服役
         */
        ENLIST("服役"),
        /**
         * 服刑
         */
        PRISON("服刑"),
        /**
         * 劳教
         */
        REEDUCATION("劳教"),
        /**
         * 失踪
         */
        DISAPPEAR("失踪"),
        /**
         * 死亡
         */
        DEATH("死亡");
        private String value;

        private PopulationType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
