package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

//坦克大战绘图区
//为了监听键盘事件，实现KeyListener
public class MyPanel extends JPanel implements KeyListener {
    //定义我的坦克
    MyTank myTank=null;
    //定义敌人坦克，放入Vector中
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //坦克数量
    int enemyTankSNum=3;

    public MyPanel(){

        myTank=new MyTank(300,300);//初始化一个坦克
        myTank.setSpeed(4);//速度
        //  初始化敌人的坦克
        for(int i=0;i<enemyTankSNum;i++){
            //确保每一个加进去的坦克方向向下
            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
            enemyTank.setDirect(2);
            enemyTanks.add(enemyTank);

        }


    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);//填充矩形
        //画出自己的坦克--封装方法
        drawTank(myTank.getX(),myTank.getY(),g,myTank.getDirect(),1);
        //画出敌人的坦克--遍历Vector
        for (int i=0;i<enemyTanks.size();i++){
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirect(),0);
        }


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
        //根据坦克的方向来绘制对应形状坦克
        switch (direct){
            case 0://向上
                g.fill3DRect(x,y,10,60,false);//画出坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false);//画出坦克右边轮子
                g.fill3DRect(x+10,y+10,20,40,false);//画出坦克中间矩形盖子
                g.fillOval(x+10,y+20,20,20);//画出坦克中间圆形盖子
                g.drawLine(x+20,y,x+20,y+30);//坦克炮筒
                break;
            case 1://向右
                g.fill3DRect(x,y,60,10,false);//画出坦克左边轮子
                g.fill3DRect(x,y+30,60,10,false);//画出坦克右边轮子
                g.fill3DRect(x+10,y+10,40,20,false);//画出坦克中间矩形盖子
                g.fillOval(x+20,y+10,20,20);//画出坦克中间圆形盖子
                g.drawLine(x+60,y+20,x+30,y+20);//坦克炮筒
                break;
            case 2://向下
                g.fill3DRect(x,y,10,60,false);//画出坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false);//画出坦克右边轮子
                g.fill3DRect(x+10,y+10,20,40,false);//画出坦克中间矩形盖子
                g.fillOval(x+10,y+20,20,20);//画出坦克中间圆形盖子
                g.drawLine(x+20,y+60,x+20,y+30);//坦克炮筒
                break;
            case 3://向左
                g.fill3DRect(x,y,60,10,false);//画出坦克左边轮子
                g.fill3DRect(x,y+30,60,10,false);//画出坦克右边轮子
                g.fill3DRect(x+10,y+10,40,20,false);//画出坦克中间矩形盖子
                g.fillOval(x+20,y+10,20,20);//画出坦克中间圆形盖子
                g.drawLine(x,y+20,x+30,y+20);//坦克炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    //处理wasd建按下情况
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() ==KeyEvent.VK_W){//按下w键，改变坦克方向
            myTank.setDirect(0);
            myTank.moveUp();
        } else if (e.getKeyCode() ==KeyEvent.VK_D) {
            myTank.setDirect(1);
            myTank.moveRright();
        } else if (e.getKeyCode() ==KeyEvent.VK_S) {
            myTank.setDirect(2);
            myTank.moveDown();
        } else if (e.getKeyCode() ==KeyEvent.VK_A) {
            myTank.setDirect(3);
            myTank.moveLeft();
        }
        //让面板冲毁
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
