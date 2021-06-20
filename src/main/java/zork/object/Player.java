package zork.object;

import zork.object.item.Item;
import zork.object.item.weapons.Weapon;
import zork.object.monster.Monster;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public Boolean alive;
    public int HP;
    public static final int MAX_HP = 100;
    public int attackPower;
    public Item weapon;
    public List<Item> items;
    public int agility;
    public int exp;
    public int level;
    public int MAX_EXP;

    public Player() {
        alive = true;
        HP = MAX_HP;
        attackPower = 10;
        weapon = null;
        agility = 10;
        exp = 0;
        level = 1;
        MAX_EXP = 100;
        items = new ArrayList<>();
    }

    public void increaseHP(int fixHP) {
        if(this.HP + fixHP > MAX_HP) {
            this.HP = MAX_HP;
        }
        else {
            this.HP += fixHP;
        }
    }

    public void decreaseHP(int fixHP) {
        this.HP -= fixHP;
        if(this.HP <= 0) {
            this.alive = false;
        }
    }

    public void attack(Monster monster) {
        if(this.weapon instanceof Weapon) {
            if (this.agility+((Weapon) this.weapon).agility >= monster.agility) {
                int totalAttackPower = this.attackPower + ((Weapon) this.weapon).strength;
                monster.decreaseHP(totalAttackPower);

            }
            else {
                this.decreaseHP(monster.attackPower);
                if (this.alive == true) {
                    int totalAttackPower = this.attackPower + ((Weapon) this.weapon).strength;
                    monster.decreaseHP(totalAttackPower);
                }
            }
        }
        else {
            if (this.agility >= monster.agility) {
                monster.decreaseHP(this.attackPower);
                System.out.println("Monster HP Remaining : " + monster.HP);
            }
            else {
                this.decreaseHP(monster.attackPower);
                if (this.alive == true) {
                    int totalAttackPower = this.attackPower + ((Weapon) this.weapon).strength;
                    monster.decreaseHP(totalAttackPower);
                }
            }
        }
        if (monster.alive) {
            this.decreaseHP(monster.attackPower);
            if(!this.alive == true) {
                System.out.println("Game Over");
                System.exit(0);
            }
        }
        else {
            this.gainEXP(monster.expDrop);
            // not finish
        }
    }
    public void levelUp(int level) {
        int increasePower = attackPower*level;
        int increaseHP = HP*level;
        int newMAX_EXP = MAX_HP*level;
        attackPower = increasePower;
        HP = increaseHP;
        MAX_EXP = newMAX_EXP;

    }

    public void gainEXP(int exp) {
        if (this.exp + exp >= MAX_EXP) {
            this.exp = 0;
            level = level+1;
            levelUp(level);
        }
        else {
            this.exp = this.exp + exp;
        }
    }

    public void pickWeapon(Item item) {
        if (item instanceof Weapon) {
            this.weapon = item;
        }
    }
    public void takeItems(Item item) {
        this.items.add(item);
    }
}
