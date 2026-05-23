package exp2;

public class PenFactory {

    public static void main(String[] args) {
        Factory penFactory = new Factory(PenType.Pen);
        WritingTool pen = penFactory.getPen();
        pen.write();

        Factory ballPenFactory = new Factory(PenType.BallPen);
        WritingTool ballPen = ballPenFactory.getPen();
        ballPen.write();

        Factory brushPenFactory = new Factory(PenType.BrushPen);
        WritingTool brushPen = brushPenFactory.getPen();
        brushPen.write();
    }
}

class Factory {

    private PenType type;

    public Factory(PenType type) {
        this.type = type;
    }

    public WritingTool getPen() {
        return new WritingTool(type);
    }
}

enum PenType {
    Pen, BallPen, BrushPen
}

class WritingTool {

    private PenType type;

    public WritingTool(PenType type) {
        this.type = type;
    }

    public void write() {
        switch (type) {
            case Pen:
                System.out.println("[*] Writing with a regular pen.");
                break;
            case BallPen:
                System.out.println("[*] Writing with a ball pen.");
                break;
            case BrushPen:
                System.out.println("[*] Writing with a brush pen.");
                break;
        }
    }

    public PenType getType() {
        return type;
    }
}
