/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recognizer;

import util.ConectaBanco;
import capture.ControlPerson;
import capture.ModelPerson;
import capture.Register;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Array;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imencode;
import org.bytedeco.opencv.global.opencv_imgproc;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

/**
 *
 * @author ACER
 */
public class Recognizer extends javax.swing.JFrame {

    ModelPerson mod = new ModelPerson();
    ControlPerson cod = new ControlPerson();

    private Recognizer.DaemonThread myThread = null;

    //JavaCV 1.5.1
    VideoCapture webSource = null;
    Mat cameraImage = new Mat();
    CascadeClassifier cascade = new CascadeClassifier("G:\\TUGAS AKHIR WWOOOYYOOOOO\\Java\\FacEmot_v1\\lib\\haarcascade_frontalface_alt2.xml");
    LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();

    BytePointer mem = new BytePointer();
    RectVector detectedFaces = new RectVector();

    //Vars
    String root, firstNamePerson, lastNamePerson, officePerson, dobPerson, telefone;
    //Social Info
    int marah = 0, bahagia = 0, terkejut = 0, sedih = 0, jijik = 0;
    int idPerson;

    //Utils
    ConectaBanco conecta = new ConectaBanco();

    /**
     * Creates new form Recognizer
     */
    public Recognizer() {
//        super(parent, modal);
        initComponents();
        recognizer.read("G:\\TUGAS AKHIR WWOOOYYOOOOO\\Java\\FacEmot_v1\\src\\photos\\classifierLBPH.yml");
        recognizer.setThreshold(100);
        startCamera();

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        jLabel24.setForeground(Color.GRAY.darker());
        jLabel24.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel24.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Register frame2 = new Register();
                Recognizer.this.setVisible(false);
                frame2.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jLabel24.setText("- Daftar Dataset");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                jLabel24.setText("<html><a href=''>" + "- Daftar Dataset" + "</a></html>");
            }
        });
        
        jLabel25.setForeground(Color.GRAY.darker());
        jLabel25.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel25.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Register frame2 = new Register();
                Recognizer.this.setVisible(false);
                frame2.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jLabel25.setText("- Daftar Stimulus");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                jLabel25.setText("<html><a href=''>" + "- Daftar Stimulus" + "</a></html>");
            }
        });
        
        jLabel26.setForeground(Color.GRAY.darker());
        jLabel26.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel26.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Register frame2 = new Register();
                Recognizer.this.setVisible(false);
                frame2.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jLabel26.setText("- Daftar User");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                jLabel26.setText("<html><a href=''>" + "- Daftar User" + "</a></html>");
            }
        });
        
        jLabel16.setForeground(Color.GRAY.darker());
        jLabel16.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel16.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                Recognizer frame2 = new Recognizer();
