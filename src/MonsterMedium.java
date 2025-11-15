import java.util.Random;
import java.util.Scanner;

public class MonsterMedium extends Monster {
    private String image = "\uD83D\uDC7B";

    public MonsterMedium(int sizeBoard) {
        super(sizeBoard);
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public boolean taskMonster(int difficultGame) {
        System.out.println("Решите задачу средней сложности:");

        int range = 50 + (difficultGame * 20);
        int x = r.nextInt(range);
        int y = r.nextInt(range);
        int operation = r.nextInt(3);

        int trueAnswer;
        String operationSymbol;

        switch (operation) {
            case 0:
                trueAnswer = x + y;
                operationSymbol = "+";
                break;
            case 1:
                trueAnswer = x - y;
                operationSymbol = "-";
                break;
            case 2:
                trueAnswer = x * y;
                operationSymbol = "*";
                break;
            default:
                trueAnswer = x + y;
                operationSymbol = "+";
        }

        System.out.println("Реши пример: " + x + " " + operationSymbol + " " + y + " = ?");
        Scanner sc = new Scanner(System.in);
        int ans = sc.nextInt();

        if (trueAnswer == ans) {
            System.out.println("Верно! Ты победил монстра средней сложности");
            return true;
        }
        System.out.println("Неправильно! Монстр средней сложности оказался сильнее");
        return false;
    }
}