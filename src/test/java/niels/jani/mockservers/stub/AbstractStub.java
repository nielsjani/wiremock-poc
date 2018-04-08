package niels.jani.mockservers.stub;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.opentable.extension.BodyTransformer;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public abstract class AbstractStub {

    private WireMockServer wireMockServer;

    void start(){
        if(!wireMockServer.isRunning()){
            wireMockServer.start();
        }
    }

    void stop(){
        if(wireMockServer.isRunning()){
            wireMockServer.stop();
        }
    }

    AbstractStub(){
        wireMockServer = new WireMockServer(wireMockConfig().port(getPort()).extensions(new BodyTransformer()));
        configure();
    }

    protected WireMockServer getWireMockServer(){
        return wireMockServer;
    }

    protected abstract int getPort();

    protected abstract void configure();
}
