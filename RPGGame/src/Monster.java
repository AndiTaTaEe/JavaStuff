public class Monster extends Character{

    private int maxHealth; // needed for remembering where it started
    public Monster(){
        super();
    }

    public Monster(String name, int health, int attackPower){
        super(name, health, attackPower);
        this.maxHealth = health; // storing the starting health into the maxHealth
    }

    //overriding the setter to keep maxHealth accurate if method chaining used

    public Monster setHealth(int health){
        super.setHealth(health);
        this.maxHealth = health;
        return this;
    }


    @Override
    public void attack(Character altCharacter) {
        System.out.println(this.name + " lunges aggressively at " + altCharacter.name + "!");
        int actualDamage = this.attackPower;
        //calculating the 30% threshold
        double rageThreshold = this.maxHealth * 0.30;
        if (this.health > 0 && this.health < rageThreshold){
            System.out.println("ENRAGED! " + this.name + " attacks with reckless fury!");

            //when enraged, increase damage by 1.5
            actualDamage = (int)(this.attackPower*1.5); // used casting to int
        }

        altCharacter.health-=actualDamage;

        System.out.println(altCharacter.name + " takes " + actualDamage + " damage! Remaining HP: " + Math.max(0,altCharacter.health));
        System.out.println("-------------------");

    }
}
