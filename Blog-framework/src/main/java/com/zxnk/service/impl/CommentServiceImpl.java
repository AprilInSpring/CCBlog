package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.dao.Comment;
import com.zxnk.dao.User;
import com.zxnk.entity.CommentVo;
import com.zxnk.entity.PageVo;
import com.zxnk.exception.SystemException;
import com.zxnk.mapper.CommentMapper;
import com.zxnk.mapper.UserMapper;
import com.zxnk.service.CommentService;
import com.zxnk.util.AppHttpCodeEnum;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName CommentServiceImpl
 * @Description 评论接口实现类
 * @Author cc
 * @Date 2023/5/4 20:34
 * @Version 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * @param articleId 博文id
     * @param pageNum   分页页码
     * @param pageSize  每页条数
     * @return: com.zxnk.util.ResponseResult<java.util.List<com.zxnk.entity.CommentVo>>
     * @decription 根据博文id和分页信息，完成博文评论的查询，并对数据进行相应的处理，完成昵称的注入和子评论的添加
     * @date 2023/5/4 22:01
    */
    @Override
    public ResponseResult<List<CommentVo>> commentList(Long articleId, Integer pageNum, Integer pageSize) {
        //1.根据博文id查询相应的根评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        //1.1根id为-1，表示为根评论
        wrapper.eq(Comment::getRootId,-1);
        //查询文章评论
        wrapper.eq(Comment::getType,"0");
        //1.2博文id符合要求
        wrapper.eq(Comment::getArticleId,articleId);
        //1.3按时间顺序排序
        wrapper.orderByDesc(Comment::getCreateTime);
        Page<Comment> commentPage = commentMapper.selectPage(new Page<Comment>(pageNum, pageSize), wrapper);
        //完成属性复制
        List<CommentVo> commentVoList = getCommentVoList(commentPage.getRecords());
        //完成子评论的注入
        for (CommentVo commentVo : commentVoList) {
            getChildren(commentVo);
        }
        return ResponseResult.okResult(new PageVo(commentVoList,commentVoList.stream().count()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_CONTENT);
        }
        commentMapper.insert(comment);
        return ResponseResult.okResult();
    }

    //查询友链评论
    @Override
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        //1.根据博文id查询相应的根评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        //1.1根id为-1，表示为根评论
        wrapper.eq(Comment::getRootId,-1);
        //查询文章评论
        wrapper.eq(Comment::getType,"1");
        //1.3按时间顺序排序
        wrapper.orderByDesc(Comment::getCreateTime);
        //完成分页查询
        Page<Comment> commentPage = commentMapper.selectPage(new Page<Comment>(pageNum, pageSize), wrapper);
        //完成属性复制
        List<CommentVo> commentVoList = getCommentVoList(commentPage.getRecords());
        //完成子评论的注入
        for (CommentVo commentVo : commentVoList) {
            getLinkChildren(commentVo);
        }
        return ResponseResult.okResult(new PageVo(commentVoList,commentVoList.stream().count()));
    }

    /**
     * @param commentList 评论列表
     * @return: java.util.List<com.zxnk.entity.CommentVo>
     * @decription 根据评论列表完成数据的封装，完成评论者昵称和被评论者昵称的注入
     * @date 2023/5/4 21:29
    */
    private List<CommentVo> getCommentVoList(List<Comment> commentList){
        //1.完成属性的复制
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeans(commentList, CommentVo.class);
        //2.完成评论者昵称和被评论者昵称的注入
        commentVoList.stream().forEach(new Consumer<CommentVo>() {
            @Override
            public void accept(CommentVo commentVo) {
                //完成评论者昵称的注入
                Long createBy = commentVo.getCreateBy();
                String nickName = userMapper.selectById(createBy).getNickName();
                commentVo.setUsername(nickName);

                //完成被评论者昵称的注入,当rootId不为-1的时候
                if(commentVo.getToCommentUserId()!= -1){
                    Long toCommentUserId = commentVo.getToCommentUserId();
                    User toCommentUser = userMapper.selectById(toCommentUserId);
                    commentVo.setToCommentUserName(toCommentUser.getNickName());
                }else {
                    commentVo.setToCommentUserName(null);
                }
            }
        });
        return commentVoList;
    }

    /**
     * @param commentVo 父评论对象
     * @return: void
     * @decription 根据父评论对象查询相应的子评论，并注入父评论
     * @date 2023/5/4 21:44
    */
    private void getChildren(CommentVo commentVo){
        //查询子评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        //1.保证是属于同一篇博文
        wrapper.eq(Comment::getArticleId,commentVo.getArticleId());
        //2.保证是相应的子评论，即子评论的rootId等等于父评论的id
        wrapper.eq(Comment::getRootId,commentVo.getId());
        //3.按时间顺序排序
        wrapper.orderByDesc(Comment::getCreateTime);
        List<Comment> children = commentMapper.selectList(wrapper);
        //对子评论集合完成相应的封装,并进行相应的昵称注入
        List<CommentVo> commentVoList = getCommentVoList(children);
        //完成子评论的注入
        commentVo.setChildren(commentVoList);
    }

    private void getLinkChildren(CommentVo commentVo){
        //查询子评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        //2.保证是相应的子评论，即子评论的rootId等等于父评论的id
        wrapper.eq(Comment::getRootId,commentVo.getId());
        //3.按时间顺序排序
        wrapper.orderByDesc(Comment::getCreateTime);
        List<Comment> children = commentMapper.selectList(wrapper);
        //对子评论集合完成相应的封装
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeans(children, CommentVo.class);
        //完成子评论的注入
        commentVo.setChildren(commentVoList);
    }
}