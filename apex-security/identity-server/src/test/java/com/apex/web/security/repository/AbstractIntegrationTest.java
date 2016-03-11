package com.apex.web.security.repository;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apex.web.security.Application.RepositoryConfiguration;

/**
 * Base class to implement transactiona integration tests using the root
 * application configuration.
 * 
 * @author Oliver Gierke
 */
// @Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RepositoryConfiguration.class)
public abstract class AbstractIntegrationTest {
}