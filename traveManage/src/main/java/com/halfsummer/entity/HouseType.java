package com.halfsummer.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 *  房型表
 */
public class HouseType extends Model<HouseType> {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.UUID)
    /**
     * 名称
     */
    private String id;
    /**
     * 房型名
     */
    private String houseTypeName;
    /**
     * 房型描述
     */
    private String houseTypeDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseTypeName() {
        return houseTypeName;
    }

    public void setHouseTypeName(String houseTypeName) {
        this.houseTypeName = houseTypeName;
    }

    public String getHouseTypeDetail() {
        return houseTypeDetail;
    }

    public void setHouseTypeDetail(String houseTypeDetail) {
        this.houseTypeDetail = houseTypeDetail;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
