package com.green.bloom.utils;

import lombok.Getter;

@Getter
public class PageUtil {
	
	private int from; //시작페이지번호
	private int to; //마지막페이지 번호
	private int tot; //페이지 총 수
	
	private int pageLimit;
	
	/**
	 * 
	 * @param rowCount	: 총 게시글 수
	 * @param limit		: 한페이지의 표현되는 게시글 개수
	 * @param page		: 현재 페이지 번호
	 * @hidden pageLimit: default 5
	 */
	public static PageUtil createPage(int rowCount, int limit, int page) {
		return new PageUtil(rowCount, limit, page);
	}
	
	private PageUtil(int rowCount, int limit, int page) {
		this(rowCount, limit, page, 5);
	}
	
	/**
	 * 
	 * @param rowCount	: 총 게시글 수
	 * @param limit		: 한페이지의 표현되는 게시글 개수
	 * @param page		: 현재 페이지 번호
	 * @param pageLimit	: 한번에 보여지는 페이지 개수
	 */
	public static PageUtil createPage(int rowCount, int limit, int page, int pageLimit) {
		return new PageUtil(rowCount, limit, page, pageLimit);
	}
	
	
	private PageUtil(int rowCount, int limit, int page, int pageLimit) {
		this.pageLimit=pageLimit;
		tot=rowCount/limit;
		//나머지가 존재하면 ++
		if(rowCount%limit!=0)tot++;
		
		//1~5 : 1
		//6~10 : 2
		//11~15 : 3
		//0: 1/5,2/5,3/5,4/5, 1: 5/5
		//1: 6/5,7/5,8/5,9/5, 2: 10/5
		//2: 11/5,12/5,13/5,14/5, 3: 15/5
		int pageBlock=page/pageLimit;
		if(page%pageLimit>0) pageBlock++;
		to=pageLimit * pageBlock;
		from=to-pageLimit+1;
		
		if(to>tot)to=tot;
		
	}

}