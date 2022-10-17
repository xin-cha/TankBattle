package tankgame;

import javax.swing.*;

public class TankFrame extends JFrame {
    //定义一个MyPaneL
    MyPanel mp=null;
    public static void main(String[] args) {
        TankFrame tankFrame = new TankFrame();

    }
    public TankFrame(){
        mp=new MyPanel();
        this.add(mp);//把游戏绘图区加入
        this.setSize(1000,750);
        //添加监听
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
