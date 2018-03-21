package com.blockshine.api.dao;

import com.blockshine.api.domain.ChainDO;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-03-19 18:22:33
 */
@Mapper
public interface ChainDao {

	ChainDO get(Long id);
	
	List<ChainDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ChainDO chain);
	
	int update(ChainDO chain);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
