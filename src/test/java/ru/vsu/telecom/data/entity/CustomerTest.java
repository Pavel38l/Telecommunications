package ru.vsu.telecom.data.entity;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * @author Pavel_Burdyug
 */
public class CustomerTest {

    @Test
    public void calcAge() {
        Customer testCustomer = new Customer();
        testCustomer.setDateOfBirth(LocalDate.of(2001, 7, 11));
        int expected =  testCustomer.calcAge();
        int actual = 19;
        Assert.assertEquals(expected, actual);
    }
}