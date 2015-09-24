package com.photobox.services.foundation.testing;

import com.photobox.services.foundation.server.FoundationConfiguration;
import com.photobox.services.foundation.server.FoundationService;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

/**
 * This JUnit Rule for starting and stopping your Foundation application at
 * the start and end of a test class.
 */
public class FoundationAppRule extends DropwizardAppRule<FoundationConfiguration> {

  public FoundationAppRule(String configPath) {
    super(FoundationService.DropWizardLauncher.class, ResourceHelpers.resourceFilePath(configPath));
  }

}
