package gameengine.script;

public final class AlertScript extends AbstractScript {
    private final transient String mess;

    public AlertScript(final String message) {
        super();
        mess = message;
    }

    @Override
    public String getMessage() {
        return mess;
    }
}
