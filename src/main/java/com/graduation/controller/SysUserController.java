package com.graduation.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.common.Constants;
import com.graduation.common.Result;
import com.graduation.controller.dto.UserDTo;
import com.graduation.controller.dto.UserPasswordDTO;
import com.graduation.entity.SysUser;
import com.graduation.service.impl.SysUserServiceImpl;
import com.graduation.utils.SnowflakeIdWorker;
import com.graduation.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/user")
//为所有的查询地址添加统一的前缀
public class SysUserController {
    @Autowired
    SysUserServiceImpl userService;

    //    生成唯一id
    SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

    @PostMapping("/pay")
    public Result pay(@RequestBody UserDTo user){
        if(userService.pay(user)) {
            return Result.success();
        }else {
            return Result.error(Constants.CODE_500,"支付失败，请稍后再试");
        }
    }

    /**
     * 修改密码
     * @param userPasswordDTO
     * @return
     */
    @PostMapping("/password")
    public Result password(@RequestBody UserPasswordDTO userPasswordDTO) {
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }

    @GetMapping("/person/{username}")
    public Result delete(@PathVariable String username) {
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        SysUser user=userService.getOne(queryWrapper);
        return Result.success(user);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDTo user) {

        String username = user.getUsername();
        String password = user.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "填写的参数不可以为空");
        }
        user.setId(idWorker.nextId());
        userService.registers(user);
        return Result.success();
    }

    @GetMapping("/username/{username}")
    public Result findByUsername(@PathVariable String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Result.success(userService.getOne(queryWrapper));
    }


    @PostMapping("/login")
    public Result login(@RequestBody UserDTo user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDTo userDTo = userService.login(user);
        return Result.success(userDTo);
    }

    @GetMapping("/")
    public List<SysUser> index() {
        return userService.list();
    }

    @PostMapping("/insert")
    public boolean saveUser(@RequestBody SysUser user) {

//        System.out.println("======================================"+user.toString());
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }
        if (user.getId()==null) {
            user.setId(idWorker.nextId());
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());

        if (userService.getOne(wrapper) != null && user.getId() == null) {
            return false;
        }
        return userService.saveUser(user);
    }

    @DeleteMapping("/del/{id}")
    public boolean delete(@PathVariable Long id) {
        return userService.removeById(id);
    }

    @PostMapping("/batchdel")
    public boolean deleteBatch(@RequestBody List<Long> ids) {
        return userService.removeBatchByIds(ids);
    }


    //    分页查询 普通方式
    @GetMapping("/pages")
    public IPage<SysUser> findPages(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(required = false) String username,
                                    @RequestParam(required = false) String email,
                                    @RequestParam(required = false) Integer roleid
    ) {
        IPage<SysUser> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();

        if (username != null) {
            queryWrapper.like("username", username);
        }
        if (email != null) {
            queryWrapper.eq("email", email);
        }
        if (roleid != null) {
            queryWrapper.eq("roleid", roleid);
        }
        IPage<SysUser> userIPage = userService.page(page, queryWrapper);
        System.out.println(userIPage);

        //        获取当前用户名称
        SysUser currentUser = TokenUtils.getCurrentUser();
        System.out.println("当前用户信息为==============================="+currentUser.getId());


//        queryWrapper.orderByDesc("id");
        return userIPage;
    }

    /*
    * System.out.println(pageNum);
        System.out.println(pageSize);
        Map<String,Object> res=new HashMap<>();
        pageNum=(pageNum-1)*pageSize;
        System.out.println("改后page的值"+pageNum+"页面数据大小"+pageSize);
        username="%"+username+"%";
        System.out.println("改后page的值"+pageNum+"页面数据大小"+pageSize+"username="+username);
        List<SysUser> sysUsers =userMapper.selectPages(pageNum,pageSize,username,email,roleid);
        Integer count=userMapper.selectTotal(username,email,roleid);
        res.put("data",sysUsers);
        res.put("total",count);*/

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<SysUser> list = userService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id", "标号");
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("roleid", "角色");
        writer.addHeaderAlias("money", "账户金额");
        writer.addHeaderAlias("avatarUrl", "头像");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

/*    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<SysUser> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            SysUser user = new SysUser();
            user.setUsername(row.get(1).toString());
            user.setPassword("123456");
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setCreateTime(row.get(6).toString());
            user.setRoleid((Integer) row.get(7));
            user.setMoney((Double) row.get(8));
            user.setAvatarUrl(row.get(9).toString());
            users.add(user);
        }

        userService.saveBatch(users);
        return true;
    }*/
}
