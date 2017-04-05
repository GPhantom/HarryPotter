package harrypotter.model.character;

import java.io.IOException;

import harrypotter.exceptions.InCooldownException;
import harrypotter.exceptions.OutOfBordersException;
import harrypotter.model.world.Direction;

public class SlytherinWizard extends Wizard implements Champion{
	private Direction traitDirection;
	
	public SlytherinWizard(String name){
		super(name,850,550);
		super.setDefaultHp(850);
		super.setDefaultIp(550);
		
	}
	
	public Direction getTraitDirection() {
		return traitDirection;
	}

	public void setTraitDirection(Direction traitDirection) {
		this.traitDirection = traitDirection;
	}

	public void useTrait() throws IOException, InCooldownException, OutOfBordersException
	{
		if(super.getListener() != null)
			super.getListener().onSlytherinTrait(traitDirection);
	}

}
