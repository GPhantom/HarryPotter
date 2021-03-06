package harrypotter.model.tournament;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import harrypotter.controller.TaskActionListener;
import harrypotter.exceptions.InCooldownException;
import harrypotter.exceptions.InvalidTargetCellException;
import harrypotter.exceptions.NotEnoughIPException;
import harrypotter.exceptions.OutOfBordersException;
import harrypotter.exceptions.OutOfRangeException;
import harrypotter.model.character.Champion;
import harrypotter.model.character.HufflepuffWizard;
import harrypotter.model.character.Wizard;
import harrypotter.model.magic.DamagingSpell;
import harrypotter.model.magic.HealingSpell;
import harrypotter.model.magic.RelocatingSpell;
import harrypotter.model.magic.Spell;
import harrypotter.model.world.Cell;
import harrypotter.model.world.ChampionCell;
import harrypotter.model.world.CollectibleCell;
import harrypotter.model.world.CupCell;
import harrypotter.model.world.Direction;
import harrypotter.model.world.EmptyCell;
import harrypotter.model.world.ObstacleCell;
import harrypotter.model.world.PhysicalObstacle;
import harrypotter.model.world.TreasureCell;
import harrypotter.model.world.WallCell;

public class ThirdTask extends Task {
	private Point cupCell;
	private TaskActionListener taskActionListener;

