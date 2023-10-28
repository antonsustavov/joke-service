package com.sustav;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseFunctionalTest {

  @LocalServerPort
  protected int serverPort;
  @LocalManagementPort
  protected int managementPort;

}
