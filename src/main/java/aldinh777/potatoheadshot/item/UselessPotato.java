package aldinh777.potatoheadshot.item;

public class UselessPotato extends PotatoItem {

    public UselessPotato() {
        super("useless_potato", 0);
        this.setContainerItem(this);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean isRepairable() {
        return true;
    }
}
