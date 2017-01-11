package org.openbaton.drivers.openstack4j.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openbaton.catalogue.mano.common.DeploymentFlavour;
import org.openbaton.catalogue.nfvo.NFVImage;
import org.openbaton.catalogue.nfvo.Network;
import org.openbaton.catalogue.nfvo.Server;
import org.openbaton.catalogue.nfvo.VimInstance;
import org.openbaton.drivers.openstack4j.OpenStack4JDriver;
import org.openbaton.exceptions.VimDriverException;
import org.openstack4j.api.OSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by lto on 11/01/2017. */
public class OpenStack4JDriverTest {
  private static Properties properties;
  private static Logger log = LoggerFactory.getLogger(OpenStack4JDriverTest.class);
  private static OpenStack4JDriver osd;
  private static VimInstance vimInstance;

  @BeforeClass
  public static void init() throws IOException {
    properties = new Properties();
    try {
      properties.load(
          new InputStreamReader(
              OpenStack4JDriverTest.class.getResourceAsStream("/test.properties")));
    } catch (IOException e) {
      log.error("Missing test.properties file");
      throw e;
    }
    osd = new OpenStack4JDriver();
    vimInstance = getVimInstance();
  }

  @Test
  public void authenticate() throws VimDriverException {
    OSClient.OSClientV3 os = osd.authenticate(vimInstance);
    log.debug("Token is: " + os.getToken());
    assert os.getToken() != null;
  }

  @Test
  public void launchInstance() throws VimDriverException {}

  @Test
  public void listImages() throws VimDriverException {
    try {
      for (NFVImage image : osd.listImages(vimInstance)) {
        log.info(image.toString());
      }
    } catch (VimDriverException e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Test
  public void listServer() throws VimDriverException {
    for (Server server : osd.listServer(vimInstance)) {
      log.info(server.toString());
    }
  }

  @Test
  public void listNetworks() throws VimDriverException {
    try {
      for (Network network : osd.listNetworks(vimInstance)) {
        log.info(network.toString());
      }
    } catch (VimDriverException e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Test
  public void listFlavors() throws VimDriverException {
    try {
      for (DeploymentFlavour flavour : osd.listFlavors(vimInstance)) {
        log.info(flavour.toString());
      }
    } catch (VimDriverException e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Test
  public void launchInstanceAndWait() throws VimDriverException {}

  @Test
  public void launchInstanceAndWait1() throws VimDriverException {}

  @Test
  public void deleteServerByIdAndWait() throws VimDriverException {}

  @Test
  public void createNetwork() throws VimDriverException {}

  @Test
  public void addFlavor() throws VimDriverException {}

  @Test
  public void addImage() throws VimDriverException {}

  @Test
  public void addImage1() throws VimDriverException {}

  @Test
  public void updateImage() throws VimDriverException {}

  @Test
  public void copyImage() throws VimDriverException {}

  @Test
  public void deleteImage() throws VimDriverException {}

  @Test
  public void updateFlavor() throws VimDriverException {}

  @Test
  public void deleteFlavor() throws VimDriverException {}

  @Test
  public void createSubnet() throws VimDriverException {}

  @Test
  public void updateNetwork() throws VimDriverException {}

  @Test
  public void updateSubnet() throws VimDriverException {}

  @Test
  public void getSubnetsExtIds() throws VimDriverException {}

  @Test
  public void deleteSubnet() throws VimDriverException {}

  @Test
  public void deleteNetwork() throws VimDriverException {}

  @Test
  public void getNetworkById() throws VimDriverException {}

  @Test
  public void getQuota() throws VimDriverException {
    log.info(osd.getQuota(vimInstance).toString());
  }

  public static void main(String[] args) throws VimDriverException {

    OpenStack4JDriver osd = new OpenStack4JDriver();
    VimInstance vimInstance = getVimInstance();
    osd.authenticate(vimInstance);
  }

  private static VimInstance getVimInstance() {
    VimInstance vimInstance = new VimInstance();
    vimInstance.setName(properties.getProperty("vim.instance.name"));
    vimInstance.setAuthUrl(properties.getProperty("vim.instance.url"));
    vimInstance.setUsername(properties.getProperty("vim.instance.username"));
    vimInstance.setPassword(properties.getProperty("vim.instance.password"));
    vimInstance.setTenant(properties.getProperty("vim.instance.project.id"));
    return vimInstance;
  }
}
