/*
 * Copyright (c) [2018] [Jonathan McIntosh, Martin Staadecker, Ryan Zazo]
 */

package ev3.robot;

import common.logger.Logger;
import lejos.hardware.DeviceException;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;

class CustomEV3LargeMotor {
    private final static String LOG_TAG = CustomEV3LargeMotor.class.getSimpleName();

    private RegulatedMotor regulatedMotor;
    private final Port port;
    private Thread setupThread;

    CustomEV3LargeMotor(Port port) {
        this.port = port;
    }

    void setup(){
        setupThread = new Thread() {
            @Override
            public synchronized void run() {
                synchronized (CustomEV3LargeMotor.this) {
                    if (regulatedMotor == null) {
                        try {
                            regulatedMotor = new EV3LargeRegulatedMotor(port);
                        } catch (IllegalArgumentException | DeviceException e) {
                            Logger.warning(LOG_TAG, "Could not create motor at port " + port.toString());
                        }
                    }
                }
            }
        };

        setupThread.start();
    }

    boolean stillSettingUp(){
        return setupThread.isAlive();
    }

    RegulatedMotor get() {
        if (regulatedMotor == null){
            synchronized (this){
                if (regulatedMotor == null){
                    regulatedMotor = new EV3LargeRegulatedMotor(port);
                }
            }
        }

        return regulatedMotor;
    }
}
