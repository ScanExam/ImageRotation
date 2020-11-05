import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AppPanel extends JFrame{

    public AppPanel() {
        this.setVisible(true);
        this.setTitle("Picture Rotation");
        this.setSize(400,400);



        JPanel pan = new JPanel();
        pan.setBackground(Color.DARK_GRAY);
        this.setContentPane(pan);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {

        final JFrame jf = new JFrame("Picture Rotation");

        jf.setSize(400, 250);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        AppPanel appPanel = new AppPanel();

        final JButton openBtn = new JButton(("Open"));
        openBtn.setBounds(200,200, 100, 50);



        ActionListener rotateImage  = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filechose();
            }
        };
        openBtn.addActionListener(rotateImage);
        appPanel.add(openBtn);

    }

    public static void filechose() {
        JFileChooser window = new JFileChooser();
        int returnValue = window.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            AppPanel rotatePanel = new AppPanel();
            rotatePanel.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            final JTextArea textarea = new JTextArea("",1,10);

            final JButton rotateBtn = new JButton(("Rotate"));
            rotateBtn.setBounds(100,150, 50, 25);




            ActionListener rotateListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   try{
                       String anglestr = textarea.getText();
                    int angle =  Integer.parseInt(anglestr);
                    rotate(window,angle);
                   }
                   catch (NumberFormatException exception){
                       exception.printStackTrace();

                   }
                }
            };

            rotateBtn.addActionListener(rotateListener);
            rotatePanel.add(textarea);
            rotatePanel.add(rotateBtn);


        }
    }

    public static void rotate(JFileChooser window,int angel){


        String path = window.getSelectedFile().getAbsolutePath();
        File file = new File(path);
        String fileName = file.getName();


        if (fileName.endsWith(".png")|| fileName.endsWith("jpeg")||fileName.endsWith("jpg")) {
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                BufferedImage rotateImage = RotateImage.rotateImage(bufferedImage, angel);

                File rotated = new File("template/rotated.png");
                ImageIO.write(rotateImage, "png", rotated);

                Desktop desktop = Desktop.getDesktop();
                File dirToOpen = null;
                try {
                    dirToOpen = new File("template/rotated.png");
                    desktop.open(dirToOpen);
                } catch (IllegalArgumentException iae) {
                    System.out.println("Picture not found");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Document error");
        }
    }
}
