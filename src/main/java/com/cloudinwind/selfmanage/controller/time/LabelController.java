package com.cloudinwind.selfmanage.controller.time;

import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.time.Label;
import com.cloudinwind.selfmanage.entity.time.LayUiTree;
import com.cloudinwind.selfmanage.entity.time.ReturnBean;
import com.cloudinwind.selfmanage.entity.time.UserLabel;
import com.cloudinwind.selfmanage.service.time.LabelService;
import com.cloudinwind.selfmanage.service.time.UserLabelService;
import com.cloudinwind.selfmanage.util.TreeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Label)表控制层
 *
 * @author makejava
 * @since 2022-03-30 16:47:28
 */
@RestController
@RequestMapping("label")
public class LabelController extends BaseController {

    @Resource
    LabelService labelService;

    @Resource
    UserLabelService userLabelService;

    @GetMapping("selectLabelByUserId")
    public ReturnBean selectLabelByUserId(@RequestParam("userId")Integer userId)
    {
        List<Label> labels = labelService.selectByUserId(userId);
        long count = labels.size();

        return success(labels, count);
    }

    // 根据用户名查询所有标签
    @RequestMapping("queryAllLabelByUserId")
    public ReturnBean queryAllLabelByUserId(HttpServletRequest request)
    {

        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");

        List<Label> labels = labelService.selectByUserId(loginUser.getId().intValue());
        long count = labels.size();

        List<Label> labelsHasChild = TreeUtil.getLabelsHasChild(labels, 0);

        return success(labelsHasChild);
    }

    @PostMapping("insert")
    public ReturnBean insert(HttpServletRequest request,
                             @RequestBody Label label){
        Date date = new Date();
        label.setCreateTime(date);
        label.setUpdateTime(date);

        if (label.getParentId()==null) {
            label.setParentId(0);
        }

        boolean save = labelService.save(label);

        if (save) {
            UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
            UserLabel userLabel = new UserLabel();
            userLabel.setUserId(loginUser.getId().intValue());
            userLabel.setLabelId(label.getLabelId());
            userLabelService.save(userLabel);
            return success(label);
        }else {
            return success(label);
        }
    }
    @PutMapping("update")
    public ReturnBean update(@RequestBody Label label){

        System.out.println(label.toString());
        label.setUpdateTime(new Date());
        boolean b = labelService.updateById(label);
        if (b) {
            return success(label);
        }else {
            return fail(label);
        }
    }


    // 根据标签名删除标签
    @DeleteMapping("deleteLabelById")
    public ReturnBean deleteLabelById(HttpServletRequest request,
                                      @RequestParam("labelId") Integer labelId){
        //判断是否含有子菜单，防止有人绕过浏览器直接请求删除
        System.out.println("label delete:"+labelId);
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        List<Label> labels = labelService.selectByUserId(loginUser.getId().intValue());

        List<Label> labelsHasChild = TreeUtil.getLabelsHasChild(labels, labelId);
        if (labelsHasChild==null||labelsHasChild.size()==0) {
            //删除
            Map<String, Object> map = new HashMap<>();
            map.put("user_id", loginUser.getId().intValue());
            map.put("label_id", labelId);
            userLabelService.removeByMap(map);
            return success(labelId);
        }
        //删除失败
        return fail(labelId, "删除失败,含有子标签");
    }


    @GetMapping("queryLabelTree")
    public ReturnBean queryMenuTree(HttpServletRequest request){
        //查出所有的label及其子标签
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        List<Label> labels = labelService.selectByUserId(loginUser.getId().intValue());
        //将menus改成含有child格式的格式的
        List<LayUiTree> labelsHasChild = TreeUtil.getLabelTreeListFromLabel(labels, 0);
        return success(labelsHasChild);
    }

    @RequestMapping("findParentNameId")
    public ReturnBean findParentNameId(@RequestParam("labelId") Integer labelId){
        Label label = labelService.getById(labelId);
        if (label==null) {
            label = new Label();
            label.setLabelName("没有上级菜单");
        }
        return success(label);
    }
}