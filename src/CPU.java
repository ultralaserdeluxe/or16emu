import java.util.HashMap;

/**
 * Created by Alexander on 2014-10-17.
 */
public interface CPU extends ObservableInterface {
    public void tick();

    public void reset();

    public HashMap<String, String> getState();
}
