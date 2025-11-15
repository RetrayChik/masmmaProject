import java.util.Random;

public class ModifiedPerson extends Person {
    private int score = 0;
    private int maxLives = 3;

    public ModifiedPerson(int sizeBoard) {
        super(sizeBoard);
    }

    @Override
    public boolean moveCorrect(int x, int y) {
        boolean basicMove = super.moveCorrect(x, y);
        boolean diagonalMove = Math.abs(this.x - x) == 1 && Math.abs(this.y - y) == 1;

        return basicMove || (diagonalMove && score >= 10);
    }

    public void addScore(int points) {
        this.score += points;

        if (score % 20 == 0 && live < maxLives) {
            live++;
            System.out.println("Бонус! Получена дополнительная жизнь! Теперь жизней: " + live);
        }
    }

    public int getScore() {
        return score;
    }

    public void specialAbility() {
        if (score >= 15) {
            System.out.println("⚡ Активна специальная способность: следующий монстр будет проще!");
        }
    }
}