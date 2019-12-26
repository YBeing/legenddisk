package com.service.legenddisk.pojo;

public class DirInfo {
    private Integer did;

    private String dirname;

    private String dirpath;

    private Integer dirlevel;

    private String type;

    private String filetype;

    private String username;

    public DirInfo(Integer did, String dirname, String dirpath, Integer dirlevel, String type, String filetype, String username) {
        this.did = did;
        this.dirname = dirname;
        this.dirpath = dirpath;
        this.dirlevel = dirlevel;
        this.type = type;
        this.filetype = filetype;
        this.username = username;
    }

    public DirInfo() {
        super();
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getDirname() {
        return dirname;
    }

    public void setDirname(String dirname) {
        this.dirname = dirname == null ? null : dirname.trim();
    }

    public String getDirpath() {
        return dirpath;
    }

    public void setDirpath(String dirpath) {
        this.dirpath = dirpath == null ? null : dirpath.trim();
    }

    public Integer getDirlevel() {
        return dirlevel;
    }

    public void setDirlevel(Integer dirlevel) {
        this.dirlevel = dirlevel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
}