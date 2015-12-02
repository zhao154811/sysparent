package com.enlinkmob.ucenterapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.enlinkmob.ucenterapi.Enum.AccountStatus;
import com.enlinkmob.ucenterapi.Enum.StatusEnum;
import com.enlinkmob.ucenterapi.dao.OauthClientDetailMapper;
import com.enlinkmob.ucenterapi.exception.DataExistException;
import com.enlinkmob.ucenterapi.exception.DataNotFoundException;
import com.enlinkmob.ucenterapi.exception.ParamException;
import com.enlinkmob.ucenterapi.exception.ResponseException;
import com.enlinkmob.ucenterapi.image.ImageCut;
import com.enlinkmob.ucenterapi.model.*;
import com.enlinkmob.ucenterapi.service.CustomerUserService;
import com.enlinkmob.ucenterapi.service.NewUserService;
import com.enlinkmob.ucenterapi.service.RoleService;
import com.enlinkmob.ucenterapi.service.UserService;
import com.enlinkmob.ucenterapi.util.HttpUtils;
import com.enlinkmob.ucenterapi.util.JsonFilterInject;
import com.enlinkmob.ucenterapi.util.MySimplePropertyPreFilter;
import com.enlinkmob.ucenterapi.util.RSA.RSAUtils;
import com.enlinkmob.ucenterapi.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Zhaowy on 2015/2/2.
 */
