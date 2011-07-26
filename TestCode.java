package com.samsung.sta.peel;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.peel.data.DataModel;
import com.peel.data.PeelData;
import com.peel.data.PeelData.LoaderEvents;
import com.peel.data.PeelDatabase;
import com.peel.data.StateMachine;
import com.peel.util.AppThread;
import com.peel.util.PeelConstants;
import com.samsung.sta.peel.db.DBAdapter;
import com.samsung.sta.peel.utils.Logger;

// add comments...
public class PeelRemoteActivity extends Activity {
	Logger logger = Logger.getLogger(PeelRemoteActivity.class);

	private static PeelData peelData = null;
	private static PeelDatabase peelDB = null;
	private static DBAdapter dba;
	private static int codesetid = 0;
	private static int rank = 0;
	private static int udid = 0;
	private static int uesid = 0;
	private static int deviceTypeId = 2;
	private static int brandNameId = 137;

	private final LoaderEvents.Message loaderObserver = new LoaderEvents.Message() {
		public final void event(final int id, final Object obj,
				final Object... param) {
			switch (id) {
			case LoaderEvents.LOADED:
				// now load in preferences
				final String roomId = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext())
						.getString(PeelConstants.CURRENT_ROOM, null);
				if (null != roomId) {
					PeelData.getData().setCurrentRoom(
							PeelData.getData().getRoom(roomId));
				}
				break;
			}
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		AppThread.start();
		PeelData.getData().start(this);
		PeelData.loaderEvents.add(loaderObserver);

		testSetup();
	}

	private void testSetup() {
		DataModel.Room room = new StateMachine.Room("My Room");
		room.setFruit(new StateMachine.Fruit(DataModel.Fruit.BUILTIN, null));
		room.start();

		PeelData.getData().addRoom(room);
		PeelData.getData().setCurrentRoom(room);

		PeelData pD = PeelData.getData();

		DataModel.Activity watchTvActivity = new StateMachine.Activity(
				"My Activity");
		room.addActivity(watchTvActivity);
		room.startActivity(watchTvActivity);

		DataModel.Device tv = new StateMachine.Device(DataModel.Device.TYPE_TV,
				37, "Samsung", false);
		ArrayList<String> activityInputs = new ArrayList<String>();
		activityInputs.add("HDMI1"); // e.g. HDMI1

		ArrayList<Integer> activityModes = new ArrayList<Integer>();
		// if it's an audio control device, e.g. stereo or tv
		activityModes.add(StateMachine.Activity.MODE_AUDIO);
		// if it's an control device, e.g. set top box, dvd, etc.
		activityModes.add(StateMachine.Activity.MODE_CONTROL);
		// add device to activity. activityInputs and activityModes can be null
		// if they don't have inputs and/or modes
		watchTvActivity.addDevice(tv, activityInputs, activityModes);
	}

