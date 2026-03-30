public class Mage extends Character{
    public Mage(){
        super();
    }
    public Mage(String name, int health, int attackPower){
        super(name, health, attackPower);
    }



    @Override
    public void attack(Character altCharacter) {
        System.out.println(this.name + " casts a fireball at " + altCharacter.name + "!");

        int actualDamage = this.attackPower;
        boolean isCritical = false; //used for critical hits state
        //critical hit logic
        if(Math.random() < 0.20){
            actualDamage*=2;
            isCritical = true;
            System.out.println("CRITICAL HIT! Double damage received");
        }
        //apply the calculated damage
        altCharacter.health -= actualDamage;

        //outcome
        System.out.println(altCharacter.name + " takes " + actualDamage + " damage! Remaining HP: " + Math.max(0,altCharacter.health));
        System.out.println("-------------------");
    }
}
