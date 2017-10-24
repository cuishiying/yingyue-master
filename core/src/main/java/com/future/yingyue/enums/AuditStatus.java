package com.future.yingyue.enums;

public enum AuditStatus {
	unaudited(0,"未审核"),pending(1, "审核中"), audited(2, "已审核"), rejected(3, "已拒绝");

	private Integer id;
	private String name;

	private AuditStatus(Integer id, String name) {
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
