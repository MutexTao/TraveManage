package com.halfsummer.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * 主题表
 */
public class Theme extends Model<Theme> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    /**
     * 主题编号
     */
    private String id;
    /**
     * 主题名称
     */
    private String themeName;
    /**
     * 图片地址
     */
    private String imageUrl;
    /**
     * 主题描述
     */
    private String detail;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Theme{" +
        ", id=" + id +
        ", themeName=" + themeName +
        ", imageUrl=" + imageUrl +
        ", detail=" + detail +
        "}";
    }
}
