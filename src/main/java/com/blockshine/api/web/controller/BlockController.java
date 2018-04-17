package com.blockshine.api.web.controller;


import com.blockshine.common.util.R;
import com.blockshine.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockshine.api.service.BlockShineWebCallService;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j(topic = "blockApi")
@RequestMapping("/block")
public class BlockController extends BaseController {

	@Autowired
	BlockShineWebCallService bswCallService;

	/**
	 * 返回最近块的数量
	 *
	 * @return
	 */
	@RequestMapping(value = "/number", method = RequestMethod.GET)
	@ResponseBody
	public R eth_blockNumber() {
		JSONObject blockNumberJson = bswCallService.getBestBlockNumber();
		R result = new R();
		result.put("chainData", blockNumberJson);
		return result;
	}

	/**
	 * 获取区块交易详情
	 * @param bnOrId
	 * @param fullTransactionObjects
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public R getBlockInfo(String bnOrId, boolean fullTransactionObjects) {
		JSONObject blockInfo = bswCallService.getBlockInfo(bnOrId,fullTransactionObjects);
		if(blockInfo.containsKey("nonce"))
			blockInfo.remove("nonce");
		if(blockInfo.containsKey("miner"))
			blockInfo.remove("miner");
		if(blockInfo.containsKey("difficulty"))
			blockInfo.remove("difficulty");
		if(blockInfo.containsKey("totalDifficulty"))
			blockInfo.remove("totalDifficulty");
		R result = new R();
		result.put("chainData", blockInfo);
		return result;
	}


	/**
	 *
	 * 查询指定范围内的区块列表
	 * @param hash
	 * @param qty
	 * @return
	 * @throws Exception
	 */
	//查询一组区块头信息 从 指定hash开始 固定 qty条
	@RequestMapping(value = "/headers", method = RequestMethod.GET)
	@ResponseBody
	public R getBlocksEndWith(byte[] hash, Long qty) {
		log.info("block headers");
		JSONArray blockInfo = bswCallService.getBlocksEndWith(hash,qty);
		R result = new R();
		for(Object obj : blockInfo ) {
			JSONObject json = (JSONObject) obj;
			if(json.containsKey("nonce"))
				json.remove("nonce");
			if(json.containsKey("difficulty"))
				json.remove("difficulty");
			if(json.containsKey("difficultyBI"))
				json.remove("difficultyBI");
		}

		result.put("chainData", blockInfo);
		return result;
	}





}
