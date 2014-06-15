/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth;

import com.ifhz.core.po.Resource;
import com.ifhz.core.vo.ResourceVo;

import java.text.ParseException;
import java.util.List;


/**
 * 资源管理
 * 
 * @author radish
 */
public interface ResourceService {

	/***
	 * 找到资源根节点
	 * 
	 * @author radishlee
	 * @param id
	 * @return
	 */
	public Resource findRootResource(long id);

	/**
	 * 找到资源的儿子节点集合
	 * 
	 * @author radishlee
	 * @param id
	 * @return
	 */
	public List<Resource> findAllChildrenById(long id);

	/**
	 * 找到角色对应的资源
	 * 
	 * @author radishlee
	 * @param id
	 * @return
	 */
	public String findAllRoleResourceXmlString(long id,boolean adminflag);

	/**
	 * @author radishlee
	 * @return
	 */
	public String findAllResourceTreeXmlString();

	/**
	 * 根据id查询资源
	 * 
	 * @author radishlee
	 * @param id
	 * @return
	 */
	public ResourceVo findById(long id) throws ParseException;

	/**
	 * @author radishlee
	 * @param resource
	 */
	public void insert(Resource resource);

	/**
	 * @author radishlee
	 * @param resName
	 * @return
	 */
	public Resource findByResName(String resName, Long parentId);

	/**
	 * @author radishlee
	 * @param dbResource
	 */
	public void saveResFullPath(Resource dbResource);

	/**
	 * @author radishlee
	 * @param resName
	 * @param id
	 * @return
	 */
	public Resource findByResNameBesideSelf(String resName, long id);

	/**
	 * @author radishlee
	 * @param resource
	 */
	public void update(Resource resource);

	/**
	 * @author radishlee
	 * @param resId
	 */
	public void deleteAllRefByResId(long resId);

	/**
	 * @author radishlee
	 * @param resId
	 */
	public void delete(long resId);

	/**
	 * @author wangshaofen
	 * @param id
	 * @return
	 */

	public List<String> findFullpathByRoleId(Long id);
}
