/**
 * 
 */
package org.emmanuel.church.mark.sys;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;

import org.emmanuel.church.mark.db.JDBModule;

/**
 * @author smhong
 * 
 */
public class CustomMenus {
	public static String MENU_CATEGORY_OVERVIEW = "Overview";
	public static String MENU_ITEM_PRINT = "Print";
	public static String MENU_ITEM_PRINT_ID = "PrintId";
	public static String MENU_ITEM_PRINT_MODULEID = "PrintMId";
	
	public static String MENU_CATEGORY_ADMINISTARTION = "Administration";
	public static String MENU_CATEGORY_GAME_OPERATION = "Game Operation";
	public static String MENU_CATEGORY_GAME_REPORT = "Game Report";

	public static String MENU_ITEM_SETTINGS = "Settings";
	public static String MENU_ITEM_SETTINGS_ID = "PrintId";
	public static String MENU_ITEM_SETTINGS_MODULEID = "PrintMId";
	
	public static String MENU_ITEM_TOOLS = "Tools";
	public static String MENU_ITEM_TOOLS_ID = "PrintId";
	public static String MENU_ITEM_TOOLS_MODULEID = "PrintMId";

	private HashMap<JMenuOption, HashMap<String, JMenuOption>> menuMap;
	/**
	 * 
	 */
	public CustomMenus() {
		if(menuMap == null){
			menuMap = new HashMap<JMenuOption, HashMap<String, JMenuOption>>();
			JMenuOption mOverview = addCustomMenu(MENU_CATEGORY_OVERVIEW, MENU_CATEGORY_OVERVIEW, "Overview", "FORM", 0 ); 
			menuMap.put(mOverview, new HashMap<String, JMenuOption>());
			menuMap.get(mOverview).put(MENU_ITEM_PRINT, (JMenuOption) addCustomMenu(MENU_ITEM_PRINT_ID,MENU_ITEM_PRINT_MODULEID, "Print", "EXEC", 1));
			
			JMenuOption mAdministration = addCustomMenu(MENU_CATEGORY_ADMINISTARTION, MENU_CATEGORY_ADMINISTARTION, "Administration", "FORM", 0); 
			menuMap.put(mAdministration, new HashMap<String, JMenuOption>());
			menuMap.get(mAdministration).put(MENU_ITEM_SETTINGS, (JMenuOption) addCustomMenu(MENU_ITEM_SETTINGS_ID, MENU_ITEM_SETTINGS_MODULEID, "Setting", "FORM", 2));
			menuMap.get(mAdministration).put(MENU_ITEM_TOOLS, (JMenuOption) addCustomMenu(MENU_ITEM_TOOLS_ID, MENU_ITEM_TOOLS_MODULEID, "Tools", "EXEC", 3));
		}
	}

	public JMenuOption addCustomMenu(String menuID, String moduleID, String description, String moduleType,
			int seqID) {
		JMenuOption menuOption = new JMenuOption(Common.selectedHostID, Common.sessionID);
		menuOption.menuID = menuID;
		menuOption.moduleID = moduleID;
		menuOption.description = description;
		menuOption.moduleType = moduleType;
		menuOption.sequenceID = seqID;

		return menuOption;
	}

	public HashMap<JMenuOption, HashMap<String, JMenuOption>> getMenuMap() {
		return menuMap;
	}

}
