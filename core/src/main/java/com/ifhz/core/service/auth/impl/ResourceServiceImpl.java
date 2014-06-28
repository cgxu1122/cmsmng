/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth.impl;

import com.ifhz.core.base.commons.anthrity.AuthrityTreeConstants;
import com.ifhz.core.mapper.ResourceMapper;
import com.ifhz.core.po.Resource;
import com.ifhz.core.po.RoleResourceRef;
import com.ifhz.core.service.auth.ResourceService;
import com.ifhz.core.service.auth.RoleResourceRefService;
import com.ifhz.core.service.auth.RoleService;
import com.ifhz.core.vo.ResourceVo;
import com.ifhz.core.vo.RoleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;


/**
 * 角色管理实现类
 * 
 * @author luyujian
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
    private static Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);
	protected StringBuffer sbXml;

	@Autowired
    ResourceMapper resourceMapper;
    @Autowired
    RoleService roleService;
	@Autowired
    RoleResourceRefService roleResourceRefService;

	/**
	 * 获取所有角色所有的资源xml
	 * 
	 * @author radish
	 */
	@Override
	public String findAllRoleResourceXmlString(long roleId,boolean adminflag,boolean noResFlag) {
		return buildTree(AuthrityTreeConstants.CHECKBOX_RESOURCE_TREE, roleId,adminflag,noResFlag);
	}

	final String buildTree(int type, long roleId,boolean adminflag,boolean noResFlag) {
		sbXml = new StringBuffer();
		sbXml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sbXml.append("<tree id=\"0\">\n");

		Resource root = resourceMapper.findRootResource();

        if (type == 3) {
            doBuildFullResourceTree(root);
        }else if(type==2){
             if(adminflag){
                 doBuildCheckboxResourceTree(root, roleId);
             }else{
                 if(noResFlag){
                     RoleVo roleVo = roleService.findById(roleId);
                     List<RoleResourceRef> rrrList = roleResourceRefService.findAllResourceForRoleByRoleId(roleVo.getParentId());
                     if(rrrList.size()>0){
                         Resource res = resourceMapper.findParentById(rrrList.get(0).getResourceId());
                         doBuildCheckboxNotResourceTree(res,roleVo.getParentId());
                     }
                 }else{
                    // RoleVo roleVo = roleService.findById(roleId);
                     List<RoleResourceRef> rrrList = roleResourceRefService.findAllResourceForRoleByRoleId(roleId);
                     if(rrrList.size()>0){
                         Resource res = resourceMapper.findParentById(rrrList.get(0).getResourceId());
                        // doBuildCheckboxNotFullResourceTree(res,roleVo.getParentId() ,roleId);
                         doBuildCheckboxNotFullResourceTree(res ,roleId);
                     }
                 }
             }
        }
        sbXml.append("</tree>");
        return sbXml.toString();
	}

    protected void doBuildCheckboxNotFullResourceTree(Resource resource,long roleId) {
        List<Resource> childrenList = resourceMapper.findAllChildrenById(resource.getResourceId());
        String checkedStr = "";

        RoleResourceRef rrr = roleResourceRefService.findByRoleIdAndResourceId(roleId, resource.getResourceId());
        if (childrenList.size() == 0 && rrr != null && rrr.getAcces() == 1) {
                checkedStr = "   checked=\"1\"";
        }

       // RoleResourceRef rrr = roleResourceRefService.findByRoleIdAndResourceId(parentRoleId, resource.getResourceId());
        if (rrr != null) {
            sbXml.append("<item text=\"" + resource.getResName() + "\" id=\""
                    + resource.getResourceId() + "\" " + "open=\"1\"" + checkedStr + ">\n");
        }

        for (Iterator iter = childrenList.iterator(); iter.hasNext();) {
            Resource child = (Resource) iter.next();
            doBuildCheckboxNotFullResourceTree(child,roleId);
        }
        if (rrr != null) {
            sbXml.append("</item>\n");
       }
    }


    protected void doBuildCheckboxNotResourceTree(Resource resource, long roleId) {
        List<Resource> childrenList = resourceMapper.findAllChildrenById(resource.getResourceId());
        String checkedStr = "";

        RoleResourceRef rrr = roleResourceRefService.findByRoleIdAndResourceId(roleId, resource.getResourceId());

        if(rrr!=null && rrr.getAcces()==1){
            sbXml.append("<item text=\"" + resource.getResName() + "\" id=\""
                    + resource.getResourceId() + "\" " + "open=\"1\"" + checkedStr + ">\n");
        }

        for (Iterator iter = childrenList.iterator(); iter.hasNext();) {
            Resource child = (Resource) iter.next();
            doBuildCheckboxNotResourceTree(child, roleId);
        }
        if(rrr!=null && rrr.getAcces()==1){
            sbXml.append("</item>\n");
        }
    }

    /**
     * @author radishlee
     * @param resource
     */
    private void doBuildFullResourceTree(Resource resource) {
        long resourceId = resource.getResourceId();
        List<Resource> childrenList = resourceMapper
                .findAllChildrenById(resourceId);
        sbXml.append("<item text=\"" + resource.getResName() + "\" id=\""
                + resourceId + "\" " + "open=\"1\" >\n");
        for (Iterator iter = childrenList.iterator(); iter.hasNext();) {
            Resource child = (Resource) iter.next();
            doBuildFullResourceTree(child);
        }
        sbXml.append("</item>\n");
    }

    protected void doBuildCheckboxResourceTree(Resource resource, long roleId) {
        List<Resource> childrenList = resourceMapper.findAllChildrenById(resource.getResourceId());
        String checkedStr = "";

        if (childrenList.size() == 0) {
            RoleResourceRef rrr = roleResourceRefService.findByRoleIdAndResourceId(roleId, resource.getResourceId());
            if (rrr != null && rrr.getAcces() == 1) {
                checkedStr = "   checked=\"1\"";
            }
        }

        sbXml.append("<item text=\"" + resource.getResName() + "\" id=\""
                + resource.getResourceId() + "\" " + "open=\"1\"" + checkedStr + ">\n");
        for (Iterator iter = childrenList.iterator(); iter.hasNext();) {
            Resource child = (Resource) iter.next();
            doBuildCheckboxResourceTree(child, roleId);
        }
        sbXml.append("</item>\n");
    }

    /**
	 * 得到根节点
	 * 
	 * @author radish
	 */
	@Override
	public Resource findRootResource(long id) {
		return resourceMapper.findRootResource();
	}

	/**
	 * 得到当前节点的子节点
	 * 
	 * @author radish
	 */
	@Override
	public List<Resource> findAllChildrenById(long id) {
		return resourceMapper.findAllChildrenById(id);
	}

	/**
	 * 资源树
	 * 
	 * @author radishlee
	 */
	@Override
	public String findAllResourceTreeXmlString() {
		return buildTree(AuthrityTreeConstants.FULL_RESOURCE_TREE, 0l,false,false);
	}

	/**
	 * 根据id查询资源
	 * 
	 * @author radishlee
	 * @param id
	 * @return
	 * @throws java.text.ParseException
	 */
	@Override
	public ResourceVo findById(long id) throws ParseException {
        ResourceVo resourceVo = resourceMapper.findById(id);
//		Long id_ = (Long) (map.get("id") == null ? 0 : map.get("id"));
//		String resName = map.get("resName") == null ? "" : (String) map
//				.get("resName");
//		String ext = map.get("ext") == null ? "" : (String) map.get("ext");
//		String resUrl = map.get("resUrl") == null ? "" : (String) map
//				.get("resUrl");
//		Date createTime = (Date) (map.get("createTime") == null ? "" : map
//				.get("createTime"));
//		Long parentId = (Long) (map.get("parentId") == null ? 0 : map
//				.get("parentId"));
//		String icon = map.get("icon") == null ? "" : (String) map.get("icon");
//		Integer level = (Integer) (map.get("level") == null ? 0 : map
//				.get("level"));
//		String fullPath = map.get("fullPath") == null ? "" : (String) map
//				.get("fullPath");
//		String parentName = map.get("parentName") == null ? "" : (String) map
//				.get("parentName");
//
//		ResourceVo resourceVo = new ResourceVo();
//		resourceVo.setResName(resName);
//		resourceVo.setResUrl(resUrl);
//		resourceVo.setFullPath(fullPath);
//		resourceVo.setParentId(parentId);
//		resourceVo.setResourceId(id_);
//
//		resourceVo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//				.parse(createTime.toString()));

		return resourceVo;
	}

	@Override
	public void insert(Resource resource) {
		resourceMapper.insert(resource);
	}

	@Override
	public Resource findByResName(String resName,Long parentId) {
		return resourceMapper.findByResName(null,resName,parentId);
	}

	@Override
	public void saveResFullPath(Resource dbResource) {
		resourceMapper.updateResFullPath(dbResource);
	}

	/**
	 * 更新时名称排除自己
	 */
	@Override
	public Resource findByResNameBesideSelf(String resName, long id) {
		return resourceMapper.findByResNameBesideSelf(null,resName, id);
	}

	/**
	 * 更新
	 */
	@Override
	public void update(Resource resource) {
		resourceMapper.update(resource);
	}

	@Override
	public void deleteAllRefByResId(long resId) {
		roleResourceRefService.deleteAllRefByResId(resId);
	}

	@Override
	public void delete(long resId) {
		this.doDelete(resId);
	}

	/**
	 * @author radishlee
	 * @param id
	 */
	private void doDelete(long id) {
		List<Resource> resList = resourceMapper.findAllChildrenById(id);

		resourceMapper.delete(id);
		for (Resource resource : resList) {
			this.doDelete(resource.getResourceId());
		}
	}

	/**
	 * @author wangshaofen
	 * @param id
	 */
	public List<String> findFullpathByRoleId(Long id) {

		return resourceMapper.findFullpathByRoleId(id);
	}

    @Override
    public List<String> findResUrlByRoleId(Long id) {
        return resourceMapper.findResUrlByRoleId(id);
    }

}
