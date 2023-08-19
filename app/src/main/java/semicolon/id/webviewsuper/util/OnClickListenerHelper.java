package semicolon.id.webviewsuper.util;

/**
 * OnClick Listener Helper to handle any Passing Data type
 */

public class OnClickListenerHelper {

    public interface OnClickGridMenuListener {
        void onClick(PassingData mData);
    }

    public static class PassingData {
        public String f1s;
    }
}
