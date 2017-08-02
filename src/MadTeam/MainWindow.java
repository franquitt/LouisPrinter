package MadTeam;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.KeyboardFocusManager;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author FrancoMain
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    private Font myFont;
    public String DIC = "Es-Es-g1.utb";
    public final String BETWEEN_BRAILLE_DOTS = "2",
            BETWEEN_BRAILLE_CHARS = "3",
            BETWEEN_BRAILLE_LINES_DOTS = "4",
            BETWEEN_BRAILLE_LINES = "5",
            BETWEEN_BRAILLE_SHEETS = "6",
            END_PRINT = "7",
            BETWEEN_BRAILLE_DOTS_ZERO = "8";//BETWEEN_BRAILLE_CHARS+BETWEEN_BRAILLE_DOTS

    public int MAX_CHARS_PER_LINE = 32;
    public int MAX_LINES_PER_SHEET = 32;
    private String bin = "";
    private MadConnection connection;
    private String fill = "";
    public int d_A = 25, d_B = 35, d_C = 25, d_D = 50, vel_sheet = 60, vel_car = 90, sleep_time = 100;

    public MainWindow() {
        initComponents();
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/FreeMono.ttf")));
            myFont = new Font("FreeMono", Font.BOLD, 37);
        } catch (IOException | FontFormatException e) {
            System.out.println("Warning! " + e.toString());
        }
        frmMsg.setSize(320, 101);

        txtBrailleText.setFont(myFont);
        //txtBrailleText.setText("\u2813\u2815\u2807\u2801");
        txtplaintext.setLineWrap(true);
        txtplaintext.setWrapStyleWord(true);
        frmMsg.setSize(320, 101);
        
        initDimensionsFolder();
        loadDistancesBraille();
        setDistancesBraille();
        
        for (int i = 0; i < MAX_CHARS_PER_LINE * 6; i++) {
            fill += "0";
        }
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new DispatcherListener(this));
        try {
            BufferedImage myPicture = ImageIO.read(new File("assets/img/general.png"));
            lblDots.setIcon(new ImageIcon(myPicture));
            lblDots.setText("");
        } catch (Exception e) {
            System.out.println("Error en initMain: " + e);
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frmMsg = new javax.swing.JFrame();
        lblMsg = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        btnPrint = new javax.swing.JButton();
        btnConvertir = new javax.swing.JButton();
        btnOpenFile = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtplaintext = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtBrailleText = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        btnConnectPrinter = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        lblDots = new javax.swing.JLabel();
        lblDistancias = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtA = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtB = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtC = new javax.swing.JTextField();
        txtD = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMaxChars = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMaxLines = new javax.swing.JTextField();
        btnApplyDistances = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtvelsheets = new javax.swing.JTextField();
        txtvelcar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtsleep = new javax.swing.JTextField();
        btnCancelPrint = new javax.swing.JButton();
        btnTest = new javax.swing.JButton();
        comboConfigFile = new javax.swing.JComboBox<>();
        btnAddConfigFile = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();

        lblMsg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMsg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMsg.setText("Procesando..");

        javax.swing.GroupLayout frmMsgLayout = new javax.swing.GroupLayout(frmMsg.getContentPane());
        frmMsg.getContentPane().setLayout(frmMsgLayout);
        frmMsgLayout.setHorizontalGroup(
            frmMsgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmMsgLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addContainerGap())
        );
        frmMsgLayout.setVerticalGroup(
            frmMsgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmMsgLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Louis");
        setName("Main"); // NOI18N

        btnPrint.setText("Imprimir");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnConvertir.setText("Convertir");
        btnConvertir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvertirActionPerformed(evt);
            }
        });

        btnOpenFile.setText("Abrir Archivo");
        btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenFileActionPerformed(evt);
            }
        });

        txtplaintext.setColumns(20);
        txtplaintext.setRows(5);
        jScrollPane1.setViewportView(txtplaintext);

        jTabbedPane1.addTab("Original", jScrollPane1);

        txtBrailleText.setEditable(false);
        txtBrailleText.setColumns(20);
        txtBrailleText.setRows(5);
        jScrollPane2.setViewportView(txtBrailleText);

        jTabbedPane1.addTab("Braille", jScrollPane2);

        btnConnectPrinter.setText("Conectar Impresora");
        btnConnectPrinter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectPrinterActionPerformed(evt);
            }
        });

        lblDots.setText("Dots");

        lblDistancias.setText("Distancias");

        jLabel1.setText("A");

        txtA.setText("10");

        jLabel2.setText("B");

        txtB.setText("20");

        jLabel3.setText("C");

        jLabel4.setText("D");

        txtC.setText("10");

        txtD.setText("20");

        jLabel5.setText("Max Chars Line");

        txtMaxChars.setText("32");

        jLabel6.setText("Max Lines Sheet");

        txtMaxLines.setText("32");

        btnApplyDistances.setText("Aplicar");
        btnApplyDistances.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyDistancesActionPerformed(evt);
            }
        });

        jLabel7.setText("Vel motor Sheets");

        txtvelcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtvelcarActionPerformed(evt);
            }
        });

        jLabel8.setText("Vel motor car");

        jLabel9.setText("Sleep in Ops");

        btnCancelPrint.setText("Cancelar Impresion");
        btnCancelPrint.setEnabled(false);
        btnCancelPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelPrintActionPerformed(evt);
            }
        });

        btnTest.setText("Test");
        btnTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestActionPerformed(evt);
            }
        });

        comboConfigFile.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAddConfigFile.setText("+");
        btnAddConfigFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddConfigFileActionPerformed(evt);
            }
        });

        jLabel10.setText("Archivo de configuracion");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnConnectPrinter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelPrint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTest))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblDots, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel3)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(txtC, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel1)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(24, 24, 24)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel2)
                                                    .addComponent(jLabel4))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(txtB, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(59, 59, 59))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(27, 27, 27)
                                                .addComponent(txtMaxChars, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(22, 22, 22)
                                                .addComponent(txtMaxLines, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLabel7)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(txtvelsheets, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel8)
                                                        .addComponent(jLabel9))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txtsleep, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                                                        .addComponent(txtvelcar))))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnAddConfigFile)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(comboConfigFile, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnApplyDistances))))
                            .addComponent(lblDistancias))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConnectPrinter)
                    .addComponent(btnCancelPrint)
                    .addComponent(btnTest))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDistancias)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaxChars, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtMaxLines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(txtD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtvelsheets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtvelcar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtsleep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboConfigFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnApplyDistances)
                            .addComponent(btnAddConfigFile))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblDots, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))))
        );

        jTabbedPane1.addTab("Impresion", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 493, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 264, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Imagen", jPanel2);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnOpenFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConvertir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOpenFile)
                    .addComponent(btnConvertir)
                    .addComponent(btnPrint))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public int getMaxCharsLine() {
        return MAX_CHARS_PER_LINE;
    }

    public int getMaxLinesSheet() {
        return MAX_LINES_PER_SHEET;
    }
    public void printed(){
        btnCancelPrint.setEnabled(false);
        btnPrint.setEnabled(true);
    }
    public void openFile() {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(rootPane);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println(file.getAbsolutePath());
            if (file.getName().endsWith("pdf")) {

                PDFParser parser = null;
                PDDocument pdDoc = null;
                COSDocument cosDoc = null;
                PDFTextStripper pdfStripper;
                try {
                    parser = new PDFParser(new RandomAccessFile(file, "r"));
                    parser.parse();
                    cosDoc = parser.getDocument();
                    pdfStripper = new PDFTextStripper();
                    pdDoc = new PDDocument(cosDoc);
                    String parsedText = pdfStripper.getText(pdDoc);
                    txtplaintext.setText(parsedText);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    try {
                        if (cosDoc != null) {
                            cosDoc.close();
                        }
                        if (pdDoc != null) {
                            pdDoc.close();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            } else {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    txtplaintext.setText("\u0645");
                    if ((line = br.readLine()) != null) {
                        txtplaintext.append(line);
                    }
                    while ((line = br.readLine()) != null) {
                        txtplaintext.append("\n" + line);
                    }
                    br.close();
                    jTabbedPane1.setSelectedIndex(0);
                } catch (Exception e) {
                }
            }
        }
    }
    private void btnOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenFileActionPerformed
        openFile();
    }//GEN-LAST:event_btnOpenFileActionPerformed

    private void btnConvertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConvertirActionPerformed
        translate();
    }//GEN-LAST:event_btnConvertirActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        connectPrinter(true);
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestActionPerformed

        String text="";
        for(int i=0;i< MAX_CHARS_PER_LINE;i++)
        text=text+"y";
        for(int i=1;i< MAX_LINES_PER_SHEET;i++)
        text=text+"\np";
        translate(text);
        connectPrinter(true);
    }//GEN-LAST:event_btnTestActionPerformed

    private void btnCancelPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelPrintActionPerformed
        btnCancelPrint.setEnabled(false);
        btnPrint.setEnabled(true);
        connection.stop();
        setProgressValue(0);
        //199 vs
    }//GEN-LAST:event_btnCancelPrintActionPerformed

    private void txtvelcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtvelcarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtvelcarActionPerformed

    private void btnApplyDistancesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyDistancesActionPerformed
        checkDistances();
    }//GEN-LAST:event_btnApplyDistancesActionPerformed

    private void btnConnectPrinterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectPrinterActionPerformed
        connectPrinter(false);
    }//GEN-LAST:event_btnConnectPrinterActionPerformed

    private void btnAddConfigFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddConfigFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddConfigFileActionPerformed
    public void connectPrinter(boolean print) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                jTabbedPane1.setSelectedIndex(2);
                if (connection == null) {
                    btnConnectPrinter.setEnabled(false);
                    btnConnectPrinter.setText("Conectando..");
                    connection = new MadConnection();
                    if (connection.isConnected()) {
                        btnPrint.setEnabled(true);
                        connection.sendData("led_on");
                        btnConnectPrinter.setText("Conectado");
                    } else {
                        connection.close();
                        btnConnectPrinter.setEnabled(true);
                        btnConnectPrinter.setText("Conectar Impresora");
                        connection = null;
                    }
                }
                if (print) {

                    print();
                }
            }
        }).start();
    }

    public void checkDistances() {

        if (checkValue(txtA) && checkValue(txtB) && checkValue(txtC) && checkValue(txtD) && checkValue(txtMaxChars) && checkValue(txtMaxLines) && checkValue(txtvelcar) && checkValue(txtvelsheets) && checkValue(txtsleep)) {
            try {
                PrintWriter out = new PrintWriter("dist.lou");
                out.println(txtA.getText());
                out.println(txtB.getText());
                out.println(txtC.getText());
                out.println(txtD.getText());
                out.println(txtMaxChars.getText());
                out.println(txtMaxLines.getText());
                out.println(txtvelsheets.getText());
                out.println(txtvelcar.getText());
                out.println(txtsleep.getText());
                out.close();
                loadDistancesBraille();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean checkValue(JTextField txt) {
        if (txt.getText().equals("")) {
            return false;
        }
        try {
            int number = Integer.parseInt(txt.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
    public void setProgressValue(int value) {
        jProgressBar1.setValue(value);
    }
    public void translate(){
        translate(txtplaintext.getText());
    }
    public void translate(String base) {
        txtBrailleText.setText("");
        txtBrailleText.setFont(myFont);
        
        if (base.equals("")) {
            return;
        }
        txtBrailleText.setText(Braille.translate(this, base));
        System.out.println("##Conversion Finalizada");
        jTabbedPane1.setSelectedIndex(1);
    }

    public void print() {
        jTabbedPane1.setSelectedIndex(2);
        if (connection != null) {
            if (connection.isConnected()) {
                btnCancelPrint.setEnabled(true);
                btnPrint.setEnabled(false);
                setProgressValue(0);
                String braille = txtBrailleText.getText().replace("\t", "    ");
                String mad = Braille.getMadText(braille, this);
                connection.print(this, mad);
            }
        }
    }
    private void initDimensionsFolder(){
        File folder= new File("dims");
        if(!folder.exists()){
            folder.mkdir();
        }
    }
    private void setDistancesBraille() {
        txtA.setText(d_A + "");
        txtB.setText(d_B + "");
        txtC.setText(d_C + "");
        txtD.setText(d_D + "");
        txtMaxChars.setText(MAX_CHARS_PER_LINE + "");
        txtMaxLines.setText(MAX_LINES_PER_SHEET + "");
        txtvelsheets.setText(vel_sheet + "");
        txtvelcar.setText(vel_car + "");
        txtsleep.setText(sleep_time + "");
    }

    private void loadDistancesBraille() {
        try {
            FileReader inputFile = new FileReader("dist.lou");
            BufferedReader bufferReader = new BufferedReader(inputFile);
            int[] lines = new int[9];
            String line;
            int index = 0;
            while ((line = bufferReader.readLine()) != null) {
                lines[index] = Integer.parseInt(line);
                index++;
            }
            //Close the buffer reader
            bufferReader.close();
            inputFile.close();
            d_A = lines[0];
            d_B = lines[1];
            d_C = lines[2];
            d_D = lines[3];
            MAX_CHARS_PER_LINE = lines[4];
            MAX_LINES_PER_SHEET = lines[5];
            vel_sheet = lines[6];
            vel_car = lines[7];
            sleep_time = lines[8];
        } catch (Exception e) {
            System.out.println("Error while reading file line by line:" + e.getMessage());
            System.out.println("Error loadPreferences: " + e.toString());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddConfigFile;
    private javax.swing.JButton btnApplyDistances;
    private javax.swing.JButton btnCancelPrint;
    private javax.swing.JButton btnConnectPrinter;
    private javax.swing.JButton btnConvertir;
    private javax.swing.JButton btnOpenFile;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnTest;
    private javax.swing.JComboBox<String> comboConfigFile;
    private javax.swing.JFrame frmMsg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblDistancias;
    private javax.swing.JLabel lblDots;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField txtA;
    private javax.swing.JTextField txtB;
    private javax.swing.JTextArea txtBrailleText;
    private javax.swing.JTextField txtC;
    private javax.swing.JTextField txtD;
    private javax.swing.JTextField txtMaxChars;
    private javax.swing.JTextField txtMaxLines;
    private javax.swing.JTextArea txtplaintext;
    private javax.swing.JTextField txtsleep;
    private javax.swing.JTextField txtvelcar;
    private javax.swing.JTextField txtvelsheets;
    // End of variables declaration//GEN-END:variables
}