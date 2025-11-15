import java.util.ArrayList;
import java.util.List;

public class EnhancedPerson extends ModifiedPerson {
    private List<Item> inventory;
    private boolean hasShield;
    private boolean doubleNextScore;

    public EnhancedPerson(int sizeBoard) {
        super(sizeBoard);
        this.inventory = new ArrayList<>();
        this.hasShield = false;
        this.doubleNextScore = false;
    }

    @Override
    public boolean moveCorrect(int x, int y) {
        boolean basicMove = super.moveCorrect(x, y);
        boolean diagonalMove = Math.abs(this.getX() - x) == 1 && Math.abs(this.getY() - y) == 1;

        return basicMove || (diagonalMove && getScore() >= 5);
    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println("Получен предмет: " + item.getName() + " " + item.getSymbol());
    }

    public void useItem(int index) {
        if (index >= 0 && index < inventory.size()) {
            Item item = inventory.remove(index);
            applyItemEffect(item);
        }
    }

    private void applyItemEffect(Item item) {
        switch (item.getType()) {
            case HEALTH_POTION:
                addScore(0);
                System.out.println("🧪 Зелье здоровья выпито! Следующий бонус будет раньше");
                break;
            case SHIELD:
                hasShield = true;
                System.out.println("🛡️  Щит активирован! Следующий монстр не нанесет урона");
                break;
        }
    }

    public boolean hasShield() {
        return hasShield;
    }

    public void consumeShield() {
        hasShield = false;
    }

    public boolean isDoubleNextScore() {
        return doubleNextScore;
    }

    public void consumeDoubleScore() {
        doubleNextScore = false;
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Инвентарь пуст");
        } else {
            System.out.println("Ваш инвентарь:");
            for (int i = 0; i < inventory.size(); i++) {
                Item item = inventory.get(i);
                System.out.println((i + 1) + ". " + item.getSymbol() + " " + item.getName() + " - " + item.getDescription());
            }
        }
    }

    public int getInventorySize() {
        return inventory.size();
    }
}