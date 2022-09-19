package com.xx.storm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.storm.pojo.Picfile;
import com.xx.storm.service.PicfileService;
import com.xx.storm.mapper.PicfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 79340
* @description 针对表【picfile】的数据库操作Service实现
* @createDate 2022-09-17 23:32:55
*/
@Service
public class PicfileServiceImpl extends ServiceImpl<PicfileMapper, Picfile>
    implements PicfileService{

    private PicfileMapper picfileMapper;
    private static final Logger logger = LoggerFactory.getLogger(PicfileServiceImpl.class);

    @Override
    public void insertPicfile(Picfile picfile) {
        try {
            int i = picfileMapper.insert(picfile);
            logger.info("新增"+i+"条数据："+picfile.getLocation());
        } catch (Exception e) {
            logger.error("新增失败!,原因是:",e);
        }
    }

    @Override
    public void updatePicfile(Picfile picfile) {
        UpdateWrapper<Picfile> wrapper = new UpdateWrapper<>();
        wrapper.eq("location",picfile.getLocation());
        picfileMapper.update(picfile,wrapper);
    }

    @Override
    public Picfile findByPicfile(Picfile picfile) {
        QueryWrapper<Picfile> wrapper = new QueryWrapper<>();
        wrapper.eq("location",picfile.getLocation());
        return picfileMapper.selectOne(wrapper);
    }
}




