package com.future.yingyue.enums;

public enum PayStatus {
	unaudited(0,"未缴费"),pending(1, "待审核"), audited(2, "已缴费");

	private Integer id;
	private String name;

	private PayStatus(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
