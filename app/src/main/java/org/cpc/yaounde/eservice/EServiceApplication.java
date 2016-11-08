package org.cpc.yaounde.eservice;

import android.app.Application;
import android.provider.SyncStateContract;


import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.IO;

import org.cpc.yaounde.eservice.utils.Constants;

import java.net.URISyntaxException;

/**
 * Created by maelfosso on 11/7/16.
 */
public class EServiceApplication extends Application {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
