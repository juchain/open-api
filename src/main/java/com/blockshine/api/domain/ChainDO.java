package com.blockshine.api.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-03-19 18:22:33
 */
public class ChainDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//创建时间
	private Date created;
	//修改时间
	private Date updated;
	//状态 0:禁用，1:正常
	private Integer status;
	//nonce
	private String nonce;
	//转出地址
	private String addressFrom;
	//转入地址
	private String addressTo;
	//上链数据
	private String data;
	//上链返回数据
	private String receipt;

	//状态 1:init 2:sucess 3:falied
	private Integer dataStatus;
	//接口返回信息

	private String message;






	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreated() {
		return created;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdated() {
		return updated;
	}
	/**
	 * 设置：状态 0:禁用，1:正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态 0:禁用，1:正常
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：nonce
	 */
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	/**
	 * 获取：nonce
	 */
	public String getNonce() {
		return nonce;
	}
	/**
	 * 设置：转出地址
	 */
	public String getAddressFrom() {
		return addressFrom;
	}

	public String getAddressTo() {
		return addressTo;
	}

	public void setAddressTo(String addressTo) {
		this.addressTo = addressTo;
	}

	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}



	/**
	 * 设置：上链数据
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * 获取：上链数据
	 */
	public String getData() {
		return data;
	}
	/**
	 * 设置：上链返回数据
	 */
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	/**
	 * 获取：上链返回数据
	 */
	public String getReceipt() {
		return receipt;
	}
	/**
	 * 设置：状态 1:penging 2:sucess 3:falied
	 */
	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}
	/**
	 * 获取：状态 1:penging 2:sucess 3:falied
	 */
	public Integer getDataStatus() {
		return dataStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
