package tankgame;

import javax.swing.*;
import java.awt.*;

//坦克大战绘图区
public class MyPanel extends JPanel {
    //定义我的坦克
    MyTank myTank=null;
    public MyPanel(){
        myTank=new MyTank(100,100);//初始化一个坦克
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);//填充矩形
        //画出坦克--封装方法
        drawTank(myTank.getX(),myTank.getY(),g,0,1);

    }
    /**
     * x 坦克的左上角x坐标
     * y 坦克的左上角y坐标
     * g 画笔
     * direct 坦克方向（上下左右）
     * type 坦克类型
    * */
    public void drawTank(int x,int y,Graphics g,int direct,int type){
        //根据坦克的不同类型设置不同的颜色
        switch (type){
            case 0://我的坦克
                g.setColor(Color.cyan);//青色
                break;
            case 1://敌人的坦克
                g.setColor(Color.yellow);
                break;
        }
        //根据坦克的方向来绘制坦克
        switch (direct){
            case 0://向上
                g.fill3DRect(x,y,10,60,false);//画出坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false);//画出坦克右边轮子
                g.fill3DRect(x+10,y+10,20,40,false);//画出坦克中间矩形盖子
                g.fillOval(x+10,y+20,20,20);//画出坦克中间圆形盖子
                g.drawLine(x+20,y,x+20,y+30);//坦克炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }

    }
}
