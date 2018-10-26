package start;


import javax.swing.*;

public class HelloWorldSwing {
    /**{
     * ��������ʾGUI�������̰߳�ȫ�Ŀ��ǣ�
     * ����������¼������߳��е��á�
     */
    private static void createAndShowGUI() {
        // ȷ��һ��Ư������۷��
        setWindowLookAndFeel();
//        JFrame.setDefaultLookAndFeelDecorated(true);

        // ���������ô���
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ��� "Hello World" ��ǩ
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

//        JFileChooser fileChooser = new JFileChooser();
//        frame.getContentPane().add(fileChooser);

        // ��ʾ����
        frame.pack();
        frame.setVisible(true);
    }

    private static void setWindowLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // ��ʾӦ�� GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}