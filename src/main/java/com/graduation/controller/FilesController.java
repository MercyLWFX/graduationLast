package com.graduation.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.common.Result;
import com.graduation.entity.Files;
import com.graduation.entity.SysUser;
import com.graduation.mapper.FilesMapper;
import com.graduation.utils.SnowflakeIdWorker;
import com.graduation.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FilesController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FilesMapper fileMapper;

    SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

    /**
     * 文件上传接口
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public String upLoad(@RequestParam MultipartFile file) throws IOException {

        /**
         * 文件存储
         */
        String originalFilename = file.getOriginalFilename();
//        获取上传文件的原始文件名
        String type = FileUtil.extName(originalFilename);
//        获取上传文件的文件类型
        long size = file.getSize();
//        文件大小
        String uuid = IdUtil.fastSimpleUUID();
//        定义一个文件的唯一标识码
        String fileUUID = uuid + StrUtil.DOT + type;
//        设置上传新的文件名称
        File uploadFile = new File(fileUploadPath + fileUUID);
//        创建文件夹 存到磁盘
        File ParentFile = new File(fileUploadPath);
//        判断文件是否存在，若不存在则创建一个新的文件目录
        if (!ParentFile.exists()) {
            ParentFile.mkdirs();
        }


        String url;
        String md5 = SecureUtil.md5(file.getInputStream());
//        获取上传文件唯一标识，查看文件是否有重复，节约磁盘空间
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> dbFiles = fileMapper.selectList(queryWrapper);
        if (dbFiles.size() > 0) {
            url = dbFiles.get(0).getUrl();
        } else {
            url = "http://localhost:9090/file/" + fileUUID;
            file.transferTo(uploadFile);
        }

        /**
         * 数据库存储上传文件的信息
         */
        Files saveFiles = new Files();
        saveFiles.setId(idWorker.nextId());
//        随机生成文件id
        saveFiles.setFilename(originalFilename);
//        文件名
        saveFiles.setType(type);
//        文件类型
        saveFiles.setSize(size / 1024);
//        文件大小
        saveFiles.setMd5(md5);
//        设置文件的MD5标识位
        saveFiles.setUrl(url);
//        设置文件的访问路径
        if (TokenUtils.getCurrentUser() != null) {
            SysUser sysUser = TokenUtils.getCurrentUser();
            saveFiles.setUserid(sysUser.getId());
            Long usersId = sysUser.getId();
            saveFiles.setUserid(usersId);
            QueryWrapper<Files> query = new QueryWrapper<>();
            queryWrapper.eq("userid", usersId);
            queryWrapper.eq("md5", md5);
            List<Files> same = fileMapper.selectList(queryWrapper);
//        获得当前上传文件的用户
            if (same.size() == 0) {
                fileMapper.insert(saveFiles);
                return url;
            }
        } else {
            QueryWrapper<Files> q = new QueryWrapper<>();
            Long usersId=null;
            queryWrapper.eq("userid", usersId);
            queryWrapper.eq("md5", md5);
            List<Files> same = fileMapper.selectList(queryWrapper);
            if (same.size() == 0) {
                fileMapper.insert(saveFiles);
                return url;
            }
        }
        return url;
    }


    /**
     * 文件下载接口
     *
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
//        根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        ServletOutputStream os = response.getOutputStream();
//        设置输出流的格式
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {

        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        // 查询未删除的记录
        queryWrapper.eq("delet", 0);
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * 通过文件的md5查询文件
     *
     * @param md5
     * @return
     */
    private Files getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        return Result.success(fileMapper.updateById(files));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        Files files = fileMapper.selectById(id);
        files.setDelet(true);
        fileMapper.updateById(files);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file : files) {
            file.setDelet(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }
}
