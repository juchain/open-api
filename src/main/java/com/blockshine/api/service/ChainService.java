package com.blockshine.api.service;

import com.blockshine.api.dao.ChainDao;
import com.blockshine.api.domain.ChainDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;





@Service
public class ChainService {
	@Autowired
	private ChainDao chainDao;
	

	public ChainDO get(Long id){
		return chainDao.get(id);
	}
	

	public List<ChainDO> list(Map<String, Object> map){
		return chainDao.list(map);
	}


	public int count(Map<String, Object> map){
		return chainDao.count(map);
	}

	public int save(ChainDO chain){
		return chainDao.save(chain);
	}

	public int update(ChainDO chain){
		return chainDao.update(chain);
	}

	public int remove(Long id){
		return chainDao.remove(id);
	}

	public int batchRemove(Long[] ids){
		return chainDao.batchRemove(ids);
	}
	
}
