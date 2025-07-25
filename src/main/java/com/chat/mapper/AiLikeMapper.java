package com.chat.mapper;

import com.chat.entity.AiLike;
import org.apache.ibatis.annotations.*;

/**
 * AI点赞数据访问层
 */
@Mapper
public interface AiLikeMapper {

    /**
     * 添加点赞记录
     * @param aiLike 点赞记录
     * @return 影响行数
     */
    @Insert("INSERT INTO ai_likes (user_id, ai_id) VALUES (#{userId}, #{aiId})")
    int insertLike(AiLike aiLike);

    /**
     * 删除点赞记录
     * @param userId 用户ID
     * @param aiId AI ID
     * @return 影响行数
     */
    @Delete("DELETE FROM ai_likes WHERE user_id = #{userId} AND ai_id = #{aiId}")
    int deleteLike(@Param("userId") Long userId, @Param("aiId") Long aiId);

    /**
     * 查询用户是否已点赞某个AI
     * @param userId 用户ID
     * @param aiId AI ID
     * @return 点赞记录，如果未点赞则返回null
     */
    @Select("SELECT * FROM ai_likes WHERE user_id = #{userId} AND ai_id = #{aiId}")
    AiLike findByUserIdAndAiId(@Param("userId") Long userId, @Param("aiId") Long aiId);

    /**
     * 统计某个AI的总点赞数
     * @param aiId AI ID
     * @return 点赞总数
     */
    @Select("SELECT COUNT(*) FROM ai_likes WHERE ai_id = #{aiId}")
    int countLikesByAiId(Long aiId);
}