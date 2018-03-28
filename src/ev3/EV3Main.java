/*
 * Copyright (c) [2018] [Jonathan McIntosh, Martin Staadecker, Ryan Zazo]
 */

package ev3;

import common.Config;
import common.Logger;
import ev3.communication.PCConnection;
import ev3.communication.PCDataSender;

import java.io.OutputStream;

final class EV3Main {
    private static final String LOG_TAG = EV3Main.class.getSimpleName();

    public static void main(String[] args) {
        if (Config.currentMode == Config.Mode.DUAL || Config.currentMode == Config.Mode.SIM) {
            OutputStream outputStream = PCConnection.getConnection();

            if (outputStream == null) {
                Logger.error(LOG_TAG, "Failed to connect to PC. Stopping program");
                return;
            }

            PCDataSender.init(outputStream); //Try to getConnection to pc
        }

        Brain.start();
//        Brick.waitForUserConfirmation(); //And wait for complete
    }
}