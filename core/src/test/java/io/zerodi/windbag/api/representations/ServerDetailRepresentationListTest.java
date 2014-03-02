package io.zerodi.windbag.api.representations;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.zerodi.windbag.core.Protocol;
import io.zerodi.windbag.core.protocol.Connection;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.zerodi.windbag.JsonMapper;

/**
 * Simple proof of concept test
 *
 * @author zerodi
 */
public class ServerDetailRepresentationListTest {

    private static final JsonMapper jsonMapper = JsonMapper.getInstance();

    private List<ServerDetailRepresentation> serverDetails;
    private ServerDetail devvm;
    private ServerDetail machine2;

    @BeforeMethod
    public void setUp() throws Exception {
        serverDetails = new ArrayList<>();

        devvm = new ServerDetail();
        devvm.setName("devvm");
        devvm.setServerAddress("192.168.33.15");
        devvm.setServerPort(700);
        devvm.setProtocol(Protocol.EPP);

        machine2 = new ServerDetail();
        machine2.setName("machine2");
        machine2.setServerAddress("192.168.33.20");
        machine2.setServerPort(701);
        machine2.setProtocol(Protocol.EPP);

        serverDetails.add(ServerDetailRepresentation.getInstance(devvm, new ArrayList<Connection>()));
	    serverDetails.add(ServerDetailRepresentation.getInstance(machine2, new ArrayList<Connection>()));
    }

    @Test
    public void itShouldSerializeServerDetailListToJson() throws IOException {
        // given
        ServerDetailRepresentationList serverDetailRepresentationList = ServerDetailRepresentationList.getInstance(serverDetails);

        // when
        String expected = jsonMapper.asJson(serverDetailRepresentationList);
        String actual = jsonMapper.jsonFixture("fixtures/servers.json");

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
