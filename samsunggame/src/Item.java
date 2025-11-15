public class Item {
    private String name;
    private String symbol;
    private String description;
    private ItemType type;

    public enum ItemType {
        HEALTH_POTION, SHIELD
    }

    public Item(ItemType type) {
        this.type = type;
        switch (type) {
            case HEALTH_POTION:
                this.name = "Зелье здоровья";
                this.symbol = "🧪";
                this.description = "Восстанавливает 1 жизнь";
                break;
            case SHIELD:
                this.name = "Щит";
                this.symbol = "🛡️";
                this.description = "Защищает от следующего монстра";
                break;
        }
    }

    public String getName() { return name; }
    public String getSymbol() { return symbol; }
    public String getDescription() { return description; }
    public ItemType getType() { return type; }
}