	// // Test code for device types
	// new Thread(null, new Runnable() {
	// @SuppressWarnings("deprecation")
	// @Override
	// public void run() {
	// StringBuffer buff = new StringBuffer();
	// List<DeviceType> types = DeviceCloud.getDeviceTypes();
	// for (DeviceType type : types) {
	// buff.append(type.id);
	// buff.append("--");
	// buff.append(type.name);
	// buff.append(' ');
	// }
	// logger.debug(buff.toString());
	// }
	// }, "Thread-DeviceType").start();
	//
	// // Test codes for brand name by device type (TV)
	// // @param : devicetypeid
	// new Thread(null, new Runnable() {
	// @Override
	// public void run() {
	// StringBuffer buff = new StringBuffer();
	// List<Brand> brands = DeviceCloud
	// .getBrandsByDeviceType(deviceTypeId); // TV
	// for (Brand brand : brands) {
	// buff.append(brand.id);
	// buff.append("--");
	// buff.append(brand.brandName);
	// buff.append(' ');
	// }
	// logger.debug(buff.toString());
	// }
	// }, "Thread-BrandName").start();
	//
	// // Test codes for get code sets
	// // @param : brandid, devicetypeid
	// new Thread(null, new Runnable() {
	// @SuppressWarnings("deprecation")
	// @Override
	// public void run() {
	// ArrayList<Codeset> codesets = DeviceCloud.getCodesets(
	// brandNameId, deviceTypeId); // Samsung
	// // TV
	// for (Codeset cs : codesets) {
	// codesetid = cs.codesetid;
	// rank = cs.rank;
	// logger.debug("Codeset Id/Rank -- " + cs.toString());
	// }
	// }
	// }, "Thread-CodeSet").start();
	//
	// // Test codes for get input map by code set id
	// // @param : codesetid, devicetypeid, brandid
	// new Thread(null, new Runnable() {
	// @Override
	// public void run() {
	// final HashMap<String, HashMap<String, Object>> maps = DeviceCloud
	// .getInputsMapByCodesetid(codesetid, deviceTypeId,
	// brandNameId);
	// if (maps != null) {
	// Iterator<String> itr = maps.keySet().iterator();
	// while (itr.hasNext()) {
	// HashMap<String, Object> map = maps.get(itr.next());
	// // temporary test ... it can be changed at serveral
	// // function maps
	// uesid = (Integer) (map.get("UES"));
	// logger.debug("Input map -- " + map.toString());
	// }
	// } else {
	// logger.error("Input map is null");
	// }
	// }
	// }, "Thread-getInputsMapByCodesetid").start();
	//
	// // Test codes for get function maps by code set id
	// // @param : codesetid, devicetypeid, brandid
	// new Thread(null, new Runnable() {
	// @Override
	// public void run() {
	// final HashMap<String, HashMap<String, Object>> maps = DeviceCloud
	// .getFunctionsMapByCodesetid(codesetid, deviceTypeId,
	// brandNameId);
	// if (maps != null) {
	// Iterator<String> itr = maps.keySet().iterator();
	// while (itr.hasNext()) {
	// HashMap<String, Object> map = maps.get(itr.next());
	// // temporary test ... it can be changed at serveral
	// // function maps
	// uesid = (Integer) (map.get("UES"));
	// logger.debug("Function map: " + map.toString());
	// }
	// } else {
	// logger.error("Function map is null");
	// }
	// }
	// }, "Thread-Functionmap").start();
	//
	// // Test codes for get ues
	// // @param : uescodeid
	// new Thread(null, new Runnable() {
	// @SuppressWarnings("deprecation")
	// @Override
	// public void run() {
	// Ues ues = DeviceCloud.getUes(uesid);
	// if (ues != null) {
	// logger.debug("UES -- " + ues.toString());
	// } else {
	// logger.error("ues is null");
	// }
	// }
	// }, "Thread-getUes").start();
	//
	// // Test codes for get cloud product version stat
	// // @param : prod Id, curVersion
	// new Thread(null, new Runnable() {
	// @Override
	// public void run() {
	// // String stat = DeviceCloud.getCloudProductVersionStat(0,
	// // null);
	// // if (stat.equalsIgnoreCase("") || stat == null) {
	// // logger.error("Failed to get stat");
	// // } else {
	// // logger.debug("" + stat);
	// // }
	// }
	// }, "Thread-getCloudProductVersionStat").start();
	//
	// // Test codes for get users
	// // @return user id
	// new Thread(null, new Runnable() {
	// @Override
	// public void run() {
	// // int id = DeviceCloud.addUser();
	// // udid = id;
	// // logger.debug("User id " + id + " is created!");
	// }
	// }, "Thread-getUsers").start();
	//
	// // Test codes for get new user id
	// // @return user id
	// new Thread(null, new Runnable() {
	// @Override
	// public void run() {
	// // int id = DeviceCloud.addUser();
	// // udid = id;
	// // logger.debug("User id " + id + " is created!");
	// }
	// }, "Thread-addUser").start();
	//
	// // Test codes for get user by udid
	// // @param udid
	// new Thread(null, new Runnable() {
	// @Override
	// public void run() {
	// if (udid != 0) {
	// try {
	// HashMap<String, Object> users = DeviceCloud
	// .getUserByUdid(String.valueOf(udid));
	// if (users != null) {
	// Iterator<String> itr = users.keySet().iterator();
	// while (itr.hasNext()) {
	// Object o = users.get(itr);
	// logger.debug(o.toString());
	// }
	// } else {
	// logger.error("users is null");
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }, "Thread-getUserByUdid").start();

	// SharedPreferences sP =
	// PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	// String roomId = sP.getString(PeelConstants.CURRENT_ROOM, null);
	// if (null != roomId) {
	// PeelData.getData().setCurrentRoom(PeelData.getData().getRoom(roomId));
	// }
	// PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(PeelConstants.CURRENT_ROOM,
	// null);

	// DeviceNetwork dn = DeviceNetwork.getInstance();
	// dn.runSetup();

	// Thread t = new Thread(null, new Runnable(){
	// @Override
	// public void run() {
	// dn.runSetup();
	// }
	// });
	// t.start();

	// dn.getDeviceTypes();
	// dn.getBrands("1");

	// dba = new DBAdapter(this, "SettingPeel");
	// peelData = new PeelData();
	// peelDB = new PeelDatabase(this);

	// peelData.start(this);

	// device types
	// 1 -- TV
	// 2 -- Set-top box
	// 3 -- DVD Players
	// 4 -- Blu-ray Players
	// 5 -- A/V Receivers
	// 6 -- Streaming Media Players
	// 13 -- DVD Home theater
	// 14 -- Bluray Home theater
	// 10 -- Projectors

	// brand names of TV
	// 1 -- Admiral -- 999
	// 2 -- AOC -- 999
	// 3 -- Apex -- 999
	// 4 -- Audiovox -- 999
	// 5 -- Broksonic -- 999
	// 6 -- Casio -- 999
	// 8 -- Curtis Mathes -- 999
	// 9 -- Curtis -- 999
	// 10 -- Dell -- 999
	// 11 -- Dynex -- 999
	// 12 -- Element Electronics -- 999
	// 13 -- Emerson -- 999
	// 14 -- Epson -- 999
	// 15 -- Fujitsu -- 999
	// 16 -- Gateway -- 999
	// 17 -- Haier -- 999
	// 18 -- Hannspree -- 999
	// 19 -- Hewlett Packard -- 999
	// 20 -- Hitachi -- 999
	// 21 -- Hyundai -- 999
	// 22 -- Insignia -- 999
	// 23 -- JVC -- 999
	// 24 -- LG -- 999
	// 25 -- Magnavox -- 999
	// 26 -- Memorex -- 999
	// 27 -- Mitsubishi -- 999
	// 28 -- Nexus -- 999
	// 29 -- Optoma -- 999
	// 30 -- Panasonic -- 1
	// 31 -- Philips -- 999
	// 32 -- Pioneer -- 999
	// 33 -- Polaroid -- 999
	// 34 -- ProScan -- 999
	// 36 -- RCA -- 999
	// 37 -- Samsung -- 2
	// 38 -- Sansui -- 999
	// 39 -- Sanyo -- 999
	// 40 -- Sceptre -- 999
	// 41 -- Sharp -- 4
	// 42 -- Sony -- 3
	// 44 -- Sylvania -- 999
	// 45 -- TCL -- 999
	// 46 -- Toshiba -- 5
	// 48 -- Viore -- 999
	// 49 -- Vivitek -- 999
	// 50 -- Vizio -- 999
	// 51 -- Astar -- 999
	// 52 -- BenQ -- 999
	// 53 -- SunBrite -- 999
	// 58 -- Akai -- 999
	// 67 -- Funai -- 999
	// 70 -- ILO -- 999
	// 79 -- NEC -- 999
	// 87 -- Yamaha -- 999
	// 139 -- Zenith -- 999
	// 152 -- Bell & Howell -- 999
	// 153 -- Daewoo -- 999
	// 154 -- Electrohome -- 999
	// 155 -- Fluid -- 999
	// 156 -- General Electric -- 999
	// 157 -- Loewe -- 999
	// 158 -- Orion -- 999
	// 159 -- Symphonic -- 999
	// 160 -- Vision Quest -- 999
	// 161 -- Westinghouse -- 999
	// 184 -- Linn -- 999
	// 189 -- Olevia -- 999
	// 205 -- Crosley -- 999
	// 216 -- Soyo -- 999
	// 217 -- Norcent -- 999
	// 236 -- Auria -- 999
	// 237 -- Majestic -- 999

	// set top box brand name
	// 15--Fujitsu 20--Hitachi 22--Insignia 23--JVC 24--LG 25--Magnavox
	// 30--Panasonic 31--Philips 32--Pioneer 36--RCA 37--Samsung 42--Sony
	// 46--Toshiba 117--Aurora 118--Bell ExpressVu 119--CanalPlus 120--Cisco
	// 121--Comcast 122--Digital Stream 123--Dish 125--Hughes 126--Moxi
	// 127--NFusion 128--Pace 130--Pansat 131--Pico Macom 132--RadioShack
	// 133--Sabrent 134--Scientific Atlanta 135--Sling Media 136--Sonicview
	// 137--Tivo 138--Tristar 139--Zenith 149--DIRECTV 150--Evolution
	// 151--ATT 183--Verizon Fios 194--Motorola 199--ReplayTV 203--Foxtel
	// 204--Homecast 215--Telewest 226--Access 227--Airlink 228--Artec
	// 229--Winegard 230--GE 231--Coship 232--Zinwell 233--Hauppauge
	// 234--RCN 235--Tocom
}