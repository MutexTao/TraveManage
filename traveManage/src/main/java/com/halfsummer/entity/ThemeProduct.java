package com.halfsummer.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * 主题产品表
 */
public class ThemeProduct extends Model<ThemeProduct> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    /**
     * 主题产品编号
     */
    private String id;
    /**
     * 产品编号
     */
    private String productId;
    /**
     * 主题编号
     */
    private String themeId;
    /**
     * 主题名称
     */
    private String themeName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ThemeProduct{" +
        ", id=" + id +
        ", productId=" + productId +
        ", themeId=" + themeId +
        ", themeName=" + themeName +
        "}";
    }
}
