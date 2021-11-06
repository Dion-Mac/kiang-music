package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌手Dao
 */
@Repository
public interface SingerMapper {
    /**
     * 增加
     */
    int insert(Singer singer);

    /**
     * 修改
     */
    int update(Singer singer);

    /**
     * 删除
     */
    int delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    Singer selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌手
     */
    List<Singer> allSinger();

    /**
     * 根据歌手名字模糊查询列表
     */
    List<Singer> singerOfName(String name);

    /**
     * 根据性别查询列表
     */
    List<Singer> singerOfSex(Integer sex);

}
