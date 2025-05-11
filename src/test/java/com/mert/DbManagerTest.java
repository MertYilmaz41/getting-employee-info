package com.mert;

import org.example.com.mert.DbManager;
import org.example.com.mert.Procedures;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DbManagerTest {
    private DbManager dbManager;

    @Before
    public void setUp() throws Exception {
        dbManager = new DbManager();
        dbManager.initializeDatabase();
    }

    @Test
    public void testCountDepartments() throws Exception {
        assertEquals(2, dbManager.callCountDepartments());
    }

    @Test
    public void testCountEmployees() throws Exception {
        assertEquals(3, dbManager.callCountEmployees());
    }

    @Test
    public void testCountEmployeesByDepartmentId() throws Exception {
        assertEquals(2, dbManager.callCountEmployeesByDepartmentId(1));
        assertEquals(1, dbManager.callCountEmployeesByDepartmentId(2));
    }
}
