package com.xx.storm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName picfile
 */
@TableName(value ="picfile")
public class Picfile implements Serializable {
    /**
     * 位置
     */
    private String location;

    /**
     * 文件夹名
     */
    private String name;

    /**
     * 扩展名
     */
    private String ext;

    /**
     * 大小
     */
    private Long size;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 位置
     */
    public String getLocation() {
        return location;
    }

    /**
     * 位置
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 文件夹名
     */
    public String getName() {
        return name;
    }

    /**
     * 文件夹名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 扩展名
     */
    public String getExt() {
        return ext;
    }

    /**
     * 扩展名
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * 大小
     */
    public Long getSize() {
        return size;
    }

    /**
     * 大小
     */
    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Picfile other = (Picfile) that;
        return (this.getLocation() == null ? other.getLocation() == null : this.getLocation().equals(other.getLocation()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getExt() == null ? other.getExt() == null : this.getExt().equals(other.getExt()))
            && (this.getSize() == null ? other.getSize() == null : this.getSize().equals(other.getSize()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getExt() == null) ? 0 : getExt().hashCode());
        result = prime * result + ((getSize() == null) ? 0 : getSize().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", location=").append(location);
        sb.append(", name=").append(name);
        sb.append(", ext=").append(ext);
        sb.append(", size=").append(size);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}