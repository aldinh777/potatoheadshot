package aldinh777.potatoheadshot.content.capability;

public interface IManaStorage {
    void collectMana(int mana);
    void useMana(int mana);
    int getManaStored();
    int getMaxManaStored();
}
