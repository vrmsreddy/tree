package com.ms.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ms.entity.EmployeeEntity;

/**
 * TestCases EqualsHashCodeUtils
 *
 */
public class EqualsHashCodeTest {

    @Test
    public void deepEqualsTest(){
    	EmployeeEntity myClass1 = createEmployee();
    	EmployeeEntity myClass2 = createEmployee();
    	assertTrue(myClass1.equals(myClass2));
    }
    
    @Test
    public void deepEqualsNagativeTest(){
    	EmployeeEntity myClass1 = createEmployee();
    	EmployeeEntity myClass2 = createEmployee();
    	myClass2.setId(22);
    	assertFalse(myClass1.equals(myClass2));
    }
    
    @Test
    public void hashCodeTest(){
    	EmployeeEntity myClass1 = createEmployee();
    	EmployeeEntity myClass2 = createEmployee();
    	System.out.println("hash1="+myClass1.hashCode()+"\nhash2="+myClass2.hashCode());
    	assertTrue(myClass1.hashCode() == myClass2.hashCode());
    }
    
    @Test
    public void hashCodeNagativeTest(){
    	EmployeeEntity myClass1 = createEmployee();
    	EmployeeEntity myClass2 = createEmployee();
    	myClass2.setId(22);
    	System.out.println("hash1="+myClass1.hashCode()+"\nhash2="+myClass2.hashCode());
    	assertFalse(myClass1.hashCode() == myClass2.hashCode());
    }

    private  EmployeeEntity createEmployee(){
    	EmployeeEntity employee = new EmployeeEntity(0, true, "sampleName");
    	 return employee;
    }
}