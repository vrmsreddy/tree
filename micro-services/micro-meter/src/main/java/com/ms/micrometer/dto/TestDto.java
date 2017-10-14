package com.ms.micrometer.dto;

/**
 * @author MS
 *
 */
public class TestDto {
	private String testData;
	private int id;
	
	public String getTestData() {
		return testData;
	}

	public int getId() {
		return id;
	}

	public void setTestData(String testData) {
		this.testData = testData;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}
	
	@Override
	public int hashCode() {
		return 31;
	}
	
}
