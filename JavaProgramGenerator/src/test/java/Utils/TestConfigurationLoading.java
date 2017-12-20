package Utils;

import org.junit.Test;

public class TestConfigurationLoading {

    @Test
    public void testConfigurationLoad(){
       Configuration config =  Utilities.parseConfigFile("src/main/resources/config.xml");
        System.out.println(config);
    }
}
