package com.cloudinwind.selfmanage.controller.admin.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudinwind.selfmanage.constant.PageConstant;
import com.cloudinwind.selfmanage.controller.time.BaseController;
import com.cloudinwind.selfmanage.entity.admin.AdminAnnounce;
import com.cloudinwind.selfmanage.entity.time.ReturnBean;
import com.cloudinwind.selfmanage.service.admin.AdminAnnounceService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("admin/system/announce")
public class AdminAnnounceController extends BaseController {

    @Resource
    AdminAnnounceService adminAnnounceService;
    
    // 问题页面 分页查询
    @RequestMapping("selectByPage")
    public ReturnBean<List<AdminAnnounce>> selectByPage(Integer page, Integer limit, AdminAnnounce adminAnnounce) {

        if (page == null){
            page = PageConstant.page;
            limit= PageConstant.limit;
        }
        Page<AdminAnnounce> adminAnnouncePage = new Page<>(page, limit);

        QueryWrapper<AdminAnnounce> queryWrapper = new QueryWrapper<>();

        if (Strings.isNotEmpty(adminAnnounce.getTitle())){
            queryWrapper.like("title", adminAnnounce.getTitle());
        }

        Page<AdminAnnounce> ques = adminAnnounceService.page(adminAnnouncePage, queryWrapper);

        return success(ques.getRecords(), ques.getTotal());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ReturnBean selectOne(@PathVariable Serializable id) {
        AdminAnnounce adminAnnounce = adminAnnounceService.getById(id);
        return success(adminAnnounce);
    }

    /**
     * 插入数据
     * @param adminAnnounce
     * @return
     */
    @PostMapping("insert")
    public ReturnBean insert(@RequestBody AdminAnnounce adminAnnounce) {

        adminAnnounce.setCreateTime(new Date());
        adminAnnounce.setUpdateTime(new Date());
        
        boolean save = adminAnnounceService.save(adminAnnounce);
        if (save) return super.success(adminAnnounce);
        return super.fail(adminAnnounce);
    }


    /**
     * 修改数据
     *
     * @param adminAnnounce 实体对象
     * @return 修改结果
     */
    @PutMapping("update")
    public ReturnBean update(@RequestBody AdminAnnounce adminAnnounce) {
        //获取当前登录用户
        //设置修改时间
        adminAnnounce.setUpdateTime(new Date());
        
        boolean update = adminAnnounceService.updateById(adminAnnounce);
        if (update) return super.success(adminAnnounce);
        else return super.fail(adminAnnounce);
    }

    /**
     * 删除数据
     * 根据问题id批量删除问题
     * @param idList 主键结合
     * @return 删除结果
     */
    @RequestMapping("deleteByIdList")
    public ReturnBean delete(@RequestParam(value = "idList[]", required = false) List<Long> idList) {
        System.out.println("idList:"+idList.size());

        boolean delete = adminAnnounceService.removeByIds(idList);
        if (delete) return success(idList);
        else return fail(idList);
    }

    /**
     * 根据问题id删除一个问题
     * @param id
     * @return
     */
    @RequestMapping("deleteById")
    public ReturnBean delete(@RequestParam("id")Integer id) {
        boolean delete = adminAnnounceService.removeById(id);
        if (delete) return success(adminAnnounceService.getById(id));
        return fail(adminAnnounceService.getById(id));
    }
}
