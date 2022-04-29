package com.cloudinwind.selfmanage.controller.analysis;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudinwind.selfmanage.constant.PageConstant;
import com.cloudinwind.selfmanage.controller.time.BaseController;
import com.cloudinwind.selfmanage.entity.analysis.Mbquestion;
import com.cloudinwind.selfmanage.entity.time.ReturnBean;
import com.cloudinwind.selfmanage.service.analysis.MbquestionService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问题表(Question)表控制层
 *
 * @author makejava
 * @since 2021-12-03 09:32:43
 */
@RestController
@RequestMapping("/analysis/mbquestion")
public class MbQuestionController extends BaseController {
    @Resource
    MbquestionService mbquestionService;

    // 问题页面 分页查询
    @RequestMapping("selectByPage")
    public ReturnBean<List<Mbquestion>> selectByPage(Integer page, Integer limit, Mbquestion question) {

        if (page == null){
            page = PageConstant.page;
            limit=PageConstant.limit;
        }
        Page<Mbquestion> questionPage = new Page<>(page, limit);

        QueryWrapper<Mbquestion> queryWrapper = new QueryWrapper<>();

        if (Strings.isNotEmpty(question.getQuestion())){
            queryWrapper.like("question", question.getQuestion());
        }

//        queryWrapper.eq("flag", 0);

        Page<Mbquestion> ques = mbquestionService.page(questionPage, queryWrapper);

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
        Mbquestion question = mbquestionService.getById(id);
        return success(question);
    }

    /**
     * 根据type查询
     */
    @GetMapping("selectByType")
    public ReturnBean selectByType(@RequestParam("type") Integer type){
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        List<Mbquestion> questions = mbquestionService.listByMap(map);
        if (questions.size()>0) return success(questions);
        else return fail(questions);
    }


    /**
     * 插入数据
     * @param question
     * @return
     */
    @PostMapping("insert")
    public ReturnBean insert(@RequestBody Mbquestion question) {

        if (question.getStatus() == null) question.setStatus(1);
        else question.setStatus(0);
        boolean save = mbquestionService.save(question);
        if (save) return super.success(question);
        return super.fail(question);
    }


    /**
     * 修改数据
     *
     * @param question 实体对象
     * @return 修改结果
     */
    @PutMapping("update")
    public ReturnBean update(@RequestBody Mbquestion question) {

        if (question.getStatus() == null) question.setStatus(1);
        else question.setStatus(0);
        boolean update = mbquestionService.updateById(question);
        if (update) return super.success(question);
        else return super.fail(question);
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

        boolean delete = mbquestionService.removeByIds(idList);
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
        boolean delete = mbquestionService.removeById(id);
        if (delete) return success(mbquestionService.getById(id));
        return fail(mbquestionService.getById(id));
    }

}

