package io.zerodi.epp.core;

/**
 * @author zerodi
 */
public class EppServerDetails {

    private final String serverAddress;
    private final int serverPort;

    private EppServerDetails(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public static EppServerDetails getInstance(String serverAddress, int serverPort) {
        return new EppServerDetails(serverAddress, serverPort);
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }
}
