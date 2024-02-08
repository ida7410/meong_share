package com.ms.main.bo;

import lombok.Data;

@Data
public class Criteria {
	
	private int page;
	private int perPageNum;
	
	public int getPageStart() {
		return (this.page - 1) * perPageNum;
	}
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 8;
	}
	
	public void setPage(int page) {
		if (page <= 0) {
			this.page = 1;
		}
		else {
			this.page = page;
		}
	}
}
