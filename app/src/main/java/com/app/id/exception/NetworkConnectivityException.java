package com.app.id.exception;

public class NetworkConnectivityException extends Exception {
    @SuppressWarnings("ThrowableInstanceNeverThrown")
    public static final NetworkConnectivityException INSTANCE = new NetworkConnectivityException();
}
