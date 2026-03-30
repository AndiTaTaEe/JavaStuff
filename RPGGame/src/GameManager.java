public class GameManager {

    static int turnCount = 1;

    public static void main(String[] args) {
        Character hero = new Warrior().setName("John").setHealth(200).setAttackPower(25);
        Character monster = new Monster().setHealth(150).setName("Azrael").setAttackPower(20);

        System.out.println("Battle begins between " + hero.getName() + " and " + monster.getName() + "!\n");

        while(hero.getHealth() > 0 && monster.getHealth() > 0){
            System.out.println("ROUND " + turnCount + "!");

            //hero attack logic
            hero.attack(monster);
            //checking if monster's health < 0
            if (monster.getHealth() <= 0) {
                System.out.println(monster.getName() + " is dead!");
                break;
            }

            monster.attack(hero);

            if (hero.getHealth() <= 0){
                System.out.println(hero.getName() + " is dead!");
                break;
            }
            turnCount++;
        }

        if (hero.getHealth() > 0){
            System.out.println(hero.getName() + " won the battle with " + hero.getHealth() + " HP!");
        } else if (monster.getHealth() > 0) {
            System.out.println(monster.getName() + " won the battle with " + monster.getHealth() + " HP!");

        }

    }
}