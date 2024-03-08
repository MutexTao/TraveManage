package com.halfsummer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class UserAction{
    /**
     * 用户编号
     */
    @TableId(value = "userid", type = IdType.UUID)
    private String userid;
    /**
     * 商品编号
     */
    @TableId(value = "productid", type = IdType.INPUT)
    private String productid;
    /**
     * 得分
     */
    private Integer score;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
