package org.jsflot.demo.managed;

import javax.faces.event.ActionEvent;

public class NavigationMBean {
	private String tutorialNavigation = "firstpage.jsf";

	public String navigateToLiveDemo() {
		return "navigateTo.liveDemo";
	}
	
	public String navigateToGettingStarted() {
		return "navigateTo.gettingStarted";
	}
	
	public String navigateToFeatures() {
		return "navigateTo.features";
	}
	
	public String navigateToTutorial() {
		return "navigateTo.tutorial";
	}
	
	public String getTutorialNavigation() {
		return tutorialNavigation;
	}
	
	public void navigateToTutorialPage(ActionEvent event) {
		//System.out.println("Navigating to Tutorial page");
	}
	
	public void setTutorialNavigation(String tutorialNavigation) {
		this.tutorialNavigation = tutorialNavigation;
	}
}
