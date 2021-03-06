package MadTeam;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MadConnection implements SerialPortEventListener {

    private static final String GREETINGS = "greetingsLouis";
    private static final String GREETINGS_RESPONSE = "greetingsMadProgrammer";
    private String response = "", responseClean = "";
    private boolean STOP = false;
    /**
     * The output stream to the port
     */
    private OutputStream output = null;
    private InputStream input = null;
    boolean connected = false;
    SerialPort serialPort;
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;
    public static final int TIME_OUT_RESPONSE = 2000;
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;

    public MadConnection() {
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        // iterate through, looking for the port
        ArrayList<CommPortIdentifier> ports = new ArrayList();
        System.out.println("**********  PORTS **********");
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            System.out.println("PORT: " + currPortId.getName());
            ports.add(currPortId);
        }
        System.out.println("**********        **********");

        for (CommPortIdentifier port : ports) {
            if (!initializeArduinoConnection(port)) {
                System.out.println("PORT: " + port.getName() + " failed to connect");
            } else {
                System.out.println("*****!!!!Engage!!!!*****");
                connected = true;
                this.STOP = false;
                break;
            }
        }
    }

    private boolean initializeArduinoConnection(CommPortIdentifier portId) {

        if (portId == null) {
            System.out.println("Could not find COM port.");
            return false;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass()
                    .getName(), TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            try {
                //input = new SerialReader(in);
                serialPort.addEventListener(this);
                System.out.println("listeners attached to "+portId.getName()+"!");
            } catch (TooManyListenersException e) {
                System.out.println("too many listeners");
                return false;
            }
            serialPort.notifyOnDataAvailable(true);
            // open the streams
            output = serialPort.getOutputStream();
            input = serialPort.getInputStream();
            Thread.sleep(2000);
            sendData(GREETINGS);
            System.out.println("HANDSHAKE ==> " + responseClean + " ?= " + GREETINGS_RESPONSE);
            return responseClean.equals(GREETINGS_RESPONSE);
        } catch (PortInUseException | UnsupportedCommOperationException | IOException e) {
            System.out.println("MAIN ERROR: " + e.getMessage());
            return false;
        } catch (InterruptedException ex) {
            Logger.getLogger(MadConnection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public void print(MainWindow main, String madtext) {
        Date d1 = new Date();
        //send dimensions
        sendData("-" + main.d_A + "_" + main.d_B + "_" + main.d_C + "_" + main.d_D + "_" + main.vel_sheet + "_" + main.vel_car + "_" + main.sleep_time+ "_" + main.dist_carro);
        main.setProgressValue(1);

        //send document
        String[] lines = madtext.split("\n");
        float scale = 99 / (float) lines.length;
        System.out.println("PROGRESS SCALE " + scale);
        float progress = 4;//AAAAAAAAAAAAAAAAAAAAA
        for (String line : lines) {
            sendData("+" + line, 30000);
            progress += scale;
            System.out.println("PROGRESS " + progress);
            main.setProgressValue((int) (progress));
            if (STOP) {
                break;
            }
        }
        long seconds = (new Date().getTime() - d1.getTime()) / 1000;
        System.out.println("Document Printed in " + seconds + " seg(or " + (seconds / 60) + " minutes)");
        main.setProgressValue(100);
        STOP = false;
        main.printed();
    }

    public void sendData(String data) {
        sendData(data, TIME_OUT);
    }

    public void sendData(String data, int max) {
        try {
            output.write(data.getBytes());
            System.out.println("sended: " + data);
            int counter = 0;
            while (!response.contains(">>")) {
                try {
                    Thread.sleep(20);
                    counter += 20;
                    if (counter > max) {
                        break;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(MadConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (response.contains(">>")) {
                responseClean = response.substring(response.indexOf("<<")+2, response.length() - 2);
                System.out.println("RESPONSE: "+response);
                response = "";
                
                System.out.println("CLEAN RESPONSE: " + responseClean);
            } else {
                System.out.println("ERROR: NOT RESPONSE");
            }
        } catch (IOException e) {
            System.out.println("Error sending data " + e);
        }
    }

    public void sendDataT(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendData(data);
            }
        }).start();
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            /*case SerialPortEvent.BI:
             case SerialPortEvent.OE:
             case SerialPortEvent.FE:
             case SerialPortEvent.PE:
             case SerialPortEvent.CD:
             case SerialPortEvent.CTS:
             case SerialPortEvent.DSR:
             case SerialPortEvent.RI:
             case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
             System.out.println("event.getEventType()");
             break; */
            case SerialPortEvent.DATA_AVAILABLE:
                byte[] readBuffer = new byte[50];
                try {
                    while (input.available() > 0) {
                        int numBytes = input.read(readBuffer);
                    }
                    String recibido = new String(readBuffer).replace(" ", "").replace("\n", "");
                    System.out.println("recibi "+recibido);
                    response += recibido;
                    //System.exit(1);
                } catch (IOException e) {
                    System.out.println("ERROR serialEvent: " + e);
                    connected = false;
                    this.STOP = true;
                    close();
                }
                break;
        }
    }

    public void close() {
        try {
            if (input != null) {
                input.close();
            }

            if (output != null) {
                output.close();
            }

            if (serialPort != null) {
                serialPort.close();
            }

        } catch (Exception ex) {
            System.out.println("MadConnection.close error:" + ex);
        }
    }

    /**
     * Stop Current Printing
     */
    public void stop() {
        this.STOP = true;
    }

    public String getResponse() {

        return response;
    }

    /**
     * Set the response attribute empty for treat the next msg
     */
    public void cleanResponse() {
        response = "";
    }

    /**
     *
     * @return boolean state of the connection to the printer
     */
    public boolean isConnected() {
        return connected;
    }
}
