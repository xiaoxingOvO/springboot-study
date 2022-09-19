package com.xx.mybatis.mapper;

import com.xx.mybatis.pojo.Test;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 79340
* @description 针对表【test】的数据库操作Mapper
* @createDate 2022-09-18 20:16:02
* @Entity com.xx.mybatis.pojo.Test
*/
@Mapper
public interface TestMapper extends BaseMapper<Test> {
    /**
     * 查询所有
     *
     * @return 用户列表
     */
    @Select("SELECT * FROM test")
    List<Test> selectAllUser();

    /**
     * 根据id查询用户
     *
     * @param id 主键id
     * @return 当前id的用户，不存在则是 {@code null}
     */
    @Select("SELECT * FROM test WHERE id = #{id}")
    Test selectUserById(@Param("id") Long id);

}




