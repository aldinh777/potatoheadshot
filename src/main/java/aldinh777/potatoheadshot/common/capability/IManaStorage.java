package aldinh777.potatoheadshot.common.capability;

public interface IManaStorage {
    void collectMana(int mana);
    void useMana(int mana);
    int getManaStored();
    int getMaxManaStored();
}