@Service(value = "newUserService")
public class NewUserServiceImpl implements NewUserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OauthClientDetailMapper oauthClientDetailMapper;
    @Autowired
    private CustomerUserService customerUserServiceImpl;
    @Autowired
    private Map<String, ImageCut> imageCutImpl;
    @Autowired
    private HttpUtils httpUtils;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private SimpleDateFormat birthsdf = new SimpleDateFormat("yyyyMMdd");


    /**
     * 根据用户名获取用户资料
     *
     * @param client_id client_id
     * @return UserInfo json格式
     */
    @Override
    public ResultMessage customerLogin(String client_id, CustomerUserInfo customerUserInfo) throws ResponseException, UnsupportedEncodingException {
        if (client_id == null || client_id.length() == 0) {
            throw new ParamException("need client_id", "client_id is null or empty");
        }

        String nickName = "";
        if (customerUserInfo == null || customerUserInfo.getSourceApp() == null || customerUserInfo.getSourceApp().trim().length() == 0) {
            throw new ParamException("source error", "source error");
        }
        try {
            nickName = this.parseJson(customerUserInfo);
        } catch (Exception e) {
            throw new ParamException("json parse exception", "数据解析错误!");
        }
        //处理InfoJson
        CustomerUserInfo cui = customerUserServiceImpl.getCustomerUserInfoById(customerUserInfo.getAppUniqueId(), customerUserInfo.getSourceApp());
        if (cui != null) {
            //登录
            if (customerUserInfo.getSourceApp().equals("wechat")) {
                //微信特殊处理，因为有多个openid
                if (customerUserInfo.getOpenIds() != null && !cui.getOpenIds().containsKey(customerUserInfo.getOpenIds().keySet().toArray()[0])) {
                    //页面授权登录请求处理
                    cui.getOpenIds().putAll(customerUserInfo.getOpenIds());
                    this.customerUserServiceImpl.updateCustomerUserInfo(cui);
                } else if (customerUserInfo.getOpenIds() != null && cui.getOpenIds().containsKey(customerUserInfo.getOpenIds().keySet().toArray()[0]) && !customerUserInfo.getOpenIds().containsValue("-1")) {
                    //微信公众号管理系统请求处理
                    cui.getOpenIds().putAll(customerUserInfo.getOpenIds());
                    this.customerUserServiceImpl.updateCustomerUserInfo(cui);
                }
            }
            //获取第三方账号的关联账号
            User User = cui.getUser();

            //登录
//			String loginScript = ucClient.uc_user_synlogin(User.getUid());


            ResultMessage rm = new ResultMessage();
            Map<String, Object> jmap = new HashMap<>();
            jmap.put("userObjId", User.getId());
            User userInfo = this.getUserInfoString(User.getId());
            jmap.put("userInfo", userInfo);
            rm.setStatus("200");
            rm.setMessage("用户登录成功!");
            rm.setJsonResult(jmap);
            JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "realName", "nickName", "phoneNum", "email", "address", "sex", "uid", "objId", "headIcon"));
            return rm;
        } else {
            //注册
            User User = new User();

            OAuthClientDetails client = this.oauthClientDetailMapper
                    .getByClientId(client_id);
            if (client == null) {
                throw new DataNotFoundException("client is not exist", "can`t find client by client_id");
            }

            List<OauthAuthorities> roles = new ArrayList<>();
            String authorities = client.getAuthorities();
            if (authorities != null && authorities.length() != 0) {
                String[] rolenames = authorities.split(",");
                for (String roleName : rolenames) {
//                    roles.add(roleService.getRole(roleName));
                }
            }
            String pwd = StringUtils.generateShortUuid();
            User.setClientId(client.getClientId());
            User.setAuthorities(roles);
            User.setCreateTime(new Date());
            User.setStatus(StatusEnum.ENABLE);
            User.setPassword(StringUtils.hash(pwd, "md5"));
            User.setUserName(customerUserInfo.getSourceApp() + "_" + UUID.randomUUID());
            User.setAccountStatus(AccountStatus.ACTIVE);
//			String uid = ucClient.uc_user_register(User.getUserName(), pwd, User.getUserName() + "@null.null");
//			User.setUid(Integer.parseInt(uid));
            Long userobjid = this.userService.addUser(User);
            User.setId(userobjid);
            customerUserInfo.setStatus(StatusEnum.ENABLE);
            customerUserInfo.setCreateTime(new Date());
            customerUserInfo.setUser(User);
//			if(customerUserInfo.getOpenIds()!=null&&customerUserInfo.getOpenIds().size()!=0){
//				Map<String,List<String>> openIds=new HashMap<>();
//				List<String> openIdList=new ArrayList<>();
//				openIdList.add(customerUserInfo.getOpenIds().get(0));
//				customerUserInfo.setOpenIds(openIdList);
//			}
            Long objectId = null;
            try {
                objectId = this.customerUserServiceImpl.addCustomerUserInfo(customerUserInfo);
            } catch (Exception e) {
                this.userService.deleteUser(userobjid);
                throw new ResponseException(e.getMessage(), e.getMessage());
            }

//			String loginScript = ucClient.uc_user_synlogin(User.getUid());
            ResultMessage rm = new ResultMessage();
            if (objectId != null) {
                Map<String, Object> jmap = new HashMap<>();
                jmap.put("userObjId", userobjid.toString());
                User userInfo = this.getUserInfoString(userobjid);
                jmap.put("userInfo", userInfo);
                rm.setStatus("200");
                rm.setMessage("用户注册成功!");
                rm.setJsonResult(jmap);
                JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "realName", "nickName", "phoneNum", "email", "address", "sex", "uid", "objId", "headIcon"));
            } else {
                rm.setStatus("201");
                rm.setMessage("用户注册失败");
            }
            return rm;
        }
    }

    /**
     * 用户注册
     *
     * @param user      各种用户信息
     * @param client_id client的识别id
     */
    @Override
    public ResultMessage userRegist(User user, String client_id, String birth) throws UnsupportedEncodingException, ParseException {
        if (client_id == null || client_id.length() == 0) {
            throw new ParamException("need client_id", "client_id is null or empty");
        }
        if (user == null || user.getUserName() == null || user.getUserName().length() == 0 || user.getPassword() == null || user.getPassword().length() == 0) {
            throw new ParamException("paramer error", "用户名或密码为空!");
        }
        User User = this.userService.getUser(user.getUserName());
        if (User != null) {
            DataExistException ex = new DataExistException("userName is already registed", "该用户名已被使用！");
            Map<String, Object> jmap = new HashMap<String, Object>();
            jmap.put("userInfo", User);
            ex.setExtendJson(jmap);
            JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "userName", "realName", "nickName", "phoneNum", "email", "address", "sex", "uid", "objId", "headIcon"));
            throw ex;
        }
        OAuthClientDetails client = this.oauthClientDetailMapper
                .getByClientId(client_id);
        if (client == null) {
            throw new DataNotFoundException("client is not exist", "can`t find client by client_id");
        }

        List<Authority> roles = new ArrayList<>();
        String authorities = client.getAuthorities();
        if (authorities != null && authorities.length() != 0) {
            String[] rolenames = authorities.split(",");
            for (String roleName : rolenames) {
                roles.add(roleService.getRole(roleName));
            }
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(user.getPhoneNum())) {
            user.setPhoneNum(user.getUserName());
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(birth)) {
            Date birthdate = birthsdf.parse(birth);
            user.setBirthday(birthdate);
        }
        user.setClientId(client.getClientId());
