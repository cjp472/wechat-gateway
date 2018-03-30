package com.wangxiaobao.wechatgateway.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;

import com.wangxiaobao.wechatgateway.entity.PageModel;

/**
 * Copyright © 2017成都国盛天丰网络科技有限公司. All rights reserved.
 *
 * @project: pay-admin
 * @package: com.source3g.pay.admin.repository
 * @description: 基础Repository, 集成mybatis
 * @author: gaozitian
 * @date: 2017/6/19 18:08
 * @version: V1.0
 */
@NoRepositoryBean
public abstract class BaseRespository {

    @Autowired
    protected SqlSession sqlSession;


    /**
     * 查询对象list
     * @param nameSpace
     * @param <T>
     * @return
     */
    public <T> List<T> queryForList(String nameSpace) {
        return sqlSession.selectList(nameSpace);
    }

    /**
     * 查询对象List
     * @param nameSpace
     * @param parameter
     * @param <T>
     * @return
     */
    public <T> List<T> queryForList(String nameSpace, Object parameter) {
        return sqlSession.selectList(nameSpace, parameter);
    }

    /**
     * 查询对象List
     * @param nameSpace
     * @param pageModel
     * @param <T>
     * @return
     */
    public <T> PageModel<T> pageQueryForList(String nameSpace, PageModel<T> pageModel) {
        List<T> datas = sqlSession.selectList(nameSpace, pageModel);
        pageModel.setDatas(datas);
        return pageModel;
    }

    public <T> PageModel<T> searchByPage(String nameSpace, PageModel<T> pageModel) {
        List<T> datas = sqlSession.selectList(nameSpace, pageModel);
        pageModel.setDatas(datas);
        return pageModel;
    }

    public <T> T queryForOne(String nameSpace, Object parameter) {
        return sqlSession.selectOne(nameSpace, parameter);
    }

	public Integer getCountBy(Map<String, Object> param, String nameSpace) {
//		System.out.println(getSqlName(sql));
		return sqlSession.selectOne(nameSpace, param);
	}
}
