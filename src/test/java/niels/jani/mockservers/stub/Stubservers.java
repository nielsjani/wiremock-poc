package niels.jani.mockservers.stub;

class Stubservers {


    void start() {
        try {
            new AmiiboStub().start();
        } finally {
            new AmiiboStub().stop();
        }
    }

}
