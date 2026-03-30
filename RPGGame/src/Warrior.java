public class Warrior extends Character{

    //constructor for Warrior - using super() for setting up the logic
    public Warrior(){
        super();
    }

    public Warrior(String name, int health, int attackPower){
        super(name,health,attackPower);
    }


    @Override
    public void attack(Character altCharacter) {
        System.out.println(this.name + " swings a might sword at " + altCharacter.name);

        //modifying the target's health
        altCharacter.health -= this.attackPower;
        System.out.println(altCharacter.name + " takes " + this.attackPower + " damage! Remaining HP: " + Math.max(0,altCharacter.health));
        System.out.println("-------------------");

    }
}
