package com.util;

import java.util.regex.Pattern;

public class MyUtil {
	/**
	 * 전체 페이지 수 구하기
	 * @param rows			한행에 출력할 목록 수
	 * @param dataCount	총 데이터 개수
	 * @return					전체 페이지 수
	 */
	public int pageCount(int rows, int dataCount) {
		if(dataCount <= 0) 
			return 0;
	
		return dataCount / rows + (dataCount % rows > 0 ? 1 : 0);
	}
	
	/**
	 * 페이징(paging) 처리(GET 방식)
	 * @param current_page	현재 표시되는 페이지 번호	
	 * @param total_page		전체 페이지 수
	 * @param list_url				링크를 설정할 주소
	 * @return						페이징 처리 결과
	 */
	public String paging(int current_page, int total_page, String list_url) {
		StringBuilder sb=new StringBuilder();
		
		int numPerBlock=10;
		int currentPageSetup;
		int n, page;
		
		if(current_page<1 || total_page < 1) {
			return "";
		}
		
		if(list_url.indexOf("?")!=-1) {
			list_url+="&";
		} else {
			list_url+="?";
		}
		
		// currentPageSetup : 표시할첫페이지-1
		currentPageSetup=(current_page/numPerBlock)*numPerBlock;
		if(current_page%numPerBlock==0) {
			currentPageSetup=currentPageSetup-numPerBlock;
		}

		sb.append("<style type='text/css'>");
		sb.append("#paginate {clear:both;font:12px \"맑은 고딕\",NanumGothic,돋움,Dotum,AppleGothic;padding:15px 0px 0px 0px;text-align:center;height:28px;white-space:nowrap;}");
		sb.append("#paginate a {border:1px solid #ccc;height:28px;color:#000000;text-decoration:none;padding:4px 7px 4px 7px;margin-left:3px;line-height:normal;vertical-align:middle;outline:none; select-dummy: expression(this.hideFocus=true);}");
		sb.append("#paginate a:hover, a:active {border:1px solid #ccc;color:#6771ff;vertical-align:middle; line-height:normal;}");
		sb.append("#paginate .curBox {border:1px solid #e28d8d; background: #fff; color:#cb3536; font-weight:bold;height:28px;padding:4px 7px 4px 7px;margin-left:3px;line-height:normal;vertical-align:middle;}");
		sb.append("#paginate .numBox {border:1px solid #ccc;height:28px;font-weight:bold;text-decoration:none;padding:4px 7px 4px 7px;margin-left:3px;line-height:normal;vertical-align:middle;}");
		sb.append("</style>");
		
		sb.append("<div id='paginate'>");
		// 처음페이지, 이전(10페이지 전)
		n=current_page-numPerBlock;
		if(total_page > numPerBlock && currentPageSetup > 0) {
			sb.append("<a href='"+list_url+"page=1'>처음</a>");
			sb.append("<a href='"+list_url+"page="+n+"'>이전</a>");
		}
		
		// 바로가기
		page=currentPageSetup+1;
		while(page<=total_page && page <=(currentPageSetup+numPerBlock)) {
			if(page==current_page) {
				sb.append("<span class='curBox'>"+page+"</span>");
			} else {
				sb.append("<a href='"+list_url+"page="+page+"' class='numBox'>"+page+"</a>");
			}
			page++;
		}
		
		// 다음(10페이지 후), 마지막페이지
		n=current_page+numPerBlock;
		if(n>total_page) n=total_page;
		if(total_page-currentPageSetup>numPerBlock) {
			sb.append("<a href='"+list_url+"page="+n+"'>다음</a>");
			sb.append("<a href='"+list_url+"page="+total_page+"'>끝</a>");
		}
		sb.append("</div>");
	
		return sb.toString();
	}

	/**
	 * javascript로 페이징(paging) 처리 : javascript listPage() 함수 호출
	 * @param current_page	현재 표시되는 페이지 번호	
	 * @param total_page		전체 페이지 수
	 * @return						페이징 처리 결과
	 */
    public String paging(int current_page, int total_page) {
    	StringBuilder sb=new StringBuilder();

        int numPerBlock = 10;   // 리스트에 나타낼 페이지 수
        int currentPageSetUp;
        int n, page;
        
        if(current_page < 1 || total_page < 1) {
        	return "";
        }
        
        // 표시할 첫 페이지
        currentPageSetUp = (current_page / numPerBlock) * numPerBlock;
        if (current_page % numPerBlock == 0) {
            currentPageSetUp = currentPageSetUp - numPerBlock;
        }
        
		sb.append("<style type='text/css'>");
		sb.append("#paginate {clear:both;font:12px \"맑은 고딕\",NanumGothic,돋움,Dotum,AppleGothic;padding:15px 0px 0px 0px;text-align:center;height:28px;white-space:nowrap;}");
		sb.append("#paginate a {border:1px solid #ccc;height:28px;color:#000000;text-decoration:none;padding:4px 7px 4px 7px;margin-left:3px;line-height:normal;vertical-align:middle;outline:none; select-dummy: expression(this.hideFocus=true);}");
		sb.append("#paginate a:hover, a:active {border:1px solid #ccc;color:#6771ff;vertical-align:middle; line-height:normal;}");
		sb.append("#paginate .curBox {border:1px solid #e28d8d; background: #fff; color:#cb3536; font-weight:bold;height:28px;padding:4px 7px 4px 7px;margin-left:3px;line-height:normal;vertical-align:middle;}");
		sb.append("#paginate .numBox {border:1px solid #ccc;height:28px;font-weight:bold;text-decoration:none;padding:4px 7px 4px 7px;margin-left:3px;line-height:normal;vertical-align:middle;}");
		sb.append("</style>");
		
		sb.append("<div id='paginate'>");
        
        // 처음페이지, 이전(10페이지 전)
        n = current_page - numPerBlock;
        if ((total_page > numPerBlock) && (currentPageSetUp > 0)) {
			sb.append("<a onclick='listPage(1);'>처음</a>");
			sb.append("<a onclick='listPage("+n+");'>이전</a>");
        }

        // 바로가기 페이지 구현
        page = currentPageSetUp + 1;
        while((page <= total_page) && (page <= currentPageSetUp + numPerBlock)) {
           if(page == current_page) {
        	   sb.append("<span class='curBox'>"+page+"</span>");
           } else {
			   sb.append("<a onclick='listPage("+page+");' class='numBox'>"+page+"</a>");
           }
           page++;
        }

        // 다음(10페이지 후), 마지막 페이지
        n = current_page + numPerBlock;
		if(n>total_page) n=total_page;
        if (total_page - currentPageSetUp > numPerBlock) {
			sb.append("<a onclick='listPage("+n+");'>다음</a>");
			sb.append("<a onclick='listPage("+total_page+");'>끝</a>");
        }
		sb.append("</div>");

        return sb.toString();
    }

