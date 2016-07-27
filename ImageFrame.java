import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.border.Border;

/**
 * Created by nigelfrnds on 2016-05-07.
 */
public class ImageFrame extends JFrame {

    public static final int FRAME_HEIGHT = 1000;
    public static final int FRAME_WIDTH = 1000;
    private JMenuBar menuBar;
    private ImageComponent comp;
    private JPanel contentsPanel;
    private JPanel buttonPanel;
    private JPanel imagePanel;



    public ImageFrame()
    {
        contentsPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout());
        imagePanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(new Color(61,112,250));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //contentsPanel.setBackground(new Color(218,218,218));
        imagePanel.setBackground(new Color(218,218,218));

        //contentsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        comp = new ImageComponent();
        comp.centerComponent(FRAME_WIDTH, FRAME_HEIGHT);
        menuBar = new JMenuBar();

        setContentPane(contentsPanel);
        setJMenuBar(menuBar);

        menuBar.add(createFileMenu());
        buttonPanel.add(createResetImageButton());
        buttonPanel.add(createGrayscaleButton());
        buttonPanel.add(createMirrorButton());
        buttonPanel.add(createSplitButton());
        buttonPanel.add(createSortPixelsButton());
        buttonPanel.add(createFindEdgesButton());
        buttonPanel.add(createRotateLeftButton());
        buttonPanel.add(createRotateRightButton());

        contentsPanel.add(buttonPanel, BorderLayout.NORTH);
        imagePanel.add(comp, BorderLayout.CENTER);
        contentsPanel.add(imagePanel, BorderLayout.CENTER);




        //add(comp);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public JButton createGrayscaleButton(){
        class GrayListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                comp.toGrayScale();
                repaint();
            }
        }
        JButton button = new JButton("Grayscale");
        button.addActionListener(new GrayListener());
        return button;
    }

    public JButton createMirrorButton(){
        class MirrorListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                comp.mirrorImage();
                repaint();
            }
        }
        JButton button = new JButton("Mirror");
        button.addActionListener(new MirrorListener());
        return button;
    }
    public JButton createSortPixelsButton(){
        class SortListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                comp.sortPixels();
                repaint();
            }
        }
        JButton button = new JButton("Sort Pixels");
        button.addActionListener(new SortListener());
        return button;
    }

    public JButton createResetImageButton(){
        class ResetImageListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                comp.resetImage();
                repaint();
            }
        }
        JButton button = new JButton("Reset");
        button.addActionListener(new ResetImageListener());
        return button;
    }

    public JButton createSplitButton(){
        class SplitButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                comp.splitImage();
                repaint();
            }
        }

        JButton button = new JButton("Split");
        button.addActionListener(new SplitButtonListener());
        return button;
    }

    public JButton createFindEdgesButton(){
        class EdgeListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                comp.findEdges();
                repaint();
            }
        }

        JButton button = new JButton("Find Edges");
        button.addActionListener(new EdgeListener());
        return button;
    }

    public JButton createRotateLeftButton(){
        class RotateLeftListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                comp.rotateLeft();
                repaint();
            }
        }

        JButton button = new JButton("Rotate Left");
        button.addActionListener(new RotateLeftListener());
        return button;
    }
    public JButton createRotateRightButton(){
        class RotateRightListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                comp.rotateRight();
                repaint();
            }
        }

        JButton button = new JButton("Rotate Right");
        button.addActionListener(new RotateRightListener());
        return button;
    }

    class NewItemListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            comp.clearImages();
            repaint();
            System.out.println("Frame Cleared");
        }
    }
    class OpenItemListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            System.out.println("Choose file");
            comp.selectImage();
            repaint();
        }
    }
    class SaveItemListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            comp.saveImage();
            repaint();
        }
    }
    class ExitItemListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            System.exit(0);
        }
    }

    public JMenu createFileMenu(){
        JMenu fileMenu = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(new NewItemListener());
        fileMenu.add(newItem);

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new OpenItemListener());
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new SaveItemListener());
        fileMenu.add(saveItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ExitItemListener());
        fileMenu.add(exitItem);
        return fileMenu;
    }

}


