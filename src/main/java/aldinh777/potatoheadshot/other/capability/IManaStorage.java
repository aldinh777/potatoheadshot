package aldinh777.potatoheadshot.other.capability;

public interface IManaStorage {
    void collectMana(int mana);
    void useMana(int mana);
    int getManaStored();
    int getMaxManaStored();
}
