package com.blockshine.api.web.controller;


import com.blockshine.common.util.R;
import com.blockshine.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.blockshine.api.service.BlockShineWebCallService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	@RequestMapping(value = "/block/number", method = RequestMethod.GET)
	@ResponseBody
	public R eth_blockNumber() {
		String blockNumberJson = bswCallService.getBestBlockNumber();
		R result = new R();
		result.put("chainData", blockNumberJson);
		return result;
	}


	/**
	 *
	 * 区块详情
	 * @param bnOrId
	 * @param fullTransactionObjects
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/block/info", method = RequestMethod.GET)
	@ResponseBody
	public R getBlockInfo(String bnOrId, boolean fullTransactionObjects) {
		String blockInfo = bswCallService.getBlockInfo(bnOrId,fullTransactionObjects);
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
	@RequestMapping(value = "/block/headers", method = RequestMethod.GET)
	@ResponseBody
	public R getBlocksEndWith(byte[] hash, Long qty) {

		String blockInfo = bswCallService.getBlocksEndWith(hash,qty);
		R result = new R();
		result.put("chainData", blockInfo);
		return result;
	}





}
