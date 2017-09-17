package com.example.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.example.demo.controller.CountryControllerTest;
import com.example.demo.service.CountryServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ CountryControllerTest.class, CountryServiceTest.class })
public class RunSuite {

}
