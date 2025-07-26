package com.chat.mapper;

import com.chat.entity.AiRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AiRoleMapper {

    /**
     * 获取所有AI角色列表(只返回需要的数据)
     * @return AI角色列表
     */
    @Select("SELECT ai_id, name, avatar, status FROM ai_roles ORDER BY created_at DESC")
    List<AiRole> findAllAiRoles();

    /**
     * 根据AI ID查询AI角色(返回所有数据)
     * @param aiId AI ID
     * @return AI角色信息
     */
    @Select("SELECT * FROM ai_roles WHERE ai_id = #{aiId}")
    AiRole findByAiId(Long aiId);

    /**
     * 根据创建者ID查询AI角色列表
     * @param createdBy 创建者ID
     * @return AI角色列表
     */
    @Select("SELECT * FROM ai_roles WHERE created_by = #{createdBy} ORDER BY created_at DESC")
    List<AiRole> findByCreatedBy(String createdBy);


}