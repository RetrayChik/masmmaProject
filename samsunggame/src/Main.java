import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String castle = "\uD83C\uDFF0";
        int sizeBoard = 5;

        EnhancedPerson person = new EnhancedPerson(sizeBoard);
        int step = 0;

        String[][] board = new String[sizeBoard][sizeBoard];
        for (int y = 0; y < sizeBoard; y++) {
            for (int x = 0; x < sizeBoard; x++) {
                board[y][x] = "  ";
            }
        }

        int countMonster = sizeBoard;
        Random r = new Random();

        Monster[] arrMonster = new Monster[countMonster];
        Item[] items = new Item[2];

        items[0] = new Item(Item.ItemType.HEALTH_POTION);
        items[1] = new Item(Item.ItemType.SHIELD);

        for (Item item : items) {
            int itemX, itemY;
            do {
                itemX = r.nextInt(sizeBoard);
                itemY = r.nextInt(sizeBoard);
            } while (!board[itemY][itemX].equals("  "));
            board[itemY][itemX] = item.getSymbol();
        }

        int count = 0;
        Monster test;

        while (count < countMonster) {
            int monsterType = r.nextInt(3);
            switch (monsterType) {
                case 0:
                    test = new Monster(sizeBoard);
                    break;
                case 1:
                    test = new MonsterMedium(sizeBoard);
                    break;
                case 2:
                    test = new BigMonster(sizeBoard);
                    break;
                default:
                    test = new Monster(sizeBoard);
            }

            if (board[test.getY()][test.getX()].equals("  ")) {
                board[test.getY()][test.getX()] = test.getImage();
                arrMonster[count] = test;
                count++;
            }
        }

        int castleX = r.nextInt(sizeBoard);
        int castleY = 0;
        board[castleY][castleX] = castle;

        System.out.println("== ДОБРО ПОЖАЛОВАТЬ! ==");
        System.out.println("Привет! Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");

        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine().toUpperCase();

        switch (answer) {
            case "ДА" -> {
                System.out.println("Выбери сложность игры (от 1 до 5):");
                int difficultGame = sc.nextInt();
                System.out.println("Выбранная сложность: " + difficultGame);
                System.out.println("Цель: дойти до замка 🏰, избегая монстров!");
                System.out.println("На карте есть предметы: 🧪 Зелье здоровья, 🛡️ Щит");
                System.out.println("⚡ Команды: 'SP' - способность, 'INV' - инвентарь, 'USE N' - использовать предмет N");

                boolean usedSpecialAbility = false;

                while (person.getLive() > 0) {
                    board[person.getY() - 1][person.getX() - 1] = person.getImage();
                    outputEnhancedBoard(board, person.getLive(), person.getScore(), person.getInventorySize());

                    if (person.getScore() >= 10 && !usedSpecialAbility) {
                        System.out.println("⚡ Доступна специальная способность! Напиши 'SP' чтобы использовать");
                    }

                    System.out.println("Введите команду или координаты (x y):");
                    System.out.println("Координаты персонажа - x: " + person.getX() + ", y: " + person.getY());

                    String input = sc.next();

                    if (input.equalsIgnoreCase("SP") && person.getScore() >= 10 && !usedSpecialAbility) {
                        usedSpecialAbility = true;
                        System.out.println("Специальная способность активирована! Следующий монстр будет проще.");
                        continue;
                    } else if (input.equalsIgnoreCase("INV")) {
                        person.showInventory();
                        continue;
                    } else if (input.equalsIgnoreCase("USE")) {
                        int itemIndex = sc.nextInt() - 1;
                        if (itemIndex >= 0 && itemIndex < person.getInventorySize()) {
                            person.useItem(itemIndex);
                        } else {
                            System.out.println("❌ Неверный номер предмета");
                        }
                        continue;
                    }

                    int x = Integer.parseInt(input);
                    int y = sc.nextInt();

                    if (person.moveCorrect(x, y)) {
                        String nextCell = board[y - 1][x - 1];

                        if (nextCell.equals("  ")) {
                            board[person.getY() - 1][person.getX() - 1] = "  ";
                            person.move(x, y);
                            step++;
                            person.addScore(1);
                            System.out.println("✅ Ход корректный! Ход номер: " + step);

                        } else if (nextCell.equals(castle)) {
                            System.out.println("🎉 ПОЗДРАВЛЯЮ! Вы прошли игру!");
                            System.out.println("🏆 Итоговый счет: " + person.getScore());
                            System.out.println("📊 Количество ходов: " + step);
                            break;

                        } else if (nextCell.equals("🧪") || nextCell.equals("🛡️")) {
                            board[person.getY() - 1][person.getX() - 1] = "  ";
                            person.move(x, y);

                            Item foundItem = null;
                            for (Item item : items) {
                                if (item.getSymbol().equals(nextCell)) {
                                    foundItem = item;
                                    break;
                                }
                            }

                            if (foundItem != null) {
                                person.addItem(foundItem);
                                person.addScore(3);
                            }

                        } else {
                            boolean monsterDefeated = false;
                            for (Monster monster : arrMonster) {
                                if (monster != null && monster.conflictPerson(x, y)) {
                                    System.out.println("⚔️ Встреча с монстром!");

                                    if (person.hasShield()) {
                                        System.out.println("🛡️  Щит защитил вас от монстра!");
                                        person.consumeShield();
                                        monsterDefeated = true;
                                    } else {
                                        if (usedSpecialAbility) {
                                            monsterDefeated = monster.taskMonster(Math.max(1, difficultGame - 2));
                                            usedSpecialAbility = false;
                                        } else {
                                            monsterDefeated = monster.taskMonster(difficultGame);
                                        }
                                    }

                                    if (monsterDefeated) {
                                        board[person.getY() - 1][person.getX() - 1] = "  ";
                                        person.move(x, y);
                                        int scoreBonus = 5;

                                        person.addScore(scoreBonus);
                                        System.out.println("➕ +" + scoreBonus + " очков за победу над монстром!");
                                    } else {
                                        person.downLive();
                                        System.out.println("💔 Потеряна одна жизнь! Осталось: " + person.getLive());
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println("❌ Некорректный ход! Можно ходить только на соседние клетки.");
                        System.out.println("💡 Подсказка: после 5 очков доступны диагональные ходы");
                    }

                    if (person.getLive() <= 0) {
                        System.out.println("💀 ИГРА ОКОНЧЕНА! Закончились жизни.");
                        System.out.println("🏆 Итоговый счет: " + person.getScore());
                        break;
                    }
                }
            }
            case "НЕТ" -> System.out.println("Жаль, приходи еще!");
            default -> System.out.println("Данные введены некорректно");
        }
        sc.close();
    }

    static void outputEnhancedBoard(String[][] board, int live, int score, int inventorySize) {
        String leftBlock = "| ";
        String rightBlock = "|";
        String wall = "+ —— + —— + —— + —— + —— +";

        System.out.println("\n" + "=".repeat(45));
        System.out.println("Текущее поле:");
        for (String[] raw : board) {
            System.out.println(wall);
            for (String col : raw) {
                System.out.print(leftBlock + col + " ");
            }
            System.out.println(rightBlock);
        }
        System.out.println(wall);

        System.out.println("❤️  Жизни: " + live + " | Счет: " + score + " | Предметы: " + inventorySize);
        System.out.println("=".repeat(45));
    }
}