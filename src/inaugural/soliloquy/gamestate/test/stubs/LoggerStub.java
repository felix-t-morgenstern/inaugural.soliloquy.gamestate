package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.logger.specs.ILogger;

import java.io.IOException;

public class LoggerStub implements ILogger {
    @Override
    public void setLogfileLocation(String s) throws IOException {

    }

    @Override
    public void logException(Exception e, String s) throws IOException {

    }

    @Override
    public void logWarning(String s, String s1) throws IOException {

    }

    @Override
    public void logInfo(String s, String s1) throws IOException {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
