package tankgame;

import java.util.Vector;

//敌方坦克
public class EnemyTank extends Tank{
    Vector<Shot> shots=new Vector<>();
    boolean isLive=true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }
}
