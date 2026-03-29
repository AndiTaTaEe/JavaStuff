public abstract class Character {
    protected String name;
    protected int health;
    protected int attackPower;

    //empty constructor - FOR CHAINING
    public Character(){

    }

    //constructor for character
    public Character(String name, int health, int attackPower){
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    //setters
    public Character setName(String name) {
        this.name = name;
        return this;
    }

    public Character setHealth(int health){
        this.health = health;
        return this;
    }

    public Character setAttackPower(int attackPower){
        this.attackPower = attackPower;
        return this;
    }

    //getters
    public String getName() {
        return name;
    }
    public int getHealth() {
        return health;
    }
    public int getAttackPower() {
        return attackPower;
    }

    public abstract void attack(Character altCharacter); // blueprint of the attack method - will be implemented in subclasses
}
