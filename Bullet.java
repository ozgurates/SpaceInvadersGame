
import javax.swing.ImageIcon;

public class Bullet extends Base {
	


    public Bullet() {
    }

    public Bullet(int x, int y) {

        initShot(x, y);
    }

    private void initShot(int x, int y) {

        var shotImg = "res/shot.png";
        var img = new ImageIcon(shotImg);
        setImage(img.getImage());

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }
}
