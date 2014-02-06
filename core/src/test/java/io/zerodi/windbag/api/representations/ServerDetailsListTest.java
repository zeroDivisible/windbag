package io.zerodi.windbag.api.representations;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.zerodi.windbag.JsonMapper;

/**
 * Simple proof of concept test
 *
 * @author zerodi
 */
public class ServerDetailsListTest {

    private static final JsonMapper jsonMapper = JsonMapper.getInstance();

    private List<ServerDetail> serverDetails;
    private ServerDetail devvm;
    private ServerDetail machine2;

    @BeforeMethod
    public void setUp() throws Exception {
        serverDetails = new ArrayList<>();

        devvm = new ServerDetail();
        devvm.setName("devvm");
        devvm.setServerAddress("192.168.33.15");
        devvm.setServerPort("700");

        machine2 = new ServerDetail();
        machine2.setName("machine2");
        machine2.setServerAddress("192.168.33.20");
        machine2.setServerPort("701");

        serverDetails.add(devvm);
        serverDetails.add(machine2);
    }

    @Test
    public void itShouldSerializeServerDetailListToJson() throws IOException {
        // given
        ServerDetailsList serverDetailsList = ServerDetailsList.getInstance(serverDetails);

        // when
        String expected = jsonMapper.asJson(serverDetailsList);
        String actual = jsonMapper.jsonFixture("fixtures/servers.json");

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