	/**
	 * javascript로 페이징(paging) 처리 : javascript 지정 함수 호출
	 * @param current_page	현재 표시되는 페이지 번호	
	 * @param total_page		전체 페이지 수
	 * @param methodName	호출할 자바 스크립트 함수명
	 * @return						페이징 처리 결과
	 */
    public String pagingMethod(int current_page, int total_page, String methodName) {
    	StringBuilder sb=new StringBuilder();

        int numPerBlock = 10;   // 리스트에 나타낼 페이지 수
        int currentPageSetUp;
        int n, page;
        
        if(current_page < 1 || total_page < 1) {
        	return "";
        }
        
        // 표시할 첫 페이지
        currentPageSetUp = (current_page / numPerBlock) * numPerBlock;
        if (current_page % numPerBlock == 0) {
            currentPageSetUp = currentPageSetUp - numPerBlock;
        }

		sb.append("<style type='text/css'>");
		sb.append("#paginate {clear:both;font:12px \"맑은 고딕\",NanumGothic,돋움,Dotum,AppleGothic;padding:15px 0px 0px 0px;text-align:center;height:28px;white-space:nowrap;}");
		sb.append("#paginate a {border:1px solid #ccc;height:28px;color:#000000;text-decoration:none;padding:4px 7px 4px 7px;margin-left:3px;line-height:normal;vertical-align:middle;outline:none; select-dummy: expression(this.hideFocus=true);}");
		sb.append("#paginate a:hover, a:active {border:1px solid #ccc;color:#6771ff;vertical-align:middle; line-height:normal;}");
		sb.append("#paginate .curBox {border:1px solid #e28d8d; background: #fff; color:#cb3536; font-weight:bold;height:28px;padding:4px 7px 4px 7px;margin-left:3px;line-height:normal;vertical-align:middle;}");
		sb.append("#paginate .numBox {border:1px solid #ccc;height:28px;font-weight:bold;text-decoration:none;padding:4px 7px 4px 7px;margin-left:3px;line-height:normal;vertical-align:middle;}");
		sb.append("</style>");
		
		sb.append("<div id='paginate'>");
        
        // 처음페이지, 이전(10페이지 전)
        n = current_page - numPerBlock;
        if ((total_page > numPerBlock) && (currentPageSetUp > 0)) {
			sb.append("<a onclick='"+methodName+"(1);'>처음</a>");
			sb.append("<a onclick='"+methodName+"("+n+");'>이전</a>");
        }

        // 바로가기 페이지 구현
        page = currentPageSetUp + 1;
        while((page <= total_page) && (page <= currentPageSetUp + numPerBlock)) {
           if(page == current_page) {
        	   sb.append("<span class='curBox'>"+page+"</span>");
           } else {
			   sb.append("<a onclick='"+methodName+"("+page+");' class='numBox'>"+page+"</a>");
           }
           page++;
        }

        // 다음(10페이지 후), 마지막 페이지
        n = current_page + numPerBlock;
		if(n>total_page) n=total_page;
        if (total_page - currentPageSetUp > numPerBlock) {
			sb.append("<a onclick='"+methodName+"("+n+");'>다음</a>");
			sb.append("<a onclick='"+methodName+"("+total_page+");'>끝</a>");
        }
		sb.append("</div>");

        return sb.toString();
    }

    /**
     * 특수문자를 HTML 문자로 변경 및 엔터를 <br>로 변경
     * @param str	변경할 문자열
     * @return		HTML 문자로 변경된 문자열
     */
     public String htmlSymbols(String str) {
		if(str==null||str.length()==0)
			return "";

    	 str=str.replaceAll("&", "&amp;");
    	 str=str.replaceAll("\"", "&quot;");
    	 str=str.replaceAll(">", "&gt;");
    	 str=str.replaceAll("<", "&lt;");
    	 
    	 str=str.replaceAll("\n", "<br>");
    	 str=str.replaceAll("\\s", "&nbsp;");  // \\s가 엔터도 변경하므로 \n보다 뒤에
    	 
    	 return str;
     }

     /**
      * NULL을 ""로 변경하기
      * @param str	변경할 문자열
      * @return		NULL을 ""로 변경된 문자열
      */
     public String checkNull(String str) {
         return str == null ? "" : str;
     }

     /**
      * E-Mail 검사
      * @param email  검사 할 E-Mail 
      * @return E-Mail 검사 결과
      */
     public boolean isValidEmail(String email) {
         if (email==null) return false;
         
         return Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
     }
}
