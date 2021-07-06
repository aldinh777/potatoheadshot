package aldinh777.potatoheadshot.content.capability;

public interface IBloodStorage {
    float getBloodQuantity();
    float getMaxQuantity();
    void setBlood(float quantity);
    void useBlood(float quantity);
    void increaseBlood(float quantity);
}
