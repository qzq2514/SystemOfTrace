package com.APES.Model;

public class QuerySimilarity {
	public int queryId;
	public double similarity;

	public int getQueryId() {
		return queryId;
	}

	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public QuerySimilarity(int queryId, double similarity) {
		super();
		this.queryId = queryId;
		this.similarity = similarity;
	}

	public QuerySimilarity() {
	}
}