//        user.setAuthorities(roles);
//		String pwd = user.getPassword();
        user.setPassword(StringUtils.hash(user.getPassword(), "md5"));// md5
        user.setCreateTime(new Date());
        user.setStatus(StatusEnum.ENABLE);
        user.setAccountStatus(AccountStatus.ACTIVE);

//		String uid = ucClient.uc_user_register(user.getExtendUserNames().getPhoneNum(), pwd, user.getExtendUserNames().getPhoneNum() + "@null.null");
        Long objid = null;

        objid = this.userService.addUser(user);


        ResultMessage rm = new ResultMessage();
        if (objid != null) {
            Map<String, Object> jmap = new HashMap<>();
            User userInfo = this.getUserInfoString(objid);
            Date current = new Date();
            String userSign = DigestUtils.md5Hex(userInfo.getUserName() + userInfo.getPassword() + sdf.format(current)).toUpperCase();
            redisTemplate.opsForValue().set("UCENTER:USERSIGN:" + userInfo.getId(), userSign);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.HOUR_OF_DAY, 8);
            redisTemplate.expireAt("UCENTER:USERSIGN:" + userInfo.getId(), calendar.getTime());
            jmap.put("userInfo", userInfo);
            jmap.put("userSign", userSign);
            jmap.put("expireAt", sdf.format(calendar.getTime()));
            rm.setStatus("200");
            rm.setMessage("用户注册成功!");
            rm.setJsonResult(jmap);
            JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "userName", "realName", "nickName", "phoneNum", "email", "address", "sex", "uid", "objId", "headIcon"));
        } else {

            rm.setStatus("201");
            rm.setMessage("用户注册失败");
        }

        return rm;
    }


    /**
     * 校验用户名是否存在
     *
     * @param user 各种用户信息
     */
    @Override
    public ResultMessage checkUserName(User user) {
        if (user == null || org.apache.commons.lang3.StringUtils.isEmpty(user.getUserName())) {
            throw new ParamException("userName is null", "用户名为空");
        }
        User User = this.userService.getUser(user.getUserName());
        ResultMessage rm = new ResultMessage();
        if (User != null) {
            rm.setStatus("201");
            rm.setMessage("用户名已注册");
        } else {
            rm.setStatus("200");
            rm.setMessage("用户名可使用");
        }


        return rm;
    }

    /**
     * 普通用户登录
     *
     * @param user      用户信息
     * @param client_id 来源端id
     */
    @Override
    public ResultMessage login(User user, String client_id) throws UnsupportedEncodingException {
        if (client_id == null || client_id.length() == 0) {
            throw new ParamException("need client_id", "client_id is null or empty");
        }
        if (user == null || user.getUserName() == null || user.getUserName().length() == 0 || user.getPassword() == null || user.getPassword().length() == 0) {
            throw new ParamException("paramer error", "用户名或密码为空！");
        }
        User User = this.userService.getUser(user.getUserName());
        if (User == null) {
            throw new DataExistException("userName can not found", "没有发现该用户！");
        }

        if (!User.getPassword().equals(StringUtils.hash(user.getPassword(), "md5"))) {
            throw new DataNotFoundException("password is not correct", "密码错误！");
        } else {
//            if (User.getAccountStatus() == -1) {
//                //账号被绑定至其他账号，切换到绑定账号
//                User = this.userService.getUserByBind(User.getObjId());
//            }
            Date current = new Date();
            String userSign = DigestUtils.md5Hex(User.getUserName() + User.getPassword() + sdf.format(current)).toUpperCase();
            redisTemplate.opsForValue().set("UCENTER:USERSIGN:" + User.getId(), userSign);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.HOUR_OF_DAY, 8);
            redisTemplate.expireAt("UCENTER:USERSIGN:" + User.getId(), calendar.getTime());
            ResultMessage rm = new ResultMessage();
            Map<String, Object> jmap = new HashMap<String, Object>();
            User userInfo = this.getUserInfoString(User.getId());
            jmap.put("userInfo", userInfo);
            jmap.put("userSign", userSign);
            jmap.put("expireAt", sdf.format(calendar.getTime()));
            rm.setStatus("200");
            rm.setMessage("用户登录成功!");
            rm.setJsonResult(jmap);
            JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "userName", "realName", "nickName", "phoneNum", "email", "address", "sex", "uid", "objId", "headIcon"));
            return rm;
        }
    }


    @Override
    public ResultMessage cancelSubcribe(String openId) {
        int result = this.customerUserServiceImpl.cancelSubcribe(openId);
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setStatus("success");
        resultMessage.setMessage("取消关注成功");
        return resultMessage;
    }

    @Override
    public ResultMessage uploadHeadIcon(Long userId, MultipartFile filedatas, String serverPath, String storeChannel) throws Exception {
        User user = this.userService.getUserById(userId);
        if (user == null) {
            throw new DataNotFoundException("user not found", "用户不存在！");
        }
        if (filedatas.isEmpty()) {
            throw new DataNotFoundException("file not found", "图片文件不存在！");
        } else {
            ImageCut imgCut = imageCutImpl.get(storeChannel);
            if (imgCut == null) {
                throw new ResponseException("can not find this channel", "can not find this channel");
            } else {
                String filename = filedatas.getOriginalFilename();
                String extendName = filename.substring(filename.indexOf("."), filename.length());
                File destFile = new File(serverPath + File.separator + "upload_temp" + File.separator + user.getId() + extendName);
//					File destFile=File.createTempFile(user.getObjId(),extendName,new File(serverPath+ File.separator+"upload_temp"+File.separator));
//					destFile.deleteOnExit();
                FileUtils.copyInputStreamToFile(filedatas.getInputStream(), destFile);
                Map<String, String> pathMap = new HashMap<>();
                pathMap.put("headicon", destFile.getPath());
                Map<String, String> headMap = imgCut.imgStore(pathMap, serverPath, true);
                user.setHeadIcon(headMap.get("small"));
                userService.updateUserInfo(user);
                boolean sss = FileUtils.deleteQuietly(destFile);
                System.out.println(sss);
            }
        }
        ResultMessage rm = new ResultMessage();
        rm.setStatus("200");
        rm.setMessage("modify headIcon success");
        Map<String, Object> jmap = new HashMap<String, Object>();
        jmap.put("headIcon", user.getHeadIcon());
        rm.setJsonResult(jmap);
        return rm;
    }

    /**
     * 第三方用户资料处理
     */
    private void checkCustomerUser(CustomerUserInfo customerUserInfo, JSONObject jb) {
        if (customerUserInfo == null || customerUserInfo.getSourceApp() == null || customerUserInfo.getSourceApp().trim().length() == 0) {
            throw new ParamException("can not find source", "can not find source");
        }
        switch (customerUserInfo.getSourceApp().trim()) {
            case "wechat":
                if (jb.get("unionid") != null) {
                    customerUserInfo.setAppUniqueId((String) jb.get("unionid"));
                } else {
                    throw new ParamException("can not find unionId", "无法解析unionId");
                }
                if (jb.getString("openid") != null) {
                    if (customerUserInfo.getOpenIds() == null) {
                        Map<String, String> openIds = new HashMap<>();
                        openIds.put((String) jb.get("openid"), jb.get("subscribe") != null ? jb.get("subscribe") + "" : "-1");
                        customerUserInfo.setOpenIds(openIds);
                    }
                } else {
                    throw new ParamException("can not find openId", "无法解析openId");
                }

                break;
            default:
                throw new ParamException("what is your source", "what is your source");
        }

    }


    private User getUserInfoString(Long objId) {
        User user = this.userService.getUserById(objId);
        if (user == null) {
            throw new DataNotFoundException("can not find data", "找不到该用户！");
        }
        return user;
    }

    public ResultMessage getUserInfo(Long objId, String userSign, String client_id) {
        ResultMessage rs = null;
        switch (client_id) {
            case "auto-china":
                rs = this.getUserInfobyUser(objId, userSign);
                break;
            case "enlink_activity_manage":
                rs = this.getUserInfobyManage(objId);
                break;
            default:
                rs = new ResultMessage();
                rs.setStatus("201");
                rs.setMessage("找不到对应的处理分支");
                rs.setMessage(null);
        }
        return rs;
    }

    @Override
    public ResultMessage updateUserInfo(Long objId, String json, String userSign, String client_id, String birth) throws Exception {
        ResultMessage rs = null;
        switch (client_id) {
            case "auto-china":
                rs = this.updateUserInfobyUser(objId, json, userSign);
                break;
            case "enlink_activity_manage":
                rs = this.updateUserInfobyManage(objId, json);
                break;
            default:
                rs = new ResultMessage();
                rs.setStatus("201");
                rs.setMessage("找不到对应的处理分支");
                rs.setMessage(null);
        }
        return rs;
    }

    @Override
    public ResultMessage updateUserPwd(User user) throws Exception {
        if (org.apache.commons.lang3.StringUtils.isEmpty(user.getUserName())) {
            throw new ParamException("userName is null", "用户名为空");
        }
        if (user.getPassword() == null || user.getPassword().trim().length() == 0) {
            throw new ParamException("password is null", "密码为空");
        }
        String field = null;

        String loginName = user.getUserName();

        User newUser = this.userService.getUser(loginName);
        if (newUser == null) {
            throw new DataNotFoundException("user is not found", "该用户不存在");
        }

        Map<String, Object> newProp = new HashMap<>();
        newProp.put("password", StringUtils.hash(user.getPassword(), "MD5"));
        newProp.put("modifyTime", new Date());
        int result = this.userService.updateUserInfo(user);
        ResultMessage rm = new ResultMessage();
        if (result != 0) {
            User muser = this.userService.getUserById(newUser.getId());
            Map<String, Object> jmap = new HashMap<>();
            jmap.put("userInfo", muser);
            rm.setStatus("200");
            rm.setMessage("更新成功");
            rm.setJsonResult(jmap);
            JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "userName", "realName", "nickName", "phoneNum", "email", "address", "sex", "objId", "headIcon"));
        } else {
            rm.setStatus("199");
            rm.setMessage("更新失败");
            rm.setJsonResult(null);
        }
        return rm;
    }

    @Override
    public ResultMessage getUserByCustomerId(String appId, String sourceApp) {
        CustomerUserInfo cu = this.customerUserServiceImpl.getCustomerUserInfoById(appId, sourceApp);
        if (cu == null) {
            throw new DataNotFoundException("can't find user", "找不到该用户！");
        }
        ResultMessage rm = new ResultMessage();
        rm.setStatus("success");
        rm.setMessage("查询成功!");
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("userid", cu.getUser().getId());
        rm.setJsonResult(jsonMap);
        return rm;
    }


    /**
     * 客户端使用，主要用于获取同步登录url
     *
     * @param userId    用户ID
     * @param client_id 来源端id
     */
    @Override
    public ResultMessage loginById(Long userId, String client_id) throws UnsupportedEncodingException {
        if (client_id == null || client_id.length() == 0) {
            throw new ParamException("need client_id", "client_id is null or empty");
        }
        User User = this.userService.getUserById(userId);
        if (User == null) {
            throw new DataExistException("userName can not found", "没有发现该用户！");
        }
//        if (User.getAccountStatus() == -1) {
//            //账号被绑定至其他账号，切换到绑定账号
//            User = this.userService.getUserByBind(User.getObjId());
//        }

        ResultMessage rm = new ResultMessage();
        Map<String, Object> jmap = new HashMap<String, Object>();
        User userInfo = this.getUserInfoString(User.getId());
        jmap.put("userObjId", User.getId());
        jmap.put("userInfo", userInfo);
        rm.setStatus("success");
        rm.setMessage("用户登录成功!");
        rm.setJsonResult(jmap);
        JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "realName", "nickName", "phoneNum", "email", "address", "sex", "uid", "objId", "headIcon"));
        return rm;

    }

    @Override
    public ResultMessage sendVerifyCode(String phoneNum) throws Exception {
        String strurl = "http://sms.cnautoshows.com/notify/send";

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1IAm4jHm25s49FntNEt+9zrQMWIIlp6wO/ER7tbWq0gPnhy3H6cDbyAF9KmGsgtauWacJxanlDwYS01yA2/CNzoyvMtsCTskPoLlXzNTWMnUuBsquBlkx58Y4Psi+gyz/09iDunpbk763zl56lFF3K+Y2WCUJ1u3iuLA4whqnXQIDAQAB";
        String verifycode = ((int) ((Math.random() * 9 + 1) * 100000)) + "";
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("client_id", "notify"));
        String message = new String("[{\"phone\":\"" + phoneNum + "\",template_param:{\"param1\":\"" + verifycode + "\"},template:\"true\",template_id:\"91004029\"}]");

        nvps.add(new BasicNameValuePair("data", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(message.getBytes("UTF-8"), publicKey))));
        byte[] b = httpUtils.post(strurl, new UrlEncodedFormEntity(nvps, "UTF-8"));
        if (b != null && b.length != 0) {
            String response = new String(b);
            JSONObject jb = JSONObject.parseObject(response);
            String rescode = (String) jb.get("res_code");
            ResultMessage rm = new ResultMessage();
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(rescode) && rescode.equals("0")) {
                Map<String, Object> jmap = new HashMap<>();
                jmap.put("verifyCode", verifycode);
                rm.setStatus("200");
                rm.setMessage("发送成功");
                rm.setJsonResult(jmap);
            } else {
                rm.setStatus("201");
                rm.setMessage("发送失败");
                rm.setJsonResult(null);
            }


            return rm;
        } else {
            throw new ResponseException("sms send error", "发送短信失败");
        }

    }


    private String parseJson(CustomerUserInfo customerUserInfo) throws Exception {
        String returnvalue = null;
        switch (customerUserInfo.getSourceApp()) {
            case "wechat":
                returnvalue = this.weichatParse(customerUserInfo);
                break;
            default:
                break;
        }
        return returnvalue;
    }


    private String weichatParse(CustomerUserInfo customerUserInfo) {
        JSONObject jb = null;
        if (customerUserInfo.getInfoJson() == null || customerUserInfo.getInfoJson().trim().length() == 0) {
            throw new ParamException("need infoJson", "nefoJson");
        } else {
            try {
                jb = JSONObject.parseObject(customerUserInfo.getInfoJson());
            } catch (Exception e) {
                throw new ParamException("parse exception", "parse exception");
            }
        }
        if (jb.get("unionid") != null) {
            customerUserInfo.setAppUniqueId((String) jb.get("unionid"));
        } else {
            throw new ParamException("can not find unionId", "can not find unionId");
        }
        if (jb.getString("openid") != null) {
            if (customerUserInfo.getOpenIds() == null) {
                Map<String, String> openIds = new HashMap<>();
                openIds.put((String) jb.get("openid"), jb.get("subscribe") != null ? jb.get("subscribe") + "" : "-1");
                customerUserInfo.setOpenIds(openIds);
            }
        } else {
            throw new ParamException("can not find openId", "can not find openId");
        }

        return (String) jb.get("nickname");
    }


    private ResultMessage updateUserInfobyUser(Long objId, String json, String userSign) throws Exception {
        if (objId == 0) {
            throw new ParamException("用户Id为空", "用户Id为空");
        }
        String cacheSign = this.redisTemplate.opsForValue().get("UCENTER:USERSIGN:" + objId);
        ResultMessage rm = new ResultMessage();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(cacheSign) && org.apache.commons.lang3.StringUtils.isNotEmpty(userSign) && cacheSign.equalsIgnoreCase(userSign)) {
            User user = this.userService.getUserById(objId);
            if (user == null) {
                throw new DataNotFoundException("can not find User", "找不到该用户！");
            }
            if (org.apache.commons.lang3.StringUtils.isEmpty(json)) {
                throw new ParamException("json is null", "json数据为空");
            }
            Map<String, Object> propMap = null;
            try {
                propMap = (Map<String, Object>) JSON.parse(json);
            } catch (Exception e) {
                throw new ResponseException("json error", "json解析错误！");
            }
            if (propMap != null && propMap.get("password") != null) {
                String pwd = (String) propMap.get("password");
                propMap.remove("password");
                propMap.remove("userName");
                propMap.put("password", StringUtils.hash(pwd, "md5"));
            }

            if (propMap != null && propMap.get("birth") != null) {
                String birth = (String) propMap.get("birth");
                propMap.remove("birth");
                Date birthdate = birthsdf.parse(birth);
                user.setBirthday(birthdate);
                propMap.put("birthday", birthdate);
            }

            this.userService.updateUserInfo(user);

            user = this.userService.getUserById(objId);
            Map<String, Object> jmap = new HashMap<>();
            jmap.put("userInfo", user);
            rm.setStatus("200");
            rm.setMessage("更新成功");
            rm.setJsonResult(jmap);
            JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "userName", "realName", "nickName", "phoneNum", "email", "address", "sex", "objId", "headIcon"));
        } else {
            rm.setStatus("199");
            rm.setMessage("用户校验失败，请重新登录");
            rm.setJsonResult(null);
        }


        return rm;
    }


    private ResultMessage updateUserInfobyManage(Long objId, String json) throws Exception {
        if (objId == 0) {
            throw new ParamException("用户Id为空", "用户Id为空");
        }
//		String cacheSign = this.redisTemplate.opsForValue().get("UCENTER:USERSIGN:" + objId);
        ResultMessage rm = new ResultMessage();
//		if (org.apache.commons.lang3.StringUtils.isNotEmpty(cacheSign) && org.apache.commons.lang3.StringUtils.isNotEmpty(userSign) && cacheSign.equalsIgnoreCase(userSign)) {
        User user = this.userService.getUserById(objId);
        if (user == null) {
            throw new DataNotFoundException("can not find User", "找不到该用户！");
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(json)) {
            throw new ParamException("json is null", "json数据为空");
        }
        Map<String, Object> propMap = null;
        try {
            propMap = (Map<String, Object>) JSON.parse(json);
        } catch (Exception e) {
            throw new ResponseException("json error", "json解析错误！");
        }
//			Map<String, Object> newProp = new HashMap<>();
//
//			for(Map.Entry<String,Object> entry:propMap.entrySet()){
//				newPro
//			}

//			if (propMap != null && propMap.get("nickName") != null) {
//				newProp.put("nickName", propMap.get("nickName"));
//			}
        if (propMap != null && propMap.get("password") != null) {
            String pwd = (String) propMap.get("password");
            propMap.remove("password");
            propMap.remove("userName");
            propMap.put("password", StringUtils.hash(pwd, "md5"));
        }

        if (propMap != null && propMap.get("birth") != null) {
            String birth = (String) propMap.get("birth");
            propMap.remove("birth");
            Date birthdate = birthsdf.parse(birth);
            user.setBirthday(birthdate);
            propMap.put("birthday", birthdate);
        }

        this.userService.updateUserInfo(user);
        user = this.userService.getUserById(objId);
        Map<String, Object> jmap = new HashMap<>();
        jmap.put("userInfo", user);
        rm.setStatus("200");
        rm.setMessage("更新成功");
        rm.setJsonResult(jmap);
        JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "userName", "realName", "nickName", "phoneNum", "email", "address", "sex", "objId", "headIcon"));
