package edu.scu.oop.proj.view;

//GUIManager is the controller class to interact and pass information across different panels 
public class GUIManager {
    ContentPanel content_panel;
    HomebuttonPanel homebutton_panel;
    MenuPanel menu_panel; 
    MapPanel map_panel; 
    ViewCaloriePanel viewCalorie_Panel;
    ViewFundPanel viewFund_Panel;
    
    private static GUIManager gui_manager = new GUIManager();

    //singleton instance 
    public static GUIManager getInstance(){
        return gui_manager;
    }

    public void setContent_panel(ContentPanel cp){
        content_panel = cp;

    }

    public ContentPanel getContent_panel(){
        return content_panel;
    }

    public void setHomebutton_panel(HomebuttonPanel hp){
        homebutton_panel = hp;

    }
    
    public HomebuttonPanel getHomebutton_panel(){
        return homebutton_panel;
    }

    public void setViewCalorie_Panel(ViewCaloriePanel vc){
        this.viewCalorie_Panel = vc;

    }
    public ViewCaloriePanel getViewCalorie_Panel(){
        return viewCalorie_Panel;
    }
    public void setViewFund_Panel(ViewFundPanel vf){
        this.viewFund_Panel = vf;
    }
    public ViewFundPanel getViewFund_Panel(){
        return viewFund_Panel;
    }
    
    public void setMenu_Panel(MenuPanel mp) {
    	this.menu_panel = mp; 
    }
    
    public MenuPanel getMenu_Panel() {
    	return this.menu_panel; 
    }
    
    public MapPanel getMap_panel() {
    	return this.map_panel; 
    }
    
    public void setMap_Panel(MapPanel mapPanel) {
    	this.map_panel = mapPanel; 
    }
}
