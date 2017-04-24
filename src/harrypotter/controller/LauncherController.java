package harrypotter.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import harrypotter.model.character.GryffindorWizard;
import harrypotter.model.character.HufflepuffWizard;
import harrypotter.model.character.RavenclawWizard;
import harrypotter.model.character.SlytherinWizard;
import harrypotter.model.character.Wizard;
import harrypotter.view.ChoosingSpells;
import harrypotter.view.Launcher;
import harrypotter.view.MainLauncher;
import harrypotter.view.PreGameLauncher;

public class LauncherController implements ActionListener {
	private Launcher launcher;
	private MainLauncher mainLauncher;
	private PreGameLauncher preGameLauncher;
	private ChoosingSpells choosingSpells;
	private ArrayList <Wizard> champs;
	
	public LauncherController() throws IOException{
		// Initialize MainLauncher
		mainLauncher = new MainLauncher();
		// DragonInto gif
		JButton dragonintrobtn = new JButton();
		dragonintrobtn.setName("DragonButton");
		mainLauncher.addButtons(dragonintrobtn);
		// HarryPotter Logo
		JButton harrypotterlogobtn = new JButton();
		harrypotterlogobtn.setName("HarryPotterLogo");
		mainLauncher.addButtons(harrypotterlogobtn);
		// NewGameButton
		JButton newgamebtn = new JButton();
		newgamebtn.setName("NewGameButton");
		newgamebtn.addActionListener(this);
		mainLauncher.addButtons(newgamebtn);
        //Set main launcher to be visible 		
		mainLauncher.setVisible(true);
		launcher = new Launcher();
		launcher.addPanel(mainLauncher);
		//Initialize the champs ArrayList 
		this.champs = new ArrayList <Wizard>();
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		JButton btn  = (JButton) e.getSource();
		String btnID = btn.getName();
		switch(btnID){
		case "NewGameButton" : startPreGameLauncher();
							   break;
		case "RavenClawButton":
			if (this.champs.size() == 4) // If champions ArrayList is full notify the user
				 JOptionPane.showMessageDialog(launcher, "You reached the maximum number of champions");
			else{
				startChoosingSpells(btnID);
			}
			 break;
		case "HufflepuffButton":
			 if (this.champs.size() == 4)
				 JOptionPane.showMessageDialog(launcher, "You reached the maximum number of champions");
			 else{
					startChoosingSpells(btnID);
				}
			 break;
		case "GryffindorButton":
		     if (this.champs.size() == 4)
			     JOptionPane.showMessageDialog(launcher, "You reached the maximum number of champions");
		     else{
					startChoosingSpells(btnID);
				}
			 break;
		case "SlytherinButton":
			 if (this.champs.size() == 4)
				 JOptionPane.showMessageDialog(launcher, "You reached the maximum number of champions");
			 else{
					startChoosingSpells(btnID);
				}
			 break;
		case "CreateButton":
			if(this.champs.size() == 4)//If the user press create check if the Champions entered are 4 
				startTournament();
			else // Notify the user to choose more champions
				JOptionPane.showMessageDialog(launcher, "Please select " + (4 - this.champs.size())
						+ " more champions");
			break;
		}
	}
	public void startPreGameLauncher(){
		mainLauncher.removeAll();
		mainLauncher.revalidate();
		preGameLauncher = new PreGameLauncher();
		// RavenClaw Image Button
		JButton ravenclawbtn = new JButton();
		ravenclawbtn.setName("RavenClawButton");
		ravenclawbtn.addActionListener(this);
		preGameLauncher.addButtons(ravenclawbtn);
		// Hufflepuff Image Button
		JButton hufflepuffbtn = new JButton();
		hufflepuffbtn.setName("HufflepuffButton");
		hufflepuffbtn.addActionListener(this);
		preGameLauncher.addButtons(hufflepuffbtn);
		// Gryffindor Image Button
		JButton gryffindorbtn = new JButton();
		gryffindorbtn.setName("GryffindorButton");
		gryffindorbtn.addActionListener(this);
		preGameLauncher.addButtons(gryffindorbtn);
		// Slytherin Image Button
		JButton slytherinbtn = new JButton();
		slytherinbtn.setName("SlytherinButton");
		slytherinbtn.addActionListener(this);
		preGameLauncher.addButtons(slytherinbtn);
		// CreateButtonGlow Image
		// CreateButton Button
		JButton createbtn = new JButton();
		createbtn.setName("CreateButton");
		createbtn.addActionListener(this);
		preGameLauncher.addButtons(createbtn);
		//Make pre game launcher visible and adding it to frame
		preGameLauncher.setVisible(true); 
		launcher.addPanel(preGameLauncher);
	}
	
	public void startChoosingSpells(String btnID){
		
		preGameLauncher.removeAll();
		preGameLauncher.revalidate();
		choosingSpells = new ChoosingSpells(btnID);
		JTextField tf  = new JTextField();
		choosingSpells.addNameTextField(tf);
		JButton createcharacterbtn = new JButton();
		createcharacterbtn.setName("CreateCharacterButton");
		createcharacterbtn.addActionListener(this);
		choosingSpells.addButtons(createcharacterbtn);
		choosingSpells.setVisible(true); 
		launcher.addPanel(choosingSpells);
	}
	public void startTournament()
	{
		
	}
	public static void main(String [] args) throws IOException{
		new LauncherController();
	}

}