//		} else {
//			rm.setStatus("199");
//			rm.setMessage("用户校验失败，请重新登录");
//			rm.setJsonResult(null);
//		}


        return rm;
    }

    private ResultMessage getUserInfobyUser(Long objId, String userSign) {
        if (objId == 0) {
            throw new ParamException("用户Id为空", "用户Id为空");
        }
        String cacheSign = this.redisTemplate.opsForValue().get("UCENTER:USERSIGN:" + objId);
        ResultMessage rm = new ResultMessage();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(cacheSign) && org.apache.commons.lang3.StringUtils.isNotEmpty(userSign) && cacheSign.equalsIgnoreCase(userSign)) {
            Map<String, Object> jmap = new HashMap<>();
            User user = this.getUserInfoString(objId);
            jmap.put("userInfo", user);
            rm.setStatus("200");
            rm.setMessage("查询成功");
            rm.setJsonResult(jmap);
            JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "userName", "realName", "nickName", "phoneNum", "email", "address", "sex", "objId", "headIcon"));
        } else {
            rm.setStatus("199");
            rm.setMessage("用户校验失败，请重新登录");
            rm.setJsonResult(null);
        }
        return rm;
    }

    private ResultMessage getUserInfobyManage(Long objId) {
        if (objId == 0) {
            throw new ParamException("用户Id为空", "用户Id为空");
        }
        ResultMessage rm = new ResultMessage();
        Map<String, Object> jmap = new HashMap<>();
        User user = this.getUserInfoString(objId);
        jmap.put("userInfo", user);
        rm.setStatus("200");
        rm.setMessage("查询成功");
        rm.setJsonResult(jmap);
        JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "userName", "realName", "nickName", "phoneNum", "email", "address", "sex", "objId", "headIcon"));
        return rm;
    }
}
