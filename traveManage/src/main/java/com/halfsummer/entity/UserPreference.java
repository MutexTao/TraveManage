package com.halfsummer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class UserPreference {
    /**
     * 用户编号
     */
    @TableId(value = "uid", type = IdType.UUID)
    private Long uid;
    /**
     * 商品编号
     */
    @TableId(value = "pid", type = IdType.INPUT)
    private Long pid;
    /**
     * 评分
     */
    private Integer preference;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getPreference() {
        return preference;
    }

    public void setPreference(Integer preference) {
        this.preference = preference;
    }
}