	public ThirdTask(ArrayList<Champion> champions)throws IOException
	{
		super(champions);
		generateMap();
		super.setCurrentChamp(super.getChampions().get(0));
	}
	@Override
	public void generateMap() throws IOException
	{
		super.generateMapWithEmptyCells();
		readMap("task3map.csv");
		super.addRandomPotions();
	}
	private void readMap(String filePath)throws IOException
	{
		String currentLine = "";
    	FileReader fileReader= new FileReader(filePath);
    	BufferedReader br = new BufferedReader(fileReader);
    	int c = 0;
    	while ((currentLine = br.readLine()) != null)
    	{
    	   String [] result= currentLine.split(",");
    	   for (int i = 0;i < result.length;i++)
    	   {
    		   switch(Tournament.parseInt(result[i])){
    		   case 0:
    			   super.getMap()[c][i] = new EmptyCell();
    			   break;
    		   case 1:
    			   if(super.getChampions().size() >= 1){
    				   super.getMap()[c][i] = new ChampionCell(super.getChampions()
    						   .get(0));
    				   ((Wizard)super.getChampions().get(0))
    				                .setLocation(new Point(c,i));
    			   }
    			   break;
    		   case 2:
    			   if(super.getChampions().size() >= 2){
    				   super.getMap()[c][i] = new ChampionCell(super.getChampions()
    						   .get(1));
    				   ((Wizard)super.getChampions().get(1))
		                            .setLocation(new Point(c,i));
    			   }
    			   break;
    		   case 3:
    			   if(super.getChampions().size() >= 3){
    				   super.getMap()[c][i] = new ChampionCell(super.getChampions()
    						   .get(2));
    				   ((Wizard)super.getChampions().get(2))
		                            .setLocation(new Point(c,i));
    			   }
    			   break;
    		   case 4:
    			   if(super.getChampions().size() >= 4){
    				   super.getMap()[c][i] = new ChampionCell(super.getChampions()
    						   .get(super.getChampions().size()-1));
    				   ((Wizard)super.getChampions().get(super.getChampions().size()-1))
		                           .setLocation(new Point(c,i));
    			   }
    			   break;
    		   case 5:
    			   super.getMap()[c][i] = new WallCell();
    			   break;
    		   case 6:
    			   int hp = 200 + (int)(Math.random() * ((300 - 200) + 1));
    			   super.getMap()[c][i] = new ObstacleCell(new PhysicalObstacle(hp));
    			   break;
    		   case 7:
    			   super.getMap()[c][i] = new CupCell();
    			   cupCell = new Point(c,i);
    			   break;
    		   default:
    			   break;
    		   }
    		   
    	   }
    	   c++;
    	}
	}
	public void setTaskActionListener(TaskActionListener taskActionListener)
	{
		this.taskActionListener = taskActionListener;
	}
	public TaskActionListener getTaskActionListener()
	{
		return this.taskActionListener;
	}
	@Override
	public void finalizeAction() throws IOException, OutOfBordersException
	{
		Wizard c = (Wizard) super.getCurrentChamp();
		Point p = c.getLocation();
		int x = (int) p.getX();
    	int y = (int) p.getY();
    	if(super.getFoundCollectible())
    	{
    		this.taskActionListener.showCollectible();
    		this.setFoundCollectible(false);
    	}
		if(this.getMap()[x][y] instanceof CupCell)
		{
			if(super.getListener() != null)
			{
				super.getListener().onFinishingThirdTask(this.getCurrentChamp());
    			return;
			}
		}
	    this.getMap()[x][y] = new ChampionCell(super.getCurrentChamp());
	    if(super.getAllowedMoves() == 0)
	    {
	    	endTurn();
	    	this.taskActionListener.updateNEWPanels();
	    }
	    else
			this.taskActionListener.moveGryffindorOnTrait();
	}
	@Override
	public void moveForward() throws IOException, OutOfBordersException, InvalidTargetCellException
	{
		super.moveForward();
		this.taskActionListener.moveUp();
		finalizeAction();
	}
	@Override
	public void moveBackward() throws IOException, OutOfBordersException, InvalidTargetCellException
	{
		super.moveBackward();
		this.taskActionListener.moveDown();
		finalizeAction();
	}
	@Override
	public void moveRight() throws IOException, OutOfBordersException, InvalidTargetCellException
	{
		super.moveRight();
		this.taskActionListener.moveRight();
		finalizeAction();
	}
	@Override
	public void moveLeft() throws IOException, OutOfBordersException, InvalidTargetCellException
	{
		super.moveLeft();
		this.taskActionListener.moveLeft();
		finalizeAction();
	}
	@Override
	public void endTurn() throws IOException, OutOfBordersException
	{
	   if(super.getChampions().size() != 0){
	     super.endTurn();
	     this.taskActionListener.updateNEWPanels();
	   }
	   else
	   {
		 if(super.getListener() != null)
			 super.getListener().onFinishingThirdTask(null);
	   }
	}
	@Override
	public void castDamagingSpell(DamagingSpell s, Direction d) throws IOException, NotEnoughIPException, OutOfBordersException, InvalidTargetCellException , InCooldownException{
		if(s.getCoolDown() > 0)
    		throw new InCooldownException(s.getCoolDown());
		Point p = directionToPoint(d, this.getCurrentChamp());
		if(p == null)
			throw new OutOfBordersException();
    	int x = (int) p.getX();
    	int y = (int) p.getY();
    	Cell cl = this.getMap()[x][y];
    	int cost = s.getCost();
    	int remainingip = cost - ((Wizard)super.getCurrentChamp()).getIp();
    	if(remainingip >= 0)
    		throw new NotEnoughIPException(cost, remainingip);
    	if(getMap()[p.x][p.y] instanceof CollectibleCell || 
    			getMap()[p.x][p.y] instanceof EmptyCell ||
    			getMap()[p.x][p.y] instanceof CupCell || 
    			getMap()[p.x][p.y] instanceof WallCell)
    		throw new InvalidTargetCellException();
 
    	if (cl instanceof ObstacleCell){
    		ObstacleCell o = (ObstacleCell) cl;
    		o.getObstacle().setHp(o.getObstacle().getHp() - s.getDamageAmount());
    		if(o.getObstacle().getHp() <= 0)
    		{
    			this.taskActionListener.castDamaging(p);
    			this.getMap()[x][y] = new EmptyCell();
    	    }
    	}
    	else if (cl instanceof ChampionCell){
    		ChampionCell c = (ChampionCell) cl;
    		Wizard w = (Wizard) c.getChamp();
    		if (w instanceof HufflepuffWizard){
    			int halfdamage = s.getDamageAmount() / 2;
    			w.setHp(w.getHp() - halfdamage);
    		}
    		else{
    			w.setHp(w.getHp() - s.getDamageAmount());
    		}
    		if(!isAlive(c.getChamp()))
    		{
    			this.taskActionListener.removeChamp(w, "Dead");
    			this.getMap()[x][y] = new EmptyCell();
    			removeWizard(c.getChamp());
    		}
    	}
    	useSpell(s);
    	finalizeAction();
    }
	@Override
	public void castHealingSpell(HealingSpell s) throws IOException, NotEnoughIPException, InCooldownException, OutOfBordersException{
		super.castHealingSpell(s);
		taskActionListener.castHealing();
		finalizeAction();
	}
	@Override
	 public void castRelocatingSpell(RelocatingSpell s,Direction d,Direction t,int r) throws IOException, NotEnoughIPException, InCooldownException, OutOfRangeException, OutOfBordersException, InvalidTargetCellException
    {
		super.castRelocatingSpell(s, d, t, r);
    	this.taskActionListener.castRelocating(super.directionToPoint(d, super.getCurrentChamp()), super.getExactPosition(super.getTargetPoint(t), t, r-1));
	    finalizeAction();
	}
	@Override
    public void onSlytherinTrait(Direction d) throws IOException, InCooldownException, OutOfBordersException, InvalidTargetCellException{
    	Wizard w = (Wizard) this.getCurrentChamp();
    	if (w.getTraitCooldown() > 0){
    		throw new InCooldownException(w.getTraitCooldown());
    	}
    	//Checks if target point inside map 
	    //Note: getExactPosition method could create point outside map and does not assign it to null
	    //not like getAdjacentCells method
    	Point champpoint  = w.getLocation();
    	Point firstpoint  = super.getExactPosition(w.getLocation(), d, 1);
    	Point secondpoint = super.getExactPosition(w.getLocation(), d, 2);
    	if (!( super.insideBoundary(firstpoint) || super.insideBoundary(secondpoint) ))    	
    		throw new OutOfBordersException();
    	
    	int cpx = (int) champpoint.getX();
    	int cpy = (int) champpoint.getY();
    	int fpx = (int) firstpoint.getX();
    	int fpy = (int) firstpoint.getY();
    	int spx = (int) secondpoint.getX();
    	int spy = (int) secondpoint.getY();
    	Cell cellofchamp  = this.getMap()[cpx][cpy];
    	Cell firstcell    = this.getMap()[fpx][fpy];
    	Cell secondcell   = this.getMap()[spx][spy];
    	if(!(secondcell instanceof EmptyCell))
    		throw new InvalidTargetCellException();

    	if (secondcell instanceof EmptyCell &&
    			(firstcell instanceof EmptyCell ||
    				firstcell instanceof ObstacleCell ||
    					firstcell instanceof WallCell)){
    					
    		w.setLocation(secondpoint);
    		this.getMap()[spx][spy] = cellofchamp;
    		this.getMap()[cpx][cpy] = new EmptyCell();
    	}
    	super.setTraitActivated(true);
		w.setTraitCooldown(10);
		super.setAllowedMoves(super.getAllowedMoves() - 1);
		finalizeAction();
    }
	public Object onRavenclawTrait() throws InCooldownException
	{
		ArrayList <Direction> hint = new ArrayList<Direction>();
		Wizard c = (Wizard) super.getCurrentChamp();
		Point champ = c.getLocation();
		int champx = (int) champ.getX();
		int champy = (int) champ.getY();
		int cupx = (int) this.cupCell.getX();
		int cupy = (int) this.cupCell.getY();
		if (c.getTraitCooldown() > 0){
    		throw new InCooldownException(c.getTraitCooldown());
    	}
		if(champy > cupy)
			hint.add(Direction.LEFT);
		else if(cupy > champy)
			hint.add(Direction.RIGHT);
		if(champx > cupx)
			hint.add(Direction.FORWARD);
		else if(cupx > champx)
			hint.add(Direction.BACKWARD);
		super.setTraitActivated(true);
		c.setTraitCooldown(7);
	    this.taskActionListener.showHint(hint);
		return hint;
	}
	public void useSpell(Spell s) throws NotEnoughIPException{
		super.useSpell(s);
	}

}
