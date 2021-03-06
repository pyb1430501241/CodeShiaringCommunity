package com.pdsu.banmeng.manager;

import com.pdsu.banmeng.bo.AuthorBo;
import com.pdsu.banmeng.bo.ChangePasswordBeforeBo;
import com.pdsu.banmeng.bo.FansInformationBo;
import com.pdsu.banmeng.bo.PageTemplateBo;
import com.pdsu.banmeng.context.CurrentUser;
import com.pdsu.banmeng.ibo.ApplyAccountIbo;
import com.pdsu.banmeng.ibo.LikeSearchIbo;
import com.pdsu.banmeng.ibo.UserSearchIbo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 半梦
 * @email 1430501241@qq.com
 * @since 2021-11-24 20:11
 */
public interface IUserManager {

    /**
     * 添加用户
     * @param applyAccountIbo 用户信息
     * @return
     * 是否成功
     */
    boolean applyAccount(ApplyAccountIbo applyAccountIbo);

    /**
     * 获取一个作者的信息
     * @param uid uid
     * @return
     * 作者信息
     */
    AuthorBo getAuthor(Integer uid, CurrentUser currentUser);

    /**
     * 获取特定条件的用户信息
     * @param ibo 条件
     * @return
     */
    PageTemplateBo<FansInformationBo> getFansOrFollow(LikeSearchIbo ibo, CurrentUser currentUser);

    /**
     * 更换用户头像
     * @param file 文件
     * @return
     * 是否
     */
    Boolean updateUserImage(MultipartFile file, CurrentUser currentUser);

    /**
     * 根据条件查看邮箱
     * @param ibo 条件
     * @return
     * 邮箱 加密后的
     */
    ChangePasswordBeforeBo checkEmailBeforeChangePassword(UserSearchIbo ibo);
}
