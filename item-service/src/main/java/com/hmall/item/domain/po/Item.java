package com.hmall.item.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * item list
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * item id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * SKU name
     */
    private String name;

    /**
     * price, cent
     */
    private Integer price;

    /**
     * stock amount
     */
    private Integer stock;

    /**
     * item image
     */
    private String image;

    /**
     * item category
     */
    private String category;

    /**
     * item brand
     */
    private String brand;

    /**
     * item spec
     */
    private String spec;

    /**
     * item sold
     */
    private Integer sold;

    /**
     * comment amount
     */
    private Integer commentCount;

    /**
     * enable ad?  true/false
     */
    @TableField("isAD")
    private Boolean isAD;

    /**
     * status 1-online，2-offline，3-deleted
     */
    private Integer status;

    /**
     * create time
     */
    private LocalDateTime createTime;

    /**
     * update time
     */
    private LocalDateTime updateTime;

    /**
     * creater
     */
    private Long creater;

    /**
     * updater
     */
    private Long updater;


}
