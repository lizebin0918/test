package com.lzb.junit5;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface IBaseTest {
  @BeforeAll
  default void beforeAll() {
    // here common setup for all tests
    System.out.println("--- Before starting tests --- ");
  }
  
  @AfterAll
  default void afetrAll() {
    // here cleanup setup after all tests completes
    System.out.println("--- After tests completed ---");
  }  
}