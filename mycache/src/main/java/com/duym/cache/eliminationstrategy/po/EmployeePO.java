package com.duym.cache.eliminationstrategy.po;

import com.google.common.base.MoreObjects;

/**
 * @author duym
 * @version $ Id: EmployeePO, v 0.1 2023/04/27 18:38 duym Exp $
 */
public class EmployeePO {

    private final String name;

    private final String dept;

    private final String empID;

    public EmployeePO(String name, String dept, String empID) {
        this.name = name;
        this.dept = dept;
        this.empID = empID;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getEmpID() {
        return empID;
    }
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Name",this.getName()).add("Department",this.getDept())
                .add("EmployeeID",this.getEmpID()).toString();
    }
}
