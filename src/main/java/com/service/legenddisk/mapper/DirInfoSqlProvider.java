package com.service.legenddisk.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.service.legenddisk.pojo.DirInfo;

public class DirInfoSqlProvider {

    public String insertSelective(DirInfo record) {
        BEGIN();
        INSERT_INTO("dir_info");
        
        if (record.getDid() != null) {
            VALUES("did", "#{did,jdbcType=INTEGER}");
        }
        
        if (record.getDirname() != null) {
            VALUES("dirname", "#{dirname,jdbcType=VARCHAR}");
        }
        
        if (record.getDirpath() != null) {
            VALUES("dirpath", "#{dirpath,jdbcType=VARCHAR}");
        }
        
        if (record.getDirlevel() != null) {
            VALUES("dirlevel", "#{dirlevel,jdbcType=INTEGER}");
        }
        
        if (record.getType() != null) {
            VALUES("type", "#{type,jdbcType=CHAR}");
        }
        
        if (record.getFiletype() != null) {
            VALUES("filetype", "#{filetype,jdbcType=VARCHAR}");
        }
        
        if (record.getUsername() != null) {
            VALUES("username", "#{username,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(DirInfo record) {
        BEGIN();
        UPDATE("dir_info");
        
        if (record.getDirname() != null) {
            SET("dirname = #{dirname,jdbcType=VARCHAR}");
        }
        
        if (record.getDirpath() != null) {
            SET("dirpath = #{dirpath,jdbcType=VARCHAR}");
        }
        
        if (record.getDirlevel() != null) {
            SET("dirlevel = #{dirlevel,jdbcType=INTEGER}");
        }
        
        if (record.getType() != null) {
            SET("type = #{type,jdbcType=CHAR}");
        }
        
        if (record.getFiletype() != null) {
            SET("filetype = #{filetype,jdbcType=VARCHAR}");
        }
        
        if (record.getUsername() != null) {
            SET("username = #{username,jdbcType=VARCHAR}");
        }
        
        WHERE("did = #{did,jdbcType=INTEGER}");
        
        return SQL();
    }
}