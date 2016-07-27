import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.LinkedList;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.JFileChooser ;
import javax.swing.JOptionPane ;
import java.io.File ;

/**
 * Created by nigelfrnds on 2016-05-07.
 */
public class ImageComponent extends JComponent {

    //private LinkedList<BufferedImage> images;
    private BufferedImage img;
    private BufferedImage resultImage;
    private BufferedImage imageToSave;
    Rectangle box = new Rectangle(0,0,50,50);

    //Dimensions
    private int centerX;
    private int centerY;

    public ImageComponent(){
        img = null;
        resultImage = null;
        imageToSave = null;
        //images = new LinkedList<BufferedImage>();
    }


    public void centerComponent(int x, int y){
        System.out.println("Width: " + x);
        System.out.println("Height: " + y);
    }

    public void selectImage(){
        try{
            JFileChooser jfc = new JFileChooser(".");
            File imageFile = null;
            if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                imageFile = jfc.getSelectedFile();
                img = ImageIO.read(imageFile);
                img = resizeImage(img);
                imageToSave = img;
                //resultImage = img;

                //images.add(resizeImage(img));
                //images.add(resizeImage(resultImage));
                //JOptionPane.showMessageDialog(null, "Image Selected!");
            }
            else{
                System.out.println("Please selected a file!");
            }

        }
        catch(IOException e){
            System.out.println("Please selected the correct file");
        }
        catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Only JPEG and PNG formats accepted!", "Format Mismatch!", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void saveImage(){
        try{
            BufferedImage temp = getSavedImage();
            File outputImage = new File("testImage.png");
            ImageIO.write(temp, "png", outputImage);
            System.out.println("file saved");

        }
        catch (IOException e){
            System.out.println("Please select a file");
        }
        catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "No File Selected!", "Error!", JOptionPane.WARNING_MESSAGE);
            //System.out.println("No File selected!");
        }
    }

    public BufferedImage getSavedImage(){
        return imageToSave;
    }
    /** Image Resizing method from StackOverflow**/
    public BufferedImage resizeImage(BufferedImage image){
        int width = 450;
        int height = 500;

        Image img = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = temp.createGraphics();
        g2.drawImage(img,0,0, null);
        g2.dispose();

        return temp;
    }

    public void clearImages(){
        //images.clear();
        img = null;
        resultImage = null;
        repaint();

        JOptionPane.showMessageDialog(null, "Frame Cleared!");
    }

    public void toGrayScale(){
        try {
            BufferedImage temp = img;//images.get(0);
            resultImage = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < temp.getWidth(); x++) {
                for (int y = 0; y < temp.getHeight(); y++) {
                    int col = temp.getRGB(x, y);
                    int gray = (int) brightness(col);

                    resultImage.setRGB(x, y, rgbColor(gray, gray, gray));


                }
            }
            imageToSave = resultImage;
        }
        catch (NullPointerException e){
            JOptionPane.showMessageDialog(null, "No Image selected!", "Error!", JOptionPane.WARNING_MESSAGE);
        }
        //images.add(resultImage);
    }

    public void mirrorImage(){
        try {
            BufferedImage temp = img;//images.get(0);
            resultImage = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_RGB);
            int flip = 0;
            for (int x = 0; x < temp.getWidth(); x++) {
                for (int y = 0; y < temp.getHeight(); y++) {

                    flip = temp.getWidth() - x - 1;
                    resultImage.setRGB(x, y, temp.getRGB(flip, y));

                }
            }
            imageToSave = resultImage;
        }
        catch (NullPointerException e){
            JOptionPane.showMessageDialog(null, "No Image selected!", "Error!", JOptionPane.WARNING_MESSAGE);
        }
        //images.add(resultImage);

    }

    public void sortPixels(){
        int count =0;
        try{
            BufferedImage temp = img;
            resultImage  = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_RGB);

            for(int x=0; x<temp.getWidth(); x++){
                for(int y =0; y<temp.getHeight(); y++){
                    resultImage.setRGB(x,y,temp.getRGB(x,y));
                }
            }
            int col;
            int br;
            int col2;
            int br2;

            for(int i=0; i<resultImage.getWidth(); i++){
                for(int x=0; x<resultImage.getWidth()-1; x++){
                    for(int y=0; y<resultImage.getHeight(); y++){

                        col = resultImage.getRGB(x,y);
                        col2 = resultImage.getRGB(x+1,y);
                        br = (int)brightness(col);
                        br2=(int)brightness(col2);

                        if(br > br2){
                            resultImage.setRGB(x,y,col2);
                            resultImage.setRGB(x+1,y ,col);
                            count++;
                        }
                    }
                }
            }
            imageToSave = resultImage;
            System.out.println(count);
        }
        catch (NullPointerException e){
            JOptionPane.showMessageDialog(null, "No Image selected!", "Error!", JOptionPane.WARNING_MESSAGE);
        }
    }
    public void resetImage(){
        try{
            resultImage = resizeImage(img);
            imageToSave = resultImage;
        }
        catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "No Image selected!", "Error!", JOptionPane.WARNING_MESSAGE);
        }
    }
    public void splitImage(){
        try{
            BufferedImage temp = img;
            resultImage = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_RGB);

            int n =2;
            int width = temp.getWidth()/n;
            int height = temp.getHeight()/n;

            for(int x =0; x<temp.getWidth(); x++){
                for(int y =0; y<temp.getHeight(); y++){
                    resultImage.setRGB(x/n,y/n,temp.getRGB(x,y));
                    resultImage.setRGB(x/n + width, y/n, temp.getRGB(x,y));
                    resultImage.setRGB(x/n, y/n+height, temp.getRGB(x,y));
                    resultImage.setRGB(x/n + width, y/n + height, temp.getRGB(x,y));
                }
            }
            imageToSave = resultImage;
        }
        catch (NullPointerException e){
            JOptionPane.showMessageDialog(null, "No Image selected!", "Error!", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void findEdges(){
        final double sharpen[][] = {{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
        try{
            BufferedImage temp = img;
            resultImage = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_RGB);

            for(int x =1; x<temp.getWidth()-1; x++){
                for(int y =1; y<temp.getHeight()-1; y++){
                    //Weighted values of the colors
                    int red = 0;
                    int green = 0;
                    int blue = 0;

                    for(int i =0; i<sharpen.length; i++){
                        for(int j =0; j<sharpen[i].length; j++){
                            //Calculates  the weighted sum for each color
                            red += getRed(temp.getRGB(x+i-1, y+j-1))*sharpen[i][j];
                            green += getGreen(temp.getRGB(x+1-1,y+j-1))*sharpen[i][j];
                            blue += getBlue(temp.getRGB(x+i-1, y+j-1))*sharpen[i][j];

                        }
                    }
                    //Clips the images to lie between the range of 0 - 255
                    if(red > 255){red = 255;}
                    if(red<0){red = 0;}
                    if(green > 255){green = 255;}
                    if(green <0){green = 0;}
                    if(blue > 255){blue = 255;}
                    if(blue <0){blue =0;}
                    resultImage.setRGB(x,y, rgbColor(red,green,blue));
                }
            }
            imageToSave = resultImage;
        }
        catch (NullPointerException e){
            JOptionPane.showMessageDialog(null, "No Image selected!", "Error!", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void rotateLeft(){
        // Algorithm from http://introcs.cs.princeton.edu/java/31datatype/Rotation.java.html
        try{
            BufferedImage temp = img;
            resultImage = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_RGB);

            double angle = Math.toRadians(90.0);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            double x0 = 0.5 * (temp.getWidth()-1);
            double y0 = 0.5 * (temp.getHeight()-1);

            for(int x =0; x<temp.getWidth(); x++){
                for(int y =0; y<temp.getHeight(); y++){
                    double a = x - x0;
                    double b = y - y0;
                    int xx = (int) (a * cos - b * sin + x0);
                    int yy = (int) (a * sin + b * cos + y0);

                    if (xx >= 0 && xx < temp.getWidth() && yy >=0 && y < temp.getHeight()){
                        resultImage.setRGB(x,y,temp.getRGB(xx,yy));
                    }
                }
            }
            imageToSave = resultImage;
        }
        catch (NullPointerException e){
            JOptionPane.showMessageDialog(null, "No Image selected!", "Error!", JOptionPane.WARNING_MESSAGE);

        }

    }

    public void rotateRight(){
        // Algorithm from http://introcs.cs.princeton.edu/java/31datatype/Rotation.java.html
        try{
            BufferedImage temp = img;
            resultImage = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_RGB);

            double angle = Math.toRadians(-90.0);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            double x0 = 0.5 * (temp.getWidth()-1);
            double y0 = 0.5 * (temp.getHeight()-1);

            for(int x =0; x<temp.getWidth(); x++){
                for(int y =0; y<temp.getHeight(); y++){
                    double a = x - x0;
                    double b = y - y0;
                    int xx = (int) (a * cos - b * sin + x0);
                    int yy = (int) (a * sin + b * cos + y0);

                    if (xx >= 0 && xx < temp.getWidth() && yy >=0 && y < temp.getHeight()){
                        resultImage.setRGB(x,y,temp.getRGB(xx,yy));
                    }
                }
            }
            imageToSave = resultImage;
        }
        catch (NullPointerException e){
            JOptionPane.showMessageDialog(null, "No Image selected!", "Error!", JOptionPane.WARNING_MESSAGE);

        }

    }


    public static int getRed(int rgb){ return (rgb >> 16) & 0xff;}
    public static int getGreen(int rgb){ return (rgb >> 8) & 0xff;}
    public static int getBlue(int rgb){ return rgb & 0xff;}

    public static int rgbColor(int r, int g, int b){
        return (r << 16 ) | (g <<8 ) | b;
    }

    public static double brightness(int rgb){
        int r = getRed(rgb);
        int g = getGreen(rgb);
        int b = getBlue(rgb);
        return 0.21*r + 0.72*g + 0.07*b;
    }

    public void paint(Graphics g){
        //g.drawRect(0,200,460,510);
        final int imgX = 20;
        final int imgY = 150;
        final int resX = 510;
        //Strings
        g.setColor(Color.black);
        g.setFont(new Font("Serif",Font.BOLD, 50));
        g.drawString("Original", 120, imgY+550);
        g.drawString("Result", 650, imgY+550);
        g.setFont(new Font("Broadway", Font.BOLD, 60));
        g.drawString("ImageProcessor", 200, 80);

        //Bounding Box
        //g.drawRect(5,195,460,510);
        g.setColor(new Color(40,40,40));
        g.fillRect(imgX-5,imgY-5,460,510);
        g.fillRect(resX-5,imgY-5,460,510);
        //g.drawRect(495,195,460,510);

        //Images
        g.drawImage(img, imgX, imgY, null);
        g.drawImage(resultImage, resX, imgY, null);
        /**
        for(BufferedImage image : images) {
             g.drawImage(image, offset, 200, null);
             offset += image.getWidth()+50;
         }**/
    }
}
