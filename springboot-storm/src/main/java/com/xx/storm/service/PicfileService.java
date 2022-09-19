package com.xx.storm.service;

import com.xx.storm.pojo.Picfile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 79340
* @description 针对表【picfile】的数据库操作Service
* @createDate 2022-09-17 23:32:55
*/
public interface PicfileService extends IService<Picfile> {

    /**
     * 批量新增用户
     * @param picfile
     * @return
     */
    void insertPicfile(Picfile picfile);

    void updatePicfile(Picfile picfile);


    /**
     * 查询用于
     * @param picfile
     * @return
     */
    Picfile findByPicfile(Picfile picfile);

}
