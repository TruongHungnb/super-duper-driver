package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILE WHERE filename = #{fileName}")
    File getFile(String fileName);

    @Select("SELECT filename FROM FILE WHERE userid = #{userId}")
    String[] getFileListings(Integer userId);

    @Insert("INSERT INTO FILE (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILE WHERE filename = #{fileName}")
    void deleteFile(String fileName);
}
