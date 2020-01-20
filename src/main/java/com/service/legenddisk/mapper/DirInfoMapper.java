package com.service.legenddisk.mapper;

import com.service.legenddisk.pojo.DirInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface DirInfoMapper {
    @Delete({
        "delete from dir_info",
        "where did = #{did,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer did);

    @Insert({
        "insert into dir_info (did, dirname, ",
        "dirpath, dirlevel, ",
        "type, filetype, username)",
        "values (#{did,jdbcType=INTEGER}, #{dirname,jdbcType=VARCHAR}, ",
        "#{dirpath,jdbcType=VARCHAR}, #{dirlevel,jdbcType=INTEGER}, ",
        "#{type,jdbcType=CHAR}, #{filetype,jdbcType=VARCHAR}, #{username,jdbcType=VARCHAR})"
    })
    int insert(DirInfo record);

    @InsertProvider(type=DirInfoSqlProvider.class, method="insertSelective")
    int insertSelective(DirInfo record);

    @Select({
        "select",
        "did, dirname, dirpath, dirlevel, type, filetype, username",
        "from dir_info",
        "where did = #{did,jdbcType=INTEGER}"
    })
    @ConstructorArgs({
        @Arg(column="did", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Arg(column="dirname", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="dirpath", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="dirlevel", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Arg(column="type", javaType=String.class, jdbcType=JdbcType.CHAR),
        @Arg(column="filetype", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="username", javaType=String.class, jdbcType=JdbcType.VARCHAR)
    })
    DirInfo selectByPrimaryKey(Integer did);

    @UpdateProvider(type=DirInfoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DirInfo record);

    @Update({
        "update dir_info",
        "set dirname = #{dirname,jdbcType=VARCHAR},",
          "dirpath = #{dirpath,jdbcType=VARCHAR},",
          "dirlevel = #{dirlevel,jdbcType=INTEGER},",
          "type = #{type,jdbcType=CHAR},",
          "filetype = #{filetype,jdbcType=VARCHAR},",
          "username = #{username,jdbcType=VARCHAR}",
        "where did = #{did,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(DirInfo record);
    @Select({"select * from dir_info where dirlevel=1 and username = #{username}"})
    List<DirInfo> getLevelOneDirList(String username);
    @Update("update dir_info set dirpath = #{dirpath,jdbcType=VARCHAR} where did = #{did,jdbcType=INTEGER}")
    int updateFilename(@Param("dirpath") String dirpath,@Param("did") Integer did);
}