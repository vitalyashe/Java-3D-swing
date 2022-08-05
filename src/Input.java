import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class Input implements KeyListener, MouseMotionListener {
    private Field field;
    private AtomicBoolean isBlocked = new AtomicBoolean(false);

    public Input(Field field) {
        this.field = field;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            field.addToPosition(new Vector(-0.1, 0, 0));
        }

        if (key == KeyEvent.VK_RIGHT) {
            field.addToPosition(new Vector(0.1, 0, 0));
        }

        if (key == KeyEvent.VK_UP) {
            field.addToPosition(new Vector(0, 0, -0.1));
        }

        if (key == KeyEvent.VK_DOWN) {
            field.addToPosition(new Vector(0, 0, 0.1));
        }

        if (key == KeyEvent.VK_SHIFT) {
            field.addToPosition(new Vector(0, -0.1, 0));
        }

        if (key == KeyEvent.VK_CONTROL) {
            field.addToPosition(new Vector(0, 0.1, 0));
        }

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        synchronized (this) {
            if (isBlocked.get()) {
                return;
            }

        }

        if (field.getScreenWidth() / 2 != e.getX()) {
            double diff = field.getScreenWidth() / 2 - e.getX();
            double angle = (diff > 0)? 0.5: -0.5;

            field.mulTransformToMatrix(Matrix.rotateY((angle * Math.PI) / 180));
        }

        if (field.getScreenHeight() / 2 != e.getY()) {
            double diff = field.getScreenHeight() / 2 - e.getY();
            double angle = (diff > 0)? -0.5: 0.5;

            field.mulTransformToMatrix(Matrix.rotateX((angle * Math.PI) / 180));
        }
    }

    public void block()
    {
        synchronized (this) {
            while(!isBlocked.compareAndSet(false, true));
        }

    }

    public void unblock()
    {
        synchronized (this) {
            while(!isBlocked.compareAndSet(true, false)) ;
        }
    }
}
