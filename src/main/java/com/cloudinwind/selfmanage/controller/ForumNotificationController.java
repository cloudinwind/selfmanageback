package com.cloudinwind.selfmanage.controller;

import com.cloudinwind.selfmanage.annotation.UserLoginToken;
import com.cloudinwind.selfmanage.dto.NotificationDTO;
import com.cloudinwind.selfmanage.dto.ResultDTO;
import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.enums.NotificationTypeEnum;
import com.cloudinwind.selfmanage.exception.CustomizeErrorCode;
import com.cloudinwind.selfmanage.exception.CustomizeException;
import com.cloudinwind.selfmanage.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
public class ForumNotificationController {

    @Autowired
    private NotificationService notificationService;

    @UserLoginToken
    @ResponseBody
    @RequestMapping(value = "/api/notification/mine", method = RequestMethod.GET)
    public ResultDTO questionList(
            HttpServletRequest request
    ) {
        UserDTO user = (UserDTO) request.getAttribute("loginUser");
      //  if(user==null) return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        Long unreadCount = notificationService.unreadCount(user.getId());
        return ResultDTO.okOf(unreadCount);
    }


    @UserLoginToken
    @GetMapping("/notification/{id}")
    public String readNotification(HttpServletRequest request,
                                   @PathVariable(name = "id") Long id) {

        UserDTO user = (UserDTO) request.getAttribute("loginUser");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_SUB_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()
                || NotificationTypeEnum.LIKE_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.LIKE_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/p/" + notificationDTO.getOuterid();
        } else if(NotificationTypeEnum.LIKE_SUB_COMMENT.getType() == notificationDTO.getType()){
            return "redirect:/comment/" + notificationDTO.getOuterid();
        }else if(NotificationTypeEnum.REPLY_TALK.getType() == notificationDTO.getType()|| NotificationTypeEnum.LIKE_TALK.getType() == notificationDTO.getType()|| NotificationTypeEnum.REPLY_TALK_COMMENT.getType() == notificationDTO.getType()|| NotificationTypeEnum.LIKE_TALK_COMMENT.getType() == notificationDTO.getType()){
            return "redirect:/t/" + notificationDTO.getOuterid();
        }else {
            return "redirect:/forum";
        }
    }


    @PostMapping("/notification/removeAll")
    @ResponseBody
    public Map<String,Object> removeAllNotifications(HttpServletRequest request,
                                                     @RequestParam(name = "all",defaultValue = "0") Integer all) {

        UserDTO user = (UserDTO) request.getAttribute("loginUser");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        Map<String,Object> map  = new HashMap<>();
        if(all==null||all!=1) {
            map.put("code",500);
            map.put("msg","????????????????????????");
        }
        else if(all==1){
            int c = notificationService.removeAllByUserId(user.getId());
            if(c==0) {
                map.put("code",500);
                map.put("msg","??????????????????????????????????????????");
            }
            else {
                map.put("code",200);
                map.put("msg","????????????????????????"+c+"????????????");
            }
        }
        return map;
    }

    @PostMapping("/notification/remove/id")
    @ResponseBody
    public Map<String,Object> removeNotificationById(HttpServletRequest request,
                                                     @RequestParam(name = "id",defaultValue = "0") Long id) {

        UserDTO user = (UserDTO) request.getAttribute("loginUser");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        Map<String,Object> map  = new HashMap<>();
        if(id==null||id==0) {
            map.put("code",500);
            map.put("msg","????????????????????????");
        }
        else if(id>0){
            int c = notificationService.removeById(id);
            if(c==0) {
                map.put("code",500);
                map.put("msg","?????????????????????????????????????????????");
            }
            else {
                map.put("code",200);
                map.put("msg","????????????????????????"+c+"????????????");
            }
        }
        return map;
    }


    @PostMapping("/notification/readAll")
    @ResponseBody
    public Map<String,Object> readAllNotifications(HttpServletRequest request,
                                                   @RequestParam(name = "all",defaultValue = "0") Integer all) {

        UserDTO user = (UserDTO) request.getAttribute("loginUser");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        Map<String,Object> map  = new HashMap<>();
        if(all==null||all!=1) {
            map.put("code",500);
            map.put("msg","????????????????????????");
        }
        else if(all==1){
            int c = notificationService.readAllByUserId(user.getId());
            if(c==0){
                map.put("msg","?????????????????????????????????????????????");
                map.put("code",500);
            }
            else{
                map.put("msg","????????????????????????"+c+"????????????");
                map.put("code",200);
            }
        }
        return map;

    }

}

