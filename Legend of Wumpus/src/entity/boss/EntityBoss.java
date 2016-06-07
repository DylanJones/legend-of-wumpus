package entity.boss;

import entity.Entity;

public abstract class EntityBoss extends Entity {

	/**
	 * Serial ID for Serialization to disk
	 */
	private static final long serialVersionUID = 1565443232121111L;

	public EntityBoss() { // Bosses in 4H.wld, 4M.wld and 8E.wld
		super();
	}
}
