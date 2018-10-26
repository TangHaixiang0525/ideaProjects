package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ChooseFilePath extends JFrame implements ActionListener {
    JButton jb = new JButton("���ļ�");

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        // TODO �Զ����ɵķ������
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        new ChooseFilePath();
    }

    public ChooseFilePath() {
        jb.setActionCommand("open");
        jb.setBackground(Color.GREEN);//���ð�ť��ɫ
        this.getContentPane().add(jb, BorderLayout.SOUTH);//��������ʹ�ñ߽粼��
        //
        jb.addActionListener(this);
        this.setTitle("����");
        this.setSize(333, 288);
        this.setLocation(200, 200);
        //��ʾ����true
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("open")) {
            JFileChooser jf = new JFileChooser();
            jf.showOpenDialog(this);//��ʾ�򿪵��ļ��Ի���
            File f = jf.getSelectedFile();//ʹ���ļ����ȡѡ����ѡ����ļ�
            String s = f.getAbsolutePath();//����·����
            //JOptionPane�����Ի����࣬��ʾ����·����
            JOptionPane.showMessageDialog(this, s, "����", JOptionPane.WARNING_MESSAGE);
        }
    }

}