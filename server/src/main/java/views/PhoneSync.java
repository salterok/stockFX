package views;

import app.Config;
import app.LocalNetworkConnector;
import app.PhoneSyncStage;
import utils.RestletUtil;

import java.util.ConcurrentModificationException;

/**
 * Created by salterok on 07.06.2015.
 */
public class PhoneSync extends BaseNavigableView {
    private PhoneSyncStage phoneSyncStage = new PhoneSyncStage();
    private LocalNetworkConnector localNetworkConnector;

    public PhoneSync() throws Exception {}

    @Override
    protected void onLoad() {
        localNetworkConnector = new LocalNetworkConnector(Config.getInstance().terminalSync.discoveryListenPort);
    }

    @Override
    protected void onShown() {
        //localNetworkConnector.start();
        try {
            RestletUtil.startRest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onHide() {
        localNetworkConnector.interrupt();
        try {
            RestletUtil.stopRest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
