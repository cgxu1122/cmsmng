/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.mapper;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.Resource;
import org.apache.ibatis.annotations.Param;


/**
 * 资源信息
 * 
 * @author luyujian
 */
public interface ResourceMapper {

	public List<Resource> getAllResource();

	/**
	 * 找到资源的跟节点
	 * 
	 * @author radish
	 * @return
	 */
	public Resource findRootResource();

	/**
	 * 找到所有的子节点
	 * 
	 * @author radishlee
	 * @param resourceId
	 * @return
	 */
	public List<Resource> findAllChildrenById(long resourceId);

	/**
	 * @author radishlee
	 * @param id
	 * @return
	 */
	public Map findById(long id);

	/**
	 * @author radishlee
	 * @param resource
	 * @return
	 */
	public Integer insert(Resource resource);

	/**
	 * @author radishlee
	 * @param resName
	 * @return
	 */
	public Resource findByResName(Pagination page,@Param("resName") String resName, @Param("parentId") Long parentId);

	/**
	 * @author radishlee
	 * @param dbResource
	 */
	public Integer updateResFullPath(Resource dbResource);

	/**
	 * @author radishlee
	 * @param resName
	 * @param resourceId
	 * @return
	 */
	public Resource findByResNameBesideSelf(Pagination page,@Param("resName") String resName,
                                            @Param("resourceId") long resourceId);

	/**
	 * @author radishlee
	 * @param resource
	 */
	public Integer update(Resource resource);

	/**
	 * @author radishlee
	 * @param resourceId
	 */
	public Integer delete(long resourceId);

	/**
	 * @author wangshaofen
	 * @param id
	 * @return
	 */

	public List<String> findFullpathByRoleId(Long id);
}
