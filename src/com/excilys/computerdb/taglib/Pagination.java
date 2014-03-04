package com.excilys.computerdb.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Pagination extends TagSupport {

	private int currentPage;
	private int lastPage;
	private List<String> queryParameters;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		try {
			String url = "./dashboard?";
			String params = "";
			if (queryParameters != null) {
				for (String param : queryParameters) {
					params += param + "&";
				}
			}
			url += params;
			JspWriter out = pageContext.getOut();

			if (currentPage - 4 > 0) {
				out.print("<a href='" + url + "page=" + (currentPage - 4)
						+ "'><<</a>");
			}
			if (currentPage - 3 > 0) {

				out.print(" <a href='" + url + "page=1'>1</a>");

			}
			if (currentPage - 4 > 0) {
				out.print(" ...");
			}
			if (currentPage - 2 > 0) {
				out.print(" <a href='" + url + "page=" + (currentPage - 2)
						+ "'>" + (currentPage - 2) + "</a>");
			}
			if (currentPage - 1 > 0) {
				out.print(" <a href='" + url + "page=" + (currentPage - 1)
						+ "'>" + (currentPage - 1) + "</a>");
			}
			out.print(" " + currentPage);
			if (currentPage + 1 <= lastPage) {
				out.print(" <a href='" + url + "page=" + (currentPage + 1)
						+ "'>" + (currentPage + 1) + "</a>");
			}
			if (currentPage + 2 <= lastPage) {
				out.print(" <a href='" + url + "page=" + (currentPage + 2)
						+ "'>" + (currentPage + 2) + "</a>");
			}

			if (currentPage + 4 <= lastPage) {
				out.print(" ...");
			}
			if (currentPage + 3 <= lastPage) {

				out.print(" <a href='" + url + "page=" + lastPage + "'>"
						+ lastPage + "</a>");

			}
			if (currentPage + 4 <= lastPage) {
				out.print(" <a href='" + url + "page=" + (currentPage + 4)
						+ "'>>></a>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public List<String> getQueryParameters() {
		return queryParameters;
	}

	public void setQueryParameters(List<String> queryParameters) {
		this.queryParameters = queryParameters;
	}
}