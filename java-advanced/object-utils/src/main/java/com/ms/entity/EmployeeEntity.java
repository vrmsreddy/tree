package com.ms.entity;

import com.ms.util.EqualsHashCodeUtil;

/**
 * employee
 *
 */
public class EmployeeEntity {
	private  int id;
    private  Boolean isActive;
    private  String name;
    
	public EmployeeEntity(int id, Boolean isActive, String name) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

	public int getId() {
		return id;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public String getName() {
		return name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		/*
		 * final int prime = 31; int result = 1; result = prime * result +
		 * ((b == null) ? 0 : b.hashCode()); result = prime * result + i;
		 * result = prime * result + ((my == null) ? 0 : my.hashCode());
		 * result = prime * result + ((s == null) ? 0 : s.hashCode());
		 * return result;
		 */
		return EqualsHashCodeUtil.hashCode(id, name, isActive);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsHashCodeUtil.equals(this, obj,
				EmployeeEntity::getId,
				EmployeeEntity::getName,
				EmployeeEntity::getIsActive);
	}
}
