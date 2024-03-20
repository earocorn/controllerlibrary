package com.alexalmanza.controller.wii;

import motej.event.AccelerometerEvent;
import motej.event.CoreButtonEvent;
import net.java.games.input.Component;

public class WiiComponents {

    private CoreButtonEvent coreButtonEvent;
    private AccelerometerEvent accelerometerEvent;

    public WiiComponents(CoreButtonEvent coreButtonEvent, AccelerometerEvent accelerometerEvent) {

    }

    public class WiiButtonAComponent implements Component {

        @Override
        public Identifier getIdentifier() {
            return Identifier.Button.A;
        }

        @Override
        public boolean isRelative() {
            return false;
        }

        @Override
        public boolean isAnalog() {
            return false;
        }

        @Override
        public float getDeadZone() {
            return 0;
        }

        @Override
        public float getPollData() {
            return coreButtonEvent.isButtonAPressed() ? 1.0f : 0.0f;
        }

        @Override
        public String getName() {
            return Identifier.Button.A.getName();
        }

        public class WiiButtonBComponent implements Component {

            @Override
            public Identifier getIdentifier() {
                return Identifier.Button.B;
            }

            @Override
            public boolean isRelative() {
                return false;
            }

            @Override
            public boolean isAnalog() {
                return false;
            }

            @Override
            public float getDeadZone() {
                return 0;
            }

            @Override
            public float getPollData() {
                return coreButtonEvent.isButtonBPressed() ? 1.0f : 0.0f;
            }

            @Override
            public String getName() {
                return Identifier.Button.B.getName();
            }
        }


        public class WiiButtonMinusComponent implements Component {
            @Override
            public Identifier getIdentifier() {
                return WiiIdentifier.MINUS;
            }

            @Override
            public boolean isRelative() {
                return false;
            }

            @Override
            public boolean isAnalog() {
                return false;
            }

            @Override
            public float getDeadZone() {
                return 0;
            }

            @Override
            public float getPollData() {
                return coreButtonEvent.isButtonMinusPressed() ? 1.0f : 0.0f;
            }

            @Override
            public String getName() {
                return WiiIdentifier.MINUS.getName();
            }
        }
        public class WiiButtonPlusComponent implements Component {

            @Override
            public Identifier getIdentifier() {
                return WiiIdentifier.PLUS;
            }

            @Override
            public boolean isRelative() {
                return false;
            }

            @Override
            public boolean isAnalog() {
                return false;
            }

            @Override
            public float getDeadZone() {
                return 0;
            }

            @Override
            public float getPollData() {
                return coreButtonEvent.isButtonPlusPressed() ? 1.0f : 0.0f;
            }

            @Override
            public String getName() {
                return WiiIdentifier.PLUS.getName();
            }
        }
        public class WiiButtonHomeComponent implements Component {

            @Override
            public Identifier getIdentifier() {
                return WiiIdentifier.HOME;
            }

            @Override
            public boolean isRelative() {
                return false;
            }

            @Override
            public boolean isAnalog() {
                return false;
            }

            @Override
            public float getDeadZone() {
                return 0;
            }

            @Override
            public float getPollData() {
                return coreButtonEvent.isButtonHomePressed() ? 1.0f : 0.0f;
            }

            @Override
            public String getName() {
                return WiiIdentifier.HOME.getName();
            }
        }
        public class WiiButtonDPadComponent implements Component {

            @Override
            public Identifier getIdentifier() {
                return Identifier.Axis.POV;
            }

            @Override
            public boolean isRelative() {
                return false;
            }

            @Override
            public boolean isAnalog() {
                return false;
            }

            @Override
            public float getDeadZone() {
                return 0;
            }

            @Override
            public float getPollData() {
                if(coreButtonEvent.isDPadDownPressed()) {
                    return POV.DOWN;
                } else if(coreButtonEvent.isDPadLeftPressed()){
                    return POV.LEFT;
                } else if(coreButtonEvent.isDPadRightPressed()) {
                    return POV.RIGHT;
                } else if(coreButtonEvent.isDPadUpPressed()) {
                    return POV.UP;
                } else return POV.OFF;
            }

            @Override
            public String getName() {
                return Identifier.Axis.POV.getName();
            }
        }
        public class WiiButtonNoneComponent implements Component {

            @Override
            public Identifier getIdentifier() {
                return Identifier.Button.DEAD;
            }

            @Override
            public boolean isRelative() {
                return false;
            }

            @Override
            public boolean isAnalog() {
                return false;
            }

            @Override
            public float getDeadZone() {
                return 0;
            }

            @Override
            public float getPollData() {
                return coreButtonEvent.isNoButtonPressed() ? 1.0f : 0.0f;
            }

            @Override
            public String getName() {
                return Identifier.Button.DEAD.getName();
            }
        }
        public class WiiButtonTwoComponent implements Component {

            @Override
            public Identifier getIdentifier() {
                return Identifier.Button._2;
            }

            @Override
            public boolean isRelative() {
                return false;
            }

            @Override
            public boolean isAnalog() {
                return false;
            }

            @Override
            public float getDeadZone() {
                return 0;
            }

            @Override
            public float getPollData() {
                return coreButtonEvent.isButtonTwoPressed() ? 1.0f : 0.0f;
            }

            @Override
            public String getName() {
                return Identifier.Button._2.getName();
            }
        }
        public class WiiButton1Component implements Component {

            @Override
            public Identifier getIdentifier() {
                return Identifier.Button._1;
            }

            @Override
            public boolean isRelative() {
                return false;
            }

            @Override
            public boolean isAnalog() {
                return false;
            }

            @Override
            public float getDeadZone() {
                return 0;
            }

            @Override
            public float getPollData() {
                return coreButtonEvent.isButtonOnePressed() ? 1.0f : 0.0f;
            }

            @Override
            public String getName() {
                return Identifier.Button._1.getName();
            }
        }
    }

}
