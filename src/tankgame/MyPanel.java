package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

//坦克大战绘图区
//为了监听键盘事件，实现KeyListener
//为了让Panel不停重绘，需要实现runnable接口线程
public class MyPanel extends JPanel implements KeyListener,Runnable {
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
            //给该坦克加入一颗子弹
            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
            //加入enmeyTank的Vector成员
            enemyTank.shots.add(shot);
            //立即启动
            Thread thread = new Thread(shot);
            thread.start();
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
        for (int i=0;i<enemyTanks.size();i++) {
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前坦克是否还存货
            if (enemyTank.isLive) {//当敌人坦克还存货，才画出该坦克
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画出所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 2, 2, false);
                    } else {
                        //从Vector中移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
        //画出射击的子弹
        if (myTank.shot!=null&& myTank.shot.isLive == true){
            g.draw3DRect(myTank.shot.x,myTank.shot.y,2,2,false);
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
    //编写方法判断我方子弹是否击中敌人坦克
    public static void hitTank(Shot s,EnemyTank enemyTank){
        //判断s击中
        switch (enemyTank.getDirect()){
            case 0:
            case 2:
                if (s.x>enemyTank.getX()&&s.y<enemyTank.getY()+40 &&s.y>enemyTank.getY()&&s.y<enemyTank.getY()+60){
                    s.isLive=false;
                    enemyTank.isLive=false;
                }
                break;
            case 1:
            case 3://向右
                if (s.x>enemyTank.getX()&&s.y<enemyTank.getY()+60 &&s.y>enemyTank.getY()&&s.y<enemyTank.getY()+40){
                    s.isLive=false;
                    enemyTank.isLive=false;
                }
                break;

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
        //让面板重绘
        this.repaint();
        if (e.getKeyCode() ==KeyEvent.VK_J) {//发送子弹
            myTank.shotEnemyTank();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每隔100ms重绘区域
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断是否击中了敌人坦克
            if(myTank.shot!=null &&myTank.shot.isLive){//当前我的子弹还存活，就遍历敌人所有的坦克
                for (int i=0;i<enemyTanks.size();i++){
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(myTank.shot,enemyTank);
                }

            }
            this.repaint();
        }
    }
}