//                Recognizer.this.setVisible(false);
//                frame2.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jLabel16.setText("Monitoring User");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                jLabel16.setText("<html><a href=''>" + "Monitoring User" + "</a></html>");
            }
        });
    }
    

    class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;

        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    try {
                        if (webSource.grab()) {
                            webSource.retrieve(cameraImage);
                            Graphics g = label_photo.getGraphics();

                            Mat imageGray = new Mat();
                            cvtColor(cameraImage, imageGray, COLOR_BGRA2GRAY);

                            RectVector detectedFace = new RectVector();
                            cascade.detectMultiScale(imageGray, detectedFace, 1.1, 2, 0, new Size(150, 150), new Size(500, 500));

                            for (int i = 0; i < detectedFace.size(); i++) {
                                Rect dadosFace = detectedFace.get(i);
                                rectangle(cameraImage, dadosFace, new Scalar(0, 255, 0, 3), 3, 0, 0);
                                Mat faceCapturada = new Mat(imageGray, dadosFace);
                                opencv_imgproc.resize(faceCapturada, faceCapturada, new Size(160, 160));

                                IntPointer rotulo = new IntPointer(1);
                                DoublePointer confidence = new DoublePointer(1);
                                recognizer.predict(faceCapturada, rotulo, confidence);
                                int prediction = rotulo.get(0);
                                String nome;
                                nome = firstNamePerson;

                                if (prediction == -1) {
                                    rectangle(cameraImage, dadosFace, new Scalar(0, 0, 255, 3), 3, 0, 0);
                                    idPerson = 0;
//                                    label_name.setText("Wajah tidak ditemukan");
//                                    txt_id_label.setText("");
//                                    label_office.setText("");
                                    ekspresi_field.setText("");

//                                    sendMessage_btn.setText("Send Message");
//                                    facebook = "";
//                                    insta = "";
//                                    git = "";
//                                    linkedin = "";
                                } else {
                                    rectangle(cameraImage, dadosFace, new Scalar(0, 255, 0, 3), 3, 0, 0);
//                                    System.out.println(confidence.get(0));
                                    idPerson = prediction;
//                                    System.out.println("PESSOA RECONHECIDA COMO: " + idPerson);
                                    rec();
                                }
                            }

                            imencode(".bmp", cameraImage, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
                            BufferedImage buff = (BufferedImage) im;

                            try {
                                if (g.drawImage(buff, 0, 0, 800, 880, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                    if (runnable == false) {
                                        this.wait();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }

    private void rec() {
        //Retrieve data from database
        new Thread() {
            @Override
            public void run() {
                conecta.conexao();

                try {
                    conecta.executaSQL("SELECT * FROM dataset_register WHERE id_regis = " + String.valueOf(idPerson));
                    
                    while (conecta.rs.next()) {
//                        firstNamePerson = conecta.rs.getString("ekspresi");
//                        jLabel10.setText("Ekspresi Anda, " + firstNamePerson);
//                        label_name.setText(conecta.rs.getString("nama_lengkap"));
//                        label_office.setText(conecta.rs.getString("office"));

                        telefone = conecta.rs.getString("ekspresi");
                        if (telefone.equals("Marah")) {
                            marah++;
                            marahF.setText(String.valueOf(marah));                        
                        } else if (telefone.equals("Bahagia")){
                            bahagia++;
                            bahagiaF.setText(String.valueOf(bahagia));
                        } else if (telefone.equals("Sedih")){
                            sedih++;
                            sedihF.setText(String.valueOf(sedih));
                        } else if (telefone.equals("Jijik")){
                            jijik++;
                            jijikF.setText(String.valueOf(jijik));
                        } else if (telefone.equals("Terkejut")){
                            terkejut++;
                            terkejutF.setText(String.valueOf(terkejut));
                        }
                        ekspresi_field.setText(telefone);
//                        sendMessage_btn.setText("Send Message to " + conecta.rs.getString("phone_number"));
//                        txt_id_label.setText(conecta.rs.getString("id_regis"));

                        //Social Info
//                        facebook = conecta.rs.getString("profile_facebook");
//                        insta = conecta.rs.getString("profile_instagram");
//                        linkedin = conecta.rs.getString("profile_linkedin");
//                        git = conecta.rs.getString("profile_github");
                        Array ident = conecta.rs.getArray("nama_lengkap");
                        String[] person = (String[]) ident.getArray();

                        for (String person1 : person) {
                            System.out.println(person1);
                        }

                    }
                } catch (Exception e) {
                }
                conecta.desconecta();
            }
        }.start();
    }

    public void stopCamera() {
        try {
            myThread.runnable = false;
            webSource.release();
            dispose();
        } catch (Exception e) {
        }
    }

    public void startCamera() {
        new Thread() {
            @Override
            public void run() {
                webSource = new VideoCapture(0);
                myThread = new Recognizer.DaemonThread();
                Thread t = new Thread(myThread);
                t.setDaemon(true);
                myThread.runnable = true;
                t.start();
            }
        }.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        ekspresi_field = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        label_photo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jijikF = new javax.swing.JLabel();
        terkejutF = new javax.swing.JLabel();
        sedihF = new javax.swing.JLabel();
        bahagiaF = new javax.swing.JLabel();
        marahF = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(100, 100, 100));
        jLabel13.setText("Monitoring User");

        jPanel2.setBackground(new java.awt.Color(101, 199, 113));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setBackground(new java.awt.Color(90, 68, 193));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Ekspresi Wajah    :");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 340, -1));

        ekspresi_field.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jPanel2.add(ekspresi_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 500, 50));

        jButton1.setBackground(new java.awt.Color(213, 83, 83));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Filled_Circle_15px_1.png"))); // NOI18N
        jButton1.setToolTipText("Close");
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 0, 40, 50));

        label_photo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(100, 100, 100));
        jLabel21.setText("Terkejut        : ");

        jLabel15.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(100, 100, 100));
        jLabel15.setText("Jijik               : ");

        jLabel20.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(100, 100, 100));
        jLabel20.setText("Sedih             : ");

        jLabel19.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(100, 100, 100));
        jLabel19.setText("Bahagia        :");

        jLabel18.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(100, 100, 100));
        jLabel18.setText("Marah           :");

        jLabel17.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(100, 100, 100));
        jLabel17.setText("Grafik Jumlah Ekspresi User");

        jijikF.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jijikF.setForeground(new java.awt.Color(100, 100, 100));

        terkejutF.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        terkejutF.setForeground(new java.awt.Color(100, 100, 100));

        sedihF.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        sedihF.setForeground(new java.awt.Color(100, 100, 100));

        bahagiaF.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        bahagiaF.setForeground(new java.awt.Color(100, 100, 100));

        marahF.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        marahF.setForeground(new java.awt.Color(100, 100, 100));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(terkejutF, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                            .addComponent(sedihF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jijikF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bahagiaF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(marahF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel17)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17)
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(terkejutF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel18)
                                    .addComponent(marahF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel20)
                                .addComponent(sedihF, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(bahagiaF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel19)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jijikF, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jLabel1.setText("Video");

        jLabel22.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(100, 100, 100));
        jLabel22.setText("Video Stimulus");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Segoe UI Black", 0, 30)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(100, 100, 100));
        jLabel16.setText("Monitoring User");

        jLabel24.setFont(new java.awt.Font("Segoe UI Black", 0, 30)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(100, 100, 100));
        jLabel24.setText("- Daftar Dataset");

        jLabel25.setFont(new java.awt.Font("Segoe UI Black", 0, 30)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(100, 100, 100));
        jLabel25.setText("- Daftar Stimulus");

        jLabel26.setFont(new java.awt.Font("Segoe UI Black", 0, 30)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(100, 100, 100));
        jLabel26.setText("- Daftar User");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25)
                    .addComponent(jLabel24)
                    .addComponent(jLabel16))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addComponent(jLabel26)
                .addContainerGap(694, Short.MAX_VALUE))
        );

        jLabel23.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(100, 100, 100));
        jLabel23.setText("User Webcam");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(26, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(188, 188, 188)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1385, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                .addComponent(label_photo, javax.swing.GroupLayout.PREFERRED_SIZE, 770, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel22)
                                .addGap(376, 376, 376)))))
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(label_photo, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))))
                .addGap(111, 111, 111))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        stopCamera();
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Recognizer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Recognizer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Recognizer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Recognizer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Recognizer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bahagiaF;
    private javax.swing.JLabel ekspresi_field;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel jijikF;
    private javax.swing.JLabel label_photo;
    private javax.swing.JLabel marahF;
    private javax.swing.JLabel sedihF;
    private javax.swing.JLabel terkejutF;
    // End of variables declaration//GEN-END:variables
}
