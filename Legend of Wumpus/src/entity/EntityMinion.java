package entity;

/** This class is the superclass for all hostile living things. */
public abstract class EntityMinion extends Entity {
	public abstract void attack(Entity target);

	public EntityMinion() {
		super();
	}
}