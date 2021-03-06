package aes;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JFileChooser;

/**
 *
 * @author ntduc-let
 */
public class AESForm extends javax.swing.JFrame {

    public static final int TYPE_HEX = 0;
    public static final int TYPE_UTF8 = 1;
    public static final int LENGTH_TIME = 17;
    public static final String URL_ICON = ".\\icon\\AES_icon.png";
    public static final String KEY_SIZE_128 = "128";
    public static final String KEY_SIZE_192 = "192";
    public static final String KEY_SIZE_256 = "256";
    public static final String LOOP_NUMBER_10 = "10";
    public static final String LOOP_NUMBER_12 = "12";
    public static final String LOOP_NUMBER_14 = "14";
    public ArrayList<String> data = new ArrayList<>();

    private final DocumentListener dlKey1 = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateNotiKey(cbbTypeKey1, txtKey1, txtNotiKey1);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateNotiKey(cbbTypeKey1, txtKey1, txtNotiKey1);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateNotiKey(cbbTypeKey1, txtKey1, txtNotiKey1);
        }
    };
    private final DocumentListener dlKey2 = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateNotiKey(cbbTypeKey2, txtKey2, txtNotiKey2);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateNotiKey(cbbTypeKey2, txtKey2, txtNotiKey2);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateNotiKey(cbbTypeKey2, txtKey2, txtNotiKey2);
        }
    };
    private final ActionListener myClick = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(cbbSizeKey)) {
                cbbSizeKeyClick();
            } else if (e.getSource().equals(btnExit)) {
                btnExitClick();
            } else if (e.getSource().equals(btnReset1)) {
                btnReset1Click();
            } else if (e.getSource().equals(btnEncode)) {
                btnEncodeClick();
            } else if (e.getSource().equals(btnCopy1)) {
                btnCopyClick(txtCipher1.getText());
            } else if (e.getSource().equals(btnReset2)) {
                btnReset2Click();
            } else if (e.getSource().equals(btnDecode)) {
                btnDecodeClick();
            } else if (e.getSource().equals(btnCopy2)) {
                btnCopyClick(txtPlain2.getText());
            }
        }
    };

    /**
     * Creates new form AESForm
     */
    public AESForm() {
        initComponents();

        //?????t icon
        Image icon = Toolkit.getDefaultToolkit().getImage(URL_ICON);
        setIconImage(icon);

        //?????t form ra gi???a
        setLocationRelativeTo(null);

        //g??n s??? ki???n
        txtKey1.getDocument().addDocumentListener(dlKey1);
        txtKey2.getDocument().addDocumentListener(dlKey2);
        cbbSizeKey.addActionListener(myClick);
        btnExit.addActionListener(myClick);
        btnReset1.addActionListener(myClick);
        btnEncode.addActionListener(myClick);
        btnCopy1.addActionListener(myClick);
        btnReset2.addActionListener(myClick);
        btnDecode.addActionListener(myClick);
        btnCopy2.addActionListener(myClick);

        //T???t ch???c n??ng UTF-8 c???a b???n m??
        cbbTypeCipher1.setEnabled(false);
        cbbTypeCipher2.setEnabled(false);
    }

    //C???p nh???t c???nh b??o kh??a
    private void updateNotiKey(JComboBox cbbTypeKey, JTextField txtKey, JLabel txtNotiKey) {
        int sizeKey = Integer.parseInt(cbbSizeKey.getSelectedItem().toString());
        int lengKey = sizeKey / 8;

        switch (cbbTypeKey.getSelectedIndex()) {
            case TYPE_HEX -> {
                if (txtKey.getText().length() == 0 || txtKey.getText().length() == lengKey * 2) {
                    txtNotiKey.setText(" ");
                } else if (txtKey.getText().length() < lengKey * 2) {
                    txtNotiKey.setText("????? d??i kh??a ch??a ?????");
                } else if (txtKey.getText().length() > lengKey * 2) {
                    txtNotiKey.setText("????? d??i kh??a v?????t qu??");
                }
            }
            case TYPE_UTF8 -> {
                int length = txtKey.getText().getBytes(StandardCharsets.UTF_8).length;
                if (length == 0 || length == lengKey) {
                    txtNotiKey.setText(" ");
                } else if (length < lengKey) {
                    txtNotiKey.setText("????? d??i kh??a ch??a ?????");
                } else if (length > lengKey) {
                    txtNotiKey.setText("????? d??i kh??a v?????t qu??");
                }
            }
        }
    }

    //Thay ?????i k??ch th?????c kh??a
    private void cbbSizeKeyClick() {
        //C???p nh???t c???nh b??o kh??a
        updateNotiKey(cbbTypeKey1, txtKey1, txtNotiKey1);
        updateNotiKey(cbbTypeKey2, txtKey2, txtNotiKey2);

        //Hi???n th??? s??? v??ng l???p
        String sizeKey = cbbSizeKey.getSelectedItem().toString();
        switch (sizeKey) {
            case KEY_SIZE_128 ->
                txtLoopNumber.setText(LOOP_NUMBER_10);
            case KEY_SIZE_192 ->
                txtLoopNumber.setText(LOOP_NUMBER_12);
            case KEY_SIZE_256 ->
                txtLoopNumber.setText(LOOP_NUMBER_14);
        }
    }

    //Tho??t ch????ng tr??nh
    private void btnExitClick() {
        System.exit(0);
    }

    //Reset form M?? h??a
     private void btnReset1Click(){
        txtCipher1.setText("");
       ;
        txtKey1.setText("");
        txtTime1.setText("00:00:00.00");
        txtNotiKey1.setText(" ");

        cbbTypeCipher1.setSelectedIndex(0);
        cbbTypePlain1.setSelectedIndex(0);
        cbbTypeKey1.setSelectedIndex(0);
    }
     
    //Th???c hi???n m?? h??a
    private void btnEncodeClick() {
        //C???p nh???t c???nh b??o        
        if (txtKey1.getText().isEmpty()) {
            txtNotiKey1.setText("Vui l??ng nh???p kh??a");
            return;
        }

        LocalTime timeBegin, timeEnd, timeDelay; //T??nh th???i gian th???c thi
        timeBegin = LocalTime.now(); //L???y th???i gian b???t ?????u th???c thi
 int size = 0; //K??ch th?????c kh??a
        int ver = 0; //S??? phi??n b???n

        String strCipher = ""; //Chu???i ???? m?? h??a

        byte[] byteKey = null; //M???ng kh??a
        int[] byteKeyExpansion; //M???ng kh??a m??? r???ng

        byte[] bytePlain = null; //M???ng byte chuy???n t??? chu???i b???n r?? ?????u v??o
        byte[][][] bytePlainConvert; //M???ng byte chuy???n t??? m???ng bytePlain

        size = Integer.parseInt(cbbSizeKey.getSelectedItem().toString()); //L???y k??ch th?????c kh??a

        AESAlgorithm aes = new AESAlgorithm(size);//t???o m?? c?? k??ch th?????c kh??a
        for (int i = 0; i < data.size(); i++) {
            try {

                bytePlain = getByte(aes, cbbTypePlain1, data.get(i)); //Chuy???n chu???i b???n r?? th??nh m???ng byte[]

            } catch (Exception e) {

                txtNotiPlain1.setText(e.getMessage());

                return;

            }
            ver = getVer(bytePlain); //X??c ?????nh s??? phi??n b???n

            //B???t l???i kh??ng ????ng ?????nh d???ng
            try {
                byteKey = getByte(aes, cbbTypeKey1, txtKey1.getText()); //Chuy???n chu???i kh??a th??nh m???ng byte[]
            } catch (Exception e) {
                txtNotiKey1.setText(e.getMessage());
                return;
            }

            //Ki???m tra ????? d??i kh??a
            if (byteKey.length != size / 8) {
                return;
            }

            byteKeyExpansion = aes.createKeyExpansion(byteKey); //T???o kh??a m??? r???ng

            bytePlainConvert = getByteConvert(ver, bytePlain); //Chuy???n m?? th??nh nhi???u phi??n b???n byte[][]

            strCipher +=getStrEncode(aes, ver, byteKeyExpansion, cbbTypeCipher1, bytePlainConvert); //Chu???i ???? m?? h??a
        }
        txtCipher1.setText(strCipher);
        
        

        timeEnd = LocalTime.now(); //l???y th???i gian k???t th??c th???c thi

        //T??nh kho???ng th???i gian th???c thi
        timeDelay = timeEnd.minusHours(timeBegin.getHour())
                .minusMinutes(timeBegin.getMinute())
                .minusSeconds(timeBegin.getSecond())
                .minusNanos(timeBegin.getNano());

        String strTimeDelay = "" + timeDelay; //00:00:00.00000000

        if (strTimeDelay.length() <= LENGTH_TIME) {
            txtTime1.setText(strTimeDelay);
        } else {
            txtTime1.setText(strTimeDelay.substring(0, LENGTH_TIME - 1));
        }
    }

    //Reset form Gi???i m??
    private void btnReset2Click() {
        txtCipher2.setText("");
        txtPlain2.setText("");
        txtKey2.setText("");
        txtTime2.setText("00:00:00.00");
        txtNotiKey2.setText(" ");

        cbbTypeCipher2.setSelectedIndex(0);
        cbbTypePlain2.setSelectedIndex(0);
        cbbTypeKey2.setSelectedIndex(0);
    }

    //Th???c hi???n gi???i m??
    private void btnDecodeClick() {
        //C???p nh???t c???nh b??o
        txtNotiCipher2.setText(" ");
        if (txtCipher2.getText().isEmpty()) {
            txtNotiCipher2.setText("Vui l??ng nh???p file!");
            return;
        } else if (txtKey2.getText().isEmpty()) {
            txtNotiKey2.setText("Vui l??ng nh???p kh??a");
            return;
        }

        LocalTime timeBegin, timeEnd, timeDelay; //T??nh th???i gian th???c thi
        timeBegin = LocalTime.now(); //L???y th???i gian b???t ?????u th???c thi

        int size = 0; //K??ch th?????c kh??a
        int ver = 0; //S??? phi??n b???n

        String strPlain = ""; //Chu???i ???? gi???i m??

        byte[] byteKey = null; //M???ng kh??a
        int[] byteKeyExpansion; //M???ng kh??a m??? r???ng

        byte[] byteCipher = null; //M???ng byte chuy???n t??? chu???i b???n m?? ?????u v??o
        byte[][][] byteCipherConvert; //M???ng byte chuy???n t??? m???ng byteCipher

        size = Integer.parseInt(cbbSizeKey.getSelectedItem().toString()); //L???y k??ch th?????c kh??a

        AESAlgorithm aes = new AESAlgorithm(size);

        //B???t l???i kh??ng ????ng ?????nh d???ng
        try {
            byteCipher = getByte(aes, cbbTypeCipher2, txtCipher2.getText()); //Chuy???n chu???i b???n m?? th??nh m???ng byte[]
        } catch (Exception e) {
            txtNotiCipher2.setText(e.getMessage());
            return;
        }

        //Ki???m tra b???n m?? ???? ????? k?? t??? ch??a
        if (byteCipher.length % 16 != 0) {
            txtNotiCipher2.setText("B???n m?? kh??ng h???p l???");
            return;
        }

        ver = getVer(byteCipher); //X??c ?????nh s??? phi??n b???n

        try {
            byteKey = getByte(aes, cbbTypeKey2, txtKey2.getText()); //Chuy???n chu???i key th??nh m???ng byte[]
        } catch (Exception e) {
            txtNotiKey2.setText(e.getMessage());
            return;
        }

        if (byteKey.length != size / 8) {
            return;
        }

        byteKeyExpansion = aes.createKeyExpansion(byteKey); //T???o kh??a m??? r???ng

        byteCipherConvert = getByteConvert(ver, byteCipher); //Chuy???n m?? th??nh nhi???u phi??n b???n byte[][]

        strPlain = getStrDecode(aes, ver, byteKeyExpansion, cbbTypePlain2, byteCipherConvert); //Chu???i ???? gi???i m??

        txtPlain2.setText(strPlain);

        timeEnd = LocalTime.now(); //l???y th???i gian k???t th??c th???c thi

        //T??nh kho???ng th???i gian th???c thi
        timeDelay = timeEnd.minusHours(timeBegin.getHour())
                .minusMinutes(timeBegin.getMinute())
                .minusSeconds(timeBegin.getSecond())
                .minusNanos(timeBegin.getNano());

        String strTimeDelay = "" + timeDelay; //00:00:00.00000000

        if (strTimeDelay.length() <= LENGTH_TIME) {
            txtTime2.setText(strTimeDelay);
        } else {
            txtTime2.setText(strTimeDelay.substring(0, LENGTH_TIME - 1));
        }
    }

    //Copy n???i dung v??o Clipboard
    private void btnCopyClick(String noiDung) {
        StringSelection stringSelection = new StringSelection(noiDung);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    //Chuy???n n???i dung th??nh 1 m???ng byte[]
    private byte[] getByte(AESAlgorithm aes, JComboBox<String> cbType, String noiDung) {
        switch (cbType.getSelectedIndex()) {
            case TYPE_HEX -> {
                return aes.decodeHexString(noiDung); //Chuy???n chu???i Hex th??nh m???ng Hex[]
            }
            case TYPE_UTF8 -> {
                return noiDung.getBytes(StandardCharsets.UTF_8); //Chuy???n chu???i ?????u v??o sang byte[]
            }
        }
        return null;
    }

    //X??c ?????nh phi??n b???n
    private int getVer(byte[] b) {
        if (b.length % 16 == 0) {
            return b.length / 16;
        } else {
            return b.length / 16 + 1;
        }
    }

    //Chuy???n m???ng byte[] th??nh m???ng byte[][][]
    private byte[][][] getByteConvert(int ver, byte[] b) {
        byte[][][] bConvert = new byte[ver][4][4];
        for (int v = 0; v < ver; v++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (16 * v + 4 * i + j < b.length) {
                        bConvert[v][j][i] = b[16 * v + 4 * i + j];
                    } else {
                        bConvert[v][j][i] = (byte) 0x09; //Th??m padding
                    }
                }
            }
        }
        return bConvert;
    }

    //X??c ?????nh chu???i m?? h??a
    private String getStrEncode(AESAlgorithm aes, int ver, int[] byteKeyExpansion, JComboBox<String> cbType, byte[][][] bConvert) {
        String str = "";
        byte[][][] b = new byte[ver][4][4];
        switch (cbType.getSelectedIndex()) {
            case TYPE_HEX -> {
                //M?? h??a c??c phi??n b???n
                for (int v = 0; v < ver; v++) {
                    b[v] = aes.cipher(bConvert[v], byteKeyExpansion); //M?? h??a phi??n b???n v

                    //Chuy???n m???ng byte th??nh chu???i hex
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            sb.append(String.format("%02X", b[v][j][i]));
                        }
                    }
                    str += sb.toString();
                }
            }
            case TYPE_UTF8 -> {
                byte[] byteOutConvert = new byte[ver * 4 * 4];
                //M?? h??a c??c phi??n b???n
                for (int v = 0; v < ver; v++) {
                    b[v] = aes.cipher(bConvert[v], byteKeyExpansion); //M?? h??a phi??n b???n v

                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            byteOutConvert[16 * v + 4 * i + j] = b[v][j][i];
                        }
                    }
                    //Chuy???n m???ng byte th??nh chu???i UTF-8
                    str = new String(byteOutConvert, StandardCharsets.UTF_8);
                }
            }
        }
        return str;
    }

    //X??c ?????nh chu???i gi???i m??
    private String getStrDecode(AESAlgorithm aes, int ver, int[] byteKeyExpansion, JComboBox<String> cbType, byte[][][] bConvert) {
        String str = "";
        byte[][][] b = new byte[ver][4][4];
        ArrayList<Byte> byteConvert = new ArrayList<>();
        byte[] byteConvert2 = null;
        switch (cbType.getSelectedIndex()) {
            case TYPE_HEX -> {
                //Gi???i m?? c??c phi??n b???n
                for (int v = 0; v < ver; v++) {
                    b[v] = aes.invCipher(bConvert[v], byteKeyExpansion); //Gi???i m?? phi??n b???n v

                    //Chuy???n sang m???ng 1 chi???u
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            byteConvert.add(b[v][j][i]);
                        }
                    }
                }

                byteConvert2 = arrayListToArray(byteConvert); //Chuy???n t??? ArrayList sang array

                //Chuy???n m???ng byte th??nh chu???i hex
                StringBuilder sb = new StringBuilder();
                for (byte b1 : byteConvert2) {
                    sb.append(String.format("%02X", b1));
                }
                str = sb.toString();
            }
            case TYPE_UTF8 -> {
                //Gi???i m?? c??c phi??n b???n
                for (int v = 0; v < ver; v++) {
                    b[v] = aes.invCipher(bConvert[v], byteKeyExpansion); //Gi???i m?? phi??n b???n v

                    //Chuy???n sang m???ng 1 chi???u
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            byteConvert.add(b[v][j][i]);
                        }
                    }
                }

                byteConvert2 = arrayListToArray(byteConvert); //Chuy???n t??? ArrayList sang array

                //Chuy???n m???ng byte th??nh chu???i UTF-8
                str = new String(byteConvert2, StandardCharsets.UTF_8);
            }
        }
        return str;
    }

    //Chuy???n ArrayList th??nh array
    private byte[] arrayListToArray(ArrayList<Byte> arrayList) {
        byte[] array = null;

        //Lo???i b??? c??c padding ph??a sau
        for (int i = arrayList.size() - 1; i >= 0; i--) {
            if (arrayList.get(i) == (byte) 0x09) {
                arrayList.remove(i);
            } else {
                array = new byte[i + 1];
                break;
            }
        }

        //Kh???i t???o array
        for (int i = 0; i < array.length; i++) {
            array[i] = arrayList.get(i);
        }
        return array;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbbSizeKey = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtLoopNumber = new javax.swing.JTextField();
        btnExit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbbTypePlain1 = new javax.swing.JComboBox<>();
        cbbTypeKey1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtKey1 = new javax.swing.JTextField();
        cbbTypeCipher1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnEncode = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtTime1 = new javax.swing.JTextField();
        txtNotiKey1 = new javax.swing.JLabel();
        btnReset1 = new javax.swing.JButton();
        btnCopy1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtCipher1 = new javax.swing.JTextArea();
        txtNotiPlain1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbbTypeCipher2 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCipher2 = new javax.swing.JTextArea();
        cbbTypeKey2 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtKey2 = new javax.swing.JTextField();
        cbbTypePlain2 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        btnDecode = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtTime2 = new javax.swing.JTextField();
        txtNotiKey2 = new javax.swing.JLabel();
        btnReset2 = new javax.swing.JButton();
        btnCopy2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtPlain2 = new javax.swing.JTextArea();
        txtNotiCipher2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("M?? h??a v?? gi???i m?? AES");
        setBackground(new java.awt.Color(102, 255, 255));
        setResizable(false);

        jPanel3.setForeground(new java.awt.Color(102, 255, 255));

        jLabel1.setText("????? d??i kh??a:");

        cbbSizeKey.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "128", "192", "256" }));

        jLabel2.setText("S??? l???n l???p:");

        txtLoopNumber.setText("10");
        txtLoopNumber.setEnabled(false);

        btnExit.setText("Tho??t");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbSizeKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLoopNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 455, Short.MAX_VALUE)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbbSizeKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtLoopNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("M?? h??a");

        jLabel4.setText("B???n r??:");

        cbbTypePlain1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));
        cbbTypePlain1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTypePlain1ActionPerformed(evt);
            }
        });

        cbbTypeKey1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));
        cbbTypeKey1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTypeKey1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Kh??a:");

        txtKey1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKey1ActionPerformed(evt);
            }
        });

        cbbTypeCipher1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        jLabel6.setText("B???n m??:");

        btnEncode.setText("M?? h??a");
        btnEncode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncodeActionPerformed(evt);
            }
        });

        jLabel11.setText("Th???i gian (s):");

        txtTime1.setText("00:00:00.00");
        txtTime1.setEnabled(false);

        txtNotiKey1.setFont(new java.awt.Font("Dialog", 3, 11)); // NOI18N
        txtNotiKey1.setForeground(new java.awt.Color(255, 51, 51));
        txtNotiKey1.setText(" ");

        btnReset1.setText("Reset");

        btnCopy1.setText("Sao ch??p");

        txtCipher1.setColumns(20);
        txtCipher1.setLineWrap(true);
        txtCipher1.setRows(3);
        txtCipher1.setEnabled(false);
        jScrollPane3.setViewportView(txtCipher1);

        txtNotiPlain1.setFont(new java.awt.Font("Dialog", 3, 11)); // NOI18N
        txtNotiPlain1.setForeground(new java.awt.Color(255, 51, 51));
        txtNotiPlain1.setText(" ");

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(868, 868, 868))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbbTypeKey1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbbTypeCipher1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtKey1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                    .addComponent(txtNotiKey1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbTypePlain1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(149, 149, 149)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNotiPlain1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnCopy1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                .addComponent(btnReset1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtTime1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEncode, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnReset1)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(cbbTypePlain1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1)))
                        .addGap(0, 100, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNotiPlain1)
                            .addComponent(btnEncode))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKey1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbTypeKey1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNotiKey1)
                            .addComponent(txtTime1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbTypeCipher1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCopy1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("Gi???i m??");

        jLabel8.setText("B???n m??:");

        cbbTypeCipher2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        txtCipher2.setColumns(20);
        txtCipher2.setLineWrap(true);
        txtCipher2.setRows(3);
        jScrollPane2.setViewportView(txtCipher2);

        cbbTypeKey2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        jLabel9.setText("Kh??a:");

        cbbTypePlain2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        jLabel10.setText("B???n r??:");

        btnDecode.setText("Gi???i m??");

        jLabel19.setText("Th???i gian (s):");

        txtTime2.setText("00:00:00.00");
        txtTime2.setEnabled(false);

        txtNotiKey2.setFont(new java.awt.Font("Dialog", 3, 11)); // NOI18N
        txtNotiKey2.setForeground(new java.awt.Color(255, 51, 51));
        txtNotiKey2.setText(" ");
        txtNotiKey2.setToolTipText("");

        btnReset2.setText("Reset");

        btnCopy2.setText("Sao ch??p");

        txtPlain2.setColumns(20);
        txtPlain2.setLineWrap(true);
        txtPlain2.setRows(3);
        txtPlain2.setEnabled(false);
        jScrollPane4.setViewportView(txtPlain2);

        txtNotiCipher2.setFont(new java.awt.Font("Dialog", 3, 11)); // NOI18N
        txtNotiCipher2.setForeground(new java.awt.Color(255, 51, 51));
        txtNotiCipher2.setText(" ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(868, 868, 868))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbbTypePlain2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbbTypeKey2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(19, 19, 19)))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtKey2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                    .addComponent(txtNotiKey2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbTypeCipher2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNotiCipher2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCopy2, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReset2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDecode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTime2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(cbbTypeCipher2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNotiCipher2)
                    .addComponent(btnDecode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKey2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbTypeKey2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNotiKey2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbbTypePlain2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTime2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCopy2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbbTypeKey1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTypeKey1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbTypeKey1ActionPerformed

    private void cbbTypePlain1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTypePlain1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbTypePlain1ActionPerformed

    private void txtKey1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKey1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKey1ActionPerformed

    private void btnEncodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEncodeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser C = new JFileChooser();
        int rVal = C.showOpenDialog(null);//hi??n th??? from ch???n file 

        if (rVal == JFileChooser.APPROVE_OPTION) {
            String filename = C.getSelectedFile().getName();

            String dir = C.getCurrentDirectory().toString()+"/"+filename;//l???y v??? tr?? file ,v?? t??n file

            try {
                BufferedReader in;
                in = new BufferedReader(new FileReader(dir ));
                String line = in.readLine();
                while (line != null) {

                    data.add(line);
                    line = in.readLine();
                }//d???c file sau add v??o data
            } catch (FileNotFoundException ex) {
                Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);

            }
        }
        
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
            java.util.logging.Logger.getLogger(AESForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AESForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AESForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AESForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AESForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCopy1;
    private javax.swing.JButton btnCopy2;
    private javax.swing.JButton btnDecode;
    private javax.swing.JButton btnEncode;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnReset1;
    private javax.swing.JButton btnReset2;
    private javax.swing.JComboBox<String> cbbSizeKey;
    private javax.swing.JComboBox<String> cbbTypeCipher1;
    private javax.swing.JComboBox<String> cbbTypeCipher2;
    private javax.swing.JComboBox<String> cbbTypeKey1;
    private javax.swing.JComboBox<String> cbbTypeKey2;
    private javax.swing.JComboBox<String> cbbTypePlain1;
    private javax.swing.JComboBox<String> cbbTypePlain2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea txtCipher1;
    private javax.swing.JTextArea txtCipher2;
    private javax.swing.JTextField txtKey1;
    private javax.swing.JTextField txtKey2;
    private javax.swing.JTextField txtLoopNumber;
    private javax.swing.JLabel txtNotiCipher2;
    private javax.swing.JLabel txtNotiKey1;
    private javax.swing.JLabel txtNotiKey2;
    private javax.swing.JLabel txtNotiPlain1;
    private javax.swing.JTextArea txtPlain2;
    private javax.swing.JTextField txtTime1;
    private javax.swing.JTextField txtTime2;
    // End of variables declaration//GEN-END:variables
}
