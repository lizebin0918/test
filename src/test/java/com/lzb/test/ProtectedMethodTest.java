package com.lzb.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProtectedMethodTest {

    @Mock
    private ProtectedMethod protectedMethodUnderTest;

    @InjectMocks
    private ProtectedMethodExecutor protectedMethodExecutor;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testProtestedMethod() {
        // Setup
        // Run the test
        Mockito.doCallRealMethod().when(protectedMethodUnderTest).protestedMethod();
        // Verify the results
        protectedMethodExecutor.invoke();
    }
}